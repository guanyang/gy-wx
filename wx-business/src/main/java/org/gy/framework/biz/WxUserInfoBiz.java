package org.gy.framework.biz;

import java.util.List;

import org.gy.framework.bo.WxUserInfoBo;
import org.gy.framework.dao.WxUserInfoMapper;
import org.gy.framework.model.WxUserInfo;
import org.gy.framework.model.WxUserInfoExample;
import org.springframework.stereotype.Service;

/**
 * 功能描述：微信通用用户信息Biz
 * 
 * @Date 2017-10-30 10:04:29
 */
@Service
public class WxUserInfoBiz extends BaseBiz {


    /**
     * 功能描述: 添加非空字段，返回主键
     * 
     * @param entity 实体
     * @return 主键
     * @Date 2017-10-30 10:04:29
     */
    public Long insertSelective(WxUserInfo entity) {
        sqlSessionMaster.getMapper(WxUserInfoMapper.class).insertSelective(entity);
        return entity.getId();
    }
    
    /**
     * 功能描述: 添加或更新，返回主键
     * 
     * @param entity
     * @return
     */
    public Long insertOrUpdate(WxUserInfo entity) {
        sqlSessionMaster.insert("WX_USER_INFO.INSERT_OR_UPDATE_USER_INFO", entity);
        return entity.getId();
    }
    

    /**
     * 功能描述: 根据主键更新非空字段
     * 
     * @param entity 实体
     * @return 成功的条数
     * @Date 2017-10-30 10:04:29
     */
    public int updateByPrimaryKeySelective(WxUserInfo entity) {
        return sqlSessionMaster.getMapper(WxUserInfoMapper.class).updateByPrimaryKeySelective(entity);
    }
    
    /**
     * 功能描述: 根据主键查询
     *
     * @param id 主键
     * @return 实体
     * @Date 2017-10-30 10:04:29
     */
    public WxUserInfo selectByPrimaryKey(Long id) {
        return sqlSessionSlave.getMapper(WxUserInfoMapper.class).selectByPrimaryKey(id);
    }
    
    /**
     * 功能描述: 根据主键批量删除
     * 
     * @param ids 主键集合
     * @return 成功的条数
     * @Date 2017-10-30 10:04:29
     */
    public int deleteByPrimaryKey(List<Long> ids) {
        WxUserInfoExample example = new WxUserInfoExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(WxUserInfoMapper.class).deleteByExample(example);
    }
    
    /**
     * 功能描述: 根据主键删除
     * 
     * @param id 主键
     * @return 成功的条数
     * @Date 2017-10-30 10:04:29
     */
	public int deleteByPrimaryKey(Long id){
        return sqlSessionMaster.getMapper(WxUserInfoMapper.class).deleteByPrimaryKey(id);
    }        
    
    /**
     * 功能描述: 获取满足条件的记录列表，带分页
     * 
     * @param query
     * @return
     * @Date 2017-10-30 10:04:29
     */
    public List<WxUserInfo> queryForList(WxUserInfoBo query) {
        return sqlSessionSlave.selectList("WX_USER_INFO.QUERY_FOR_LIST", query);
    }
    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2017-10-30 10:04:29
     */
    public int queryForCount(WxUserInfoBo query) {
        return sqlSessionSlave.selectOne("WX_USER_INFO.QUERY_FOR_COUNT", query);
    }
    
    
    
}
