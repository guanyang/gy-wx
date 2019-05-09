/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WxReplyLogBiz.java
 *
 * @Date 2016-08-08 00:12:59
 */
package org.gy.framework.biz;

import java.util.List;

import org.gy.framework.bo.WxReplyLogBo;
import org.gy.framework.dao.WxReplyLogMapper;
import org.gy.framework.model.WxReplyLog;
import org.gy.framework.model.WxReplyLogExample;
import org.springframework.stereotype.Service;

/**
 * 功能描述：微信回复日志Biz
 * 
 * @Date 2016-08-08 00:12:59
 */
@Service
public class WxReplyLogBiz extends BaseBiz {

    /**
     * 功能描述: 添加，返回主键
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public Long insert(WxReplyLog entity) {
        sqlSessionMaster.getMapper(WxReplyLogMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public int update(WxReplyLog entity) {
        return sqlSessionMaster.getMapper(WxReplyLogMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public WxReplyLog select(Long id) {
        return sqlSessionSlave.getMapper(WxReplyLogMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public int delete(List<Long> ids) {
        WxReplyLogExample example = new WxReplyLogExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(WxReplyLogMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 获取满足条件的记录列表
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public List<WxReplyLog> queryForList(WxReplyLogBo query) {
        return sqlSessionSlave.selectList("WX_REPLY_LOG.FIND_BY_PAGE", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2016-08-08 00:12:59
     */
    public Integer queryForCount(WxReplyLogBo query) {
        return sqlSessionSlave.selectOne("WX_REPLY_LOG.COUNT_RECORD", query);
    }

}
