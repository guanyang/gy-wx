//package org.gy.framework.util.lock.zookeeper;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import org.I0Itec.zkclient.IZkDataListener;
//import org.I0Itec.zkclient.ZkClient;
//import org.I0Itec.zkclient.exception.ZkNoNodeException;
//import org.apache.commons.lang3.StringUtils;
//import org.gy.framework.util.lock.DistributedLockAction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class SimpleZookeeperDistributedLockAction implements DistributedLockAction {
//
//    private static final Logger logger = LoggerFactory.getLogger(SimpleZookeeperDistributedLockAction.class);
//
//    private final ZkClient      zkClient;
//    private final String        lockBasePath;
//    private final String        lockName;
//    /**
//     * 获取锁失败重试超时时间，如果小于零，则直到获取锁为止，如果等于零，则仅调用一次，不重试，如果大于零，则直到超时，单位：毫秒
//     */
//    private final long          timeoutMillis;
//
//    public SimpleZookeeperDistributedLockAction(ZkClient zkClient, String lockBasePath, String lockName, long timeoutMillis) {
//        this.zkClient = zkClient;
//        this.lockBasePath = lockBasePath;
//        this.lockName = lockName;
//        this.timeoutMillis = timeoutMillis;
//        // 初始化分布式持久化节点
//        boolean exists = zkClient.exists(lockBasePath);
//        if (!exists) {
//            zkClient.createPersistent(lockBasePath, true);
//        }
//
//    }
//
//    @Override
//    public boolean acquire() {
//        return false;
//    }
//
//    @Override
//    public void release() {
//
//    }
//
//    /**
//     * 获取锁的核心方法
//     * 
//     * @param startMillis
//     * @param millisToWait
//     * @param ourPath
//     * @return
//     * @throws Exception
//     */
//    private boolean waitToLock(long startMillis,
//                               Long millisToWait,
//                               String ourPath) {
//
//        boolean haveTheLock = false;
//        boolean doDelete = false;
//
//        try {
//            while (!haveTheLock) {
//                // 该方法实现获取locker节点下的所有顺序节点，并且从小到大排序
//                List<String> children = getSortedChildren();
//                String sequenceNodeName = ourPath.substring(lockBasePath.length() + 1);
//
//                // 计算刚才客户端创建的顺序节点在locker的所有子节点中排序位置，如果是排序为0，则表示获取到了锁
//                int ourIndex = children.indexOf(sequenceNodeName);
//
//                /*如果在getSortedChildren中没有找到之前创建的[临时]顺序节点，这表示可能由于网络闪断而导致
//                 *Zookeeper认为连接断开而删除了我们创建的节点，此时需要抛出异常，让上一级去处理
//                 *上一级的做法是捕获该异常，并且执行重试指定的次数 见后面的 attemptLock方法  */
//                if (ourIndex < 0) {
//                    throw new ZkNoNodeException("节点没有找到: " + sequenceNodeName);
//                }
//
//                // 如果当前客户端创建的节点在locker子节点列表中位置大于0，表示其它客户端已经获取了锁
//                // 此时当前客户端需要等待其它客户端释放锁，
//                boolean isGetTheLock = ourIndex == 0;
//
//                // 如何判断其它客户端是否已经释放了锁？从子节点列表中获取到比自己次小的哪个节点，并对其建立监听
//                String pathToWatch = isGetTheLock ? null : children.get(ourIndex - 1);
//
//                if (isGetTheLock) {
//                    haveTheLock = true;
//                } else {
//                    // 如果次小的节点被删除了，则表示当前客户端的节点应该是最小的了，所以使用CountDownLatch来实现等待
//                    String previousSequencePath = lockBasePath.concat("/").concat(pathToWatch);
//                    final CountDownLatch latch = new CountDownLatch(1);
//                    final IZkDataListener previousListener = new IZkDataListener() {
//
//                        // 次小节点删除事件发生时，让countDownLatch结束等待
//                        // 此时还需要重新让程序回到while，重新判断一次！
//                        public void handleDataDeleted(String dataPath) throws Exception {
//                            latch.countDown();
//                        }
//
//                        public void handleDataChange(String dataPath,
//                                                     Object data) throws Exception {
//                            // ignore
//                        }
//                    };
//
//                    try {
//                        // 如果节点不存在会出现异常
//                        zkClient.subscribeDataChanges(previousSequencePath, previousListener);
//
//                        if (millisToWait != null) {
//                            millisToWait -= (System.currentTimeMillis() - startMillis);
//                            startMillis = System.currentTimeMillis();
//                            if (millisToWait <= 0) {
//                                doDelete = true; // timed out - delete our node
//                                break;
//                            }
//
//                            latch.await(millisToWait, TimeUnit.MICROSECONDS);
//                        } else {
//                            latch.await();
//                        }
//
//                    } catch (ZkNoNodeException e) {
//                        // ignore
//                    } finally {
//                        client.unsubscribeDataChanges(previousSequencePath, previousListener);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // 发生异常需要删除节点
//            doDelete = true;
//            throw e;
//
//        } finally {
//            // 如果需要删除节点
//            if (doDelete) {
//                deleteOurPath(ourPath);
//            }
//        }
//        return haveTheLock;
//    }
//
//    private void deleteOurPath(String path) {
//        zkClient.delete(path);
//    }
//
//    private String createLockNode(String path) {
//        return zkClient.createEphemeralSequential(path, null);
//    }
//
//    private String getLockNodeNumber(String str,
//                                     String lockName) {
//        return StringUtils.substringAfterLast(str, lockName);
//    }
//
//    private List<String> getSortedChildren() {
//        List<String> children = zkClient.getChildren(lockBasePath);
//        Collections.sort(children, new Comparator<String>() {
//            @Override
//            public int compare(String lhs,
//                               String rhs) {
//                return getLockNodeNumber(lhs, lockName).compareTo(getLockNodeNumber(rhs, lockName));
//            }
//        });
//        return children;
//    }
//
//}
