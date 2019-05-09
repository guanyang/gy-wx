package org.gy.framework.util.lock;


/**
 * 功能描述：分布式锁操作接口
 * 
 * @version 2.0.0
 */
public interface DistributedLockAction {

    /**
     * 获取锁
     */
    boolean acquire();

    /**
     * 释放锁
     */
    void release();

}
