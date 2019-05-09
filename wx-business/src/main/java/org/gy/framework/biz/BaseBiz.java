package org.gy.framework.biz;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.gy.framework.util.jedis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseBiz {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 执行增、删、改操作
     */
    @Resource(name = "sqlSessionMaster")
    protected SqlSession   sqlSessionMaster;

    /**
     * 执行查询操作
     */
    @Resource(name = "sqlSessionSlave")
    protected SqlSession   sqlSessionSlave;
    
    @Autowired
    protected RedisClient    redisClient;
    
}
