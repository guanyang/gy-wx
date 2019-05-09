package org.gy.framework.biz;

import java.util.Date;
import java.util.List;

import org.gy.framework.bo.SysUserBo;
import org.gy.framework.dao.SysUserMapper;
import org.gy.framework.model.SysUser;
import org.gy.framework.model.SysUserExample;
import org.springframework.stereotype.Service;

@Service
public class SysUserBiz extends BaseBiz {

    /**
     * 功能描述: 根据账号名获取用户信息(登录认证)
     * 
     * @param accountName
     * @return
     */
    public SysUser findSysUserByAccountName(String accountName) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andAccountNameEqualTo(accountName);
        List<SysUser> list = sqlSessionSlave.getMapper(SysUserMapper.class).selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 功能描述: 添加
     * 
     * @param entity
     * @return
     */
    public Long insertSelective(SysUser entity) {
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        sqlSessionMaster.getMapper(SysUserMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Author gy
     * @Date 2016年7月16日下午9:55:32
     */
    public int updateByPrimaryKeySelective(SysUser entity) {
        entity.setUpdateTime(new Date());
        return sqlSessionMaster.getMapper(SysUserMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 根据主键查询
     * 
     * @param id
     * @return
     * @Date 2016-08-05 22:41:15
     */
    public SysUser selectByPrimaryKey(Long id) {
        return sqlSessionSlave.getMapper(SysUserMapper.class).selectByPrimaryKey(id);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Date 2016-08-05 22:41:15
     */
    public int deleteByPrimaryKey(List<Long> ids) {
        SysUserExample example = new SysUserExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(SysUserMapper.class).deleteByExample(example);
    }
    
    /**
     * 功能描述: 根据主键删除
     * 
     * @param id 主键
     * @return 成功的条数
     * @Date 2016-12-25 00:27:23
     */
    public int deleteByPrimaryKey(Long id){
        return sqlSessionMaster.getMapper(SysUserMapper.class).deleteByPrimaryKey(id);
    } 

    /**
     * 功能描述: 获取满足条件的记录列表
     * 
     * @param query
     * @return
     * @Date 2016-08-05 22:41:15
     */
    public List<SysUser> queryForList(SysUserBo query) {
        return sqlSessionSlave.selectList("SYS_USER.FIND_BY_PAGE", query);
    }

    /**
     * 功能描述: 获取满足条件的记录数量
     * 
     * @param query
     * @return
     * @Date 2016-08-05 22:41:15
     */
    public Integer queryForCount(SysUserBo query) {
        return sqlSessionSlave.selectOne("SYS_USER.COUNT_RECORD", query);
    }

}
