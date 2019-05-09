/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WxReplyConfigBiz.java
 *
 * @Date 2016-08-08 00:30:54
 */
package org.gy.framework.biz;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.gy.framework.bo.WxReplyConfigBo;
import org.gy.framework.dao.WxReplyConfigMapper;
import org.gy.framework.model.WxReplyConfig;
import org.gy.framework.model.WxReplyConfigExample;
import org.gy.framework.util.cache.RedisConfig;
import org.gy.framework.util.cache.RedisConfig.CacheKey;
import org.gy.framework.util.json.JacksonMapper;
import org.springframework.stereotype.Service;

/**
 * 功能描述：微信回复配置Biz
 * 
 * @Date 2016-08-08 00:30:54
 */
@Service
public class WxReplyConfigBiz extends BaseBiz {

    /**
     * 功能描述: 添加，返回主键
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public Long insert(WxReplyConfig entity) {
        sqlSessionMaster.getMapper(WxReplyConfigMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public int update(WxReplyConfig entity) {
        deleteReplyConfigCache(entity.getAppId(), entity.getKeywords());// 删除缓存
        return sqlSessionMaster.getMapper(WxReplyConfigMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public WxReplyConfig select(Long id) {
        return sqlSessionSlave.getMapper(WxReplyConfigMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public int delete(List<Long> ids) {
        WxReplyConfigExample example = new WxReplyConfigExample();
        example.createCriteria().andIdIn(ids);

        batchDeleteConfigCache(example);// 删除缓存

        return sqlSessionMaster.getMapper(WxReplyConfigMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 获取满足条件的记录列表
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public List<WxReplyConfig> queryForList(WxReplyConfigBo query) {
        return sqlSessionSlave.selectList("WX_REPLY_CONFIG.FIND_BY_PAGE", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:30:54
     */
    public Integer queryForCount(WxReplyConfigBo query) {
        return sqlSessionSlave.selectOne("WX_REPLY_CONFIG.COUNT_RECORD", query);
    }

    /**
     * 功能描述:根据关键字获取回复配置（带缓存）
     * 
     * @param keywords
     * @return
     * @Author gy
     * @Date 2016年8月21日下午6:51:22
     */
    public WxReplyConfig getReplyConfigByKeywordsWithCache(String appId,
                                                           String keywords) {
        String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_REPLY_CONFIG_KEY, appId, keywords);
        String cache = redisClient.get(redisKey);

        if (StringUtils.isNotBlank(cache) && !"null".equals(cache.trim())) {
            return JacksonMapper.jsonToBean(cache, WxReplyConfig.class);
        } else {
            WxReplyConfigExample example = new WxReplyConfigExample();
            example.createCriteria().andKeywordsEqualTo(keywords).andEnableEqualTo(1).andAppIdEqualTo(appId);// 生效状态的回复配置
            List<WxReplyConfig> list = sqlSessionMaster.getMapper(WxReplyConfigMapper.class).selectByExample(example);
            WxReplyConfig cofnig = null;
            if (CollectionUtils.isNotEmpty(list)) {
                cofnig = list.get(0);
                redisClient.setex(redisKey, CacheKey.GY_CACHE_WX_REPLY_CONFIG_KEY.getExpireTime(), JacksonMapper.beanToJson(cofnig));

            }
            return cofnig;
        }
    }

    /**
     * 功能描述: 根据条件批量删除缓存
     * 
     * @param example
     * @Author gy
     * @Date 2016年8月21日下午7:19:51
     */
    private void batchDeleteConfigCache(WxReplyConfigExample example) {
        List<WxReplyConfig> list = sqlSessionMaster.getMapper(WxReplyConfigMapper.class).selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            for (WxReplyConfig config : list) {
                deleteReplyConfigCache(config.getAppId(), config.getKeywords());// 删除缓存
            }
        }
    }

    /**
     * 功能描述: 根据keywords删除缓存
     * 
     * @param keywords
     * @Author gy
     * @Date 2016年8月21日下午7:18:52
     */
    private void deleteReplyConfigCache(String appId,
                                        String keywords) {
        String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_REPLY_CONFIG_KEY, appId, keywords);
        redisClient.del(redisKey);
    }
    
    /**
     * 功能描述: 解析小程序配置
     * 
     * @param source
     * @return
     */
    public static Map<String, Object> parseMiniProgramConfig(Map<String, Object> menuMap,
                                                             String source) {
        Map<String, String> map = JacksonMapper.jsonToBean(source, new TypeReference<Map<String, String>>() {
        });
        menuMap.putAll(map);
        return menuMap;
    }
}
