package org.gy.framework.util.lock;

import org.gy.framework.util.lock.DistributedLockAction;
import org.gy.framework.util.lock.DistributedLockRunnable;
import org.gy.framework.util.response.Result;
import org.gy.framework.util.response.ReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：
 * 
 */
public class DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLock.class);

    private DistributedLock() {
    }

    public static Result execute(DistributedLockAction action,
                                 DistributedLockRunnable runnable) {
        Result result = new Result();
        boolean flag = action.acquire();// 获取锁
        if (!flag) {
            result.wrapResultCode(ReturnCode.E9901);
            return result;
        }
        try {
            result = runnable.run();
        } catch (Exception e) {
            LOGGER.error("DistributedLock execute error.", e);
            result.wrapResultCode(ReturnCode.E9902);
        } finally {
            action.release();// 释放锁
        }
        return result;

    }

}
