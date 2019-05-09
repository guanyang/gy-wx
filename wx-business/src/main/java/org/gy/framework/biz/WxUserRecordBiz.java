/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WxUserRecordBiz.java
 *
 * @Date 2016-08-08 00:00:57
 */
package org.gy.framework.biz;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gy.framework.bo.WxUserRecordBo;
import org.gy.framework.dao.WxUserRecordMapper;
import org.gy.framework.model.WxUserRecord;
import org.gy.framework.model.WxUserRecordExample;
import org.springframework.stereotype.Service;

/**
 * 功能描述：微信用户记录Biz
 * 
 * @Date 2016-08-08 00:00:57
 */
@Service
public class WxUserRecordBiz extends BaseBiz {

    /**
     * 功能描述: 添加，返回主键
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public Long insert(WxUserRecord entity) {
        sqlSessionMaster.getMapper(WxUserRecordMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 添加或更新，返回主键
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public Long insertOrUpdate(WxUserRecord entity) {
        sqlSessionMaster.insert("WX_USER_RECORD.INSERT_OR_UPDATE_USER_RECORD", entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public int update(WxUserRecord entity) {
        return sqlSessionMaster.getMapper(WxUserRecordMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public WxUserRecord select(Long id) {
        return sqlSessionSlave.getMapper(WxUserRecordMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public int delete(List<Long> ids) {
        WxUserRecordExample example = new WxUserRecordExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(WxUserRecordMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 获取满足条件的记录列表
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public List<WxUserRecordBo> queryForList(WxUserRecordBo query) {
        return sqlSessionSlave.selectList("WX_USER_RECORD.FIND_BY_PAGE", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:00:57
     */
    public Integer queryForCount(WxUserRecordBo query) {
        return sqlSessionSlave.selectOne("WX_USER_RECORD.COUNT_RECORD", query);
    }

    /**
     * 功能描述: 根据openId和appId更新用户记录，不需要更新场景ID
     * 
     * @param openId
     * @return
     */
    public int updateByOpenIdAndAppId(WxUserRecord entity) {
        return sqlSessionMaster.update("WX_USER_RECORD.UPDATE_USER_RECORD_BY_OPEN_ID_AND_APPID", entity);
    }

    /**
     * 功能描述:根据openId和appId获取微信用户记录
     * 
     * @param openId
     * @return
     */
    public WxUserRecord getUserRecordByOpenId(String openId,
                                              String appId) {
        WxUserRecordExample example = new WxUserRecordExample();
        example.createCriteria().andOpenIdEqualTo(openId).andAppIdEqualTo(appId);
        List<WxUserRecord> list = sqlSessionSlave.getMapper(WxUserRecordMapper.class).selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
}
