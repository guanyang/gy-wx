package org.gy.framework.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gy.framework.bo.WxAppConfigBo;
import org.gy.framework.dao.WxAppConfigMapper;
import org.gy.framework.model.WxAppConfig;
import org.gy.framework.model.WxAppConfigExample;
import org.gy.framework.util.cache.RedisConfig;
import org.gy.framework.util.cache.RedisConfig.CacheKey;
import org.gy.framework.util.jedis.DistributedLock;
import org.gy.framework.util.jedis.ShardedJedisAction;
import org.gy.framework.util.json.JacksonMapper;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

/**
 * 功能描述：微信通用配置Biz
 * 
 * @Date 2018-01-13 17:05:13
 */
@Service
public class WxAppConfigBiz extends BaseBiz {

    /**
     * 功能描述: 根据appId，获取app信息(只查启用状态)
     * 
     * @param appId
     * @return
     */
    public WxAppConfig getWxAppConfigWithCache(final String appId) {
        final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_APP_CONFIG_KEY);
        String result = redisClient.execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                return shardedJedis.hget(redisKey, appId);
            }
        });
        if (StringUtils.isNotBlank(result)) {
            WxAppConfig config = JacksonMapper.jsonToBean(result, WxAppConfig.class);
            if (config == null || config.getEnable() != 1) {
                return null;
            }
            return config;
        }
        List<WxAppConfig> list = getAllWxAppConfigWithCache(1);
        for (WxAppConfig wxAppConfig : list) {
            if (wxAppConfig.getAppId().equals(appId)) {
                return wxAppConfig;
            }
        }
        return null;

    }

    /**
     * 功能描述: 获取公众号列表
     *
     * @param enable 是否启用，1只查启用，2只查禁用，-1则查所有状态
     * @return
     */
    public Map<String, String> getAllWxAppConfig(int enable) {
        Map<String, String> map = new TreeMap<>();// 按顺序展示
        List<WxAppConfig> list = getAllWxAppConfigWithCache(enable);
        for (WxAppConfig wxAppConfig : list) {
            map.put(wxAppConfig.getAppId(), wxAppConfig.getAppName());
        }
        return map;
    }

    /**
     * 功能描述: 获取APP配置列表（带缓存）
     * 
     * @param enable 是否启用，1只查启用，2只查禁用，-1则查所有状态
     * @return
     */
    public List<WxAppConfig> getAllWxAppConfigWithCache(int enable) {
        final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_APP_CONFIG_KEY);
        List<WxAppConfig> list = new ArrayList<>();
        List<String> result = getAppConfigFromRedis(redisKey);
        if (CollectionUtils.isNotEmpty(result)) {
            wrapWxAppConfig(list, result);
            filterAppConfigStatus(enable, list);
            return list;
        }
        // 必须添加分布式锁，避免高并发时多次查询DB
        DistributedLock lock = new DistributedLock(redisClient, redisKey, 10000);
        boolean flag = lock.tryLock(5000, 200);
        if (!flag) {
            return list;// 返回空集合
        }
        try {
            result = getAppConfigFromRedis(redisKey);
            if (CollectionUtils.isNotEmpty(result)) {
                wrapWxAppConfig(list, result);
            } else {
                // 查询DB
                getAppConfigFromDB(list);
            }
        } catch (Exception e) {
            logger.error("获取APP信息列表异常", e);
        } finally {
            lock.unLock();// 释放锁
        }
        filterAppConfigStatus(enable, list);// 过滤状态
        return list;
    }

    private static void filterAppConfigStatus(int enable,
                                              List<WxAppConfig> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        if (enable == 1 || enable == 2) {
            for (Iterator<WxAppConfig> iterator = list.iterator(); iterator.hasNext();) {
                WxAppConfig config = iterator.next();
                if (config.getEnable() != enable) {
                    iterator.remove();
                }
            }
        }
    }

    public void deleteAppConfigCache() {
        final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_APP_CONFIG_KEY);
        redisClient.del(redisKey);
    }

    private void getAppConfigFromDB(List<WxAppConfig> list) {
        WxAppConfigExample example = new WxAppConfigExample();
        example.createCriteria().andEnableIsNotNull();
        List<WxAppConfig> configList = sqlSessionMaster.getMapper(WxAppConfigMapper.class).selectByExample(example);
        if (CollectionUtils.isNotEmpty(configList)) {
            final Map<String, String> map = new HashMap<>();
            for (WxAppConfig wxAppConfig : configList) {
                map.put(wxAppConfig.getAppId(), JacksonMapper.beanToJson(wxAppConfig));
            }
            final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_APP_CONFIG_KEY);
            redisClient.execute(new ShardedJedisAction<String>() {
                @Override
                public String doAction(ShardedJedis shardedJedis) {
                    shardedJedis.hmset(redisKey, map);
                    shardedJedis.expire(redisKey, CacheKey.GY_CACHE_WX_APP_CONFIG_KEY.getExpireTime());
                    return null;
                }
            });
            list.addAll(configList);
        }
    }

    /**
     * 功能描述:
     * 
     * @param redisKey
     * @return
     */
    private List<String> getAppConfigFromRedis(final String redisKey) {
        return redisClient.execute(new ShardedJedisAction<List<String>>() {
            @Override
            public List<String> doAction(ShardedJedis shardedJedis) {
                return shardedJedis.hvals(redisKey);
            }
        });
    }

    /**
     * 功能描述:
     * 
     * @param list
     * @param result
     */
    private void wrapWxAppConfig(List<WxAppConfig> list,
                                 List<String> result) {
        for (String string : result) {
            WxAppConfig config = JacksonMapper.jsonToBean(string, WxAppConfig.class);
            if (config != null) {
                list.add(config);
            }
        }
    }

    /**
     * 功能描述: 添加非空字段，返回主键
     * 
     * @param entity 实体
     * @return 主键
     * @Date 2018-01-13 17:05:13
     */
    public Long insertSelective(WxAppConfig entity) {
        deleteAppConfigCache();
        sqlSessionMaster.getMapper(WxAppConfigMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新非空字段
     * 
     * @param entity 实体
     * @return 成功的条数
     * @Date 2018-01-13 17:05:13
     */
    public int updateByPrimaryKeySelective(WxAppConfig entity) {
        deleteAppConfigCache();
        return sqlSessionMaster.getMapper(WxAppConfigMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id 主键
     * @return 实体
     * @Date 2018-01-13 17:05:13
     */
    public WxAppConfig selectByPrimaryKey(Long id) {
        return sqlSessionSlave.getMapper(WxAppConfigMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 根据主键批量删除
     * 
     * @param ids 主键集合
     * @return 成功的条数
     * @Date 2018-01-13 17:05:13
     */
    public int deleteByPrimaryKey(List<Long> ids) {
        deleteAppConfigCache();
        WxAppConfigExample example = new WxAppConfigExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(WxAppConfigMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 根据主键删除
     * 
     * @param id 主键
     * @return 成功的条数
     * @Date 2018-01-13 17:05:13
     */
    public int deleteByPrimaryKey(Long id) {
        deleteAppConfigCache();
        return sqlSessionMaster.getMapper(WxAppConfigMapper.class).deleteByPrimaryKey(id);
    }

    /**
     * 功能描述: 获取满足条件的记录列表，带分页
     * 
     * @param query
     * @return
     * @Date 2018-01-13 17:05:13
     */
    public List<WxAppConfig> queryForList(WxAppConfigBo query) {
        return sqlSessionSlave.selectList("WX_APP_CONFIG.QUERY_FOR_LIST", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2018-01-13 17:05:13
     */
    public int queryForCount(WxAppConfigBo query) {
        return sqlSessionSlave.selectOne("WX_APP_CONFIG.QUERY_FOR_COUNT", query);
    }

}
