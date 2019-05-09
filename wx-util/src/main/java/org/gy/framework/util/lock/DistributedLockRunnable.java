package org.gy.framework.util.lock;

import org.gy.framework.util.response.Result;

/**
 * 功能描述：业务运行接口
 * 
 */
public interface DistributedLockRunnable {

    /**
     * 功能描述:
     * 
     * @return
     */
    Result run();

}
