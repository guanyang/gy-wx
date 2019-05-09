/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: SysResourceBiz.java
 *
 * @Author gy
 * @Date 2016年7月12日下午10:33:27
 */
package org.gy.framework.biz;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.bo.SysResourceQueryBo;
import org.gy.framework.dao.SysResourceMapper;
import org.gy.framework.model.SysResource;
import org.gy.framework.model.SysResourceExample;
import org.springframework.stereotype.Service;

/**
 * 功能描述：系统资源配置Biz
 * 
 * @Author gy
 * @Date 2016年7月12日下午10:33:27
 */
@Service
public class SysResourceBiz extends BaseBiz {

    /**
     * 功能描述: 添加
     * 
     * @param entity
     * @return
     * @Author gy
     * @Date 2016年7月16日下午4:13:30
     */
    public Long insertSelective(SysResource entity) {
        sqlSessionMaster.getMapper(SysResourceMapper.class).insertSelective(entity);
        return entity.getId();
    }

    /**
     * 功能描述: 根据主键更新
     * 
     * @param entity
     * @return
     * @Author gy
     * @Date 2016年7月16日下午4:14:38
     */
    public int updateByPrimaryKeySelective(SysResource entity) {
        return sqlSessionMaster.getMapper(SysResourceMapper.class).updateByPrimaryKeySelective(entity);
    }

    /**
     * 功能描述: 批量删除
     * 
     * @param ids
     * @return
     * @Author gy
     * @Date 2016年7月16日下午4:29:55
     */
    public int deleteByPrimaryKey(List<Long> ids) {
        SysResourceExample example = new SysResourceExample();
        example.createCriteria().andIdIn(ids);
        return sqlSessionMaster.getMapper(SysResourceMapper.class).deleteByExample(example);
    }

    /**
     * 功能描述: 根据主键删除
     * 
     * @param id 主键
     * @return 成功的条数
     */
    public int deleteByPrimaryKey(Long id) {
        return sqlSessionMaster.getMapper(SysResourceMapper.class).deleteByPrimaryKey(id);
    }

    /**
     * 功能描述: 获取所有启用状态的资源列表
     * 
     * @return
     * @Author gy
     * @Date 2016年7月12日下午10:40:09
     */
    public List<SysResource> getAllResources(SysResourceQueryBo query) {
        SysResourceExample example = new SysResourceExample();
        if (query.getStatus() != null) {
            example.createCriteria().andStatusEqualTo(query.getStatus());
        }
        if (StringUtils.isNotBlank(query.getName())) {
            example.createCriteria().andNameLike("%" + query.getName() + "%");
        }
        return sqlSessionSlave.getMapper(SysResourceMapper.class).selectByExample(example);
    }

    /**
     * 功能描述: 获取满足条件的资源列表
     * 
     * @param query
     * @return
     * @Author gy
     * @Date 2016年7月14日上午1:41:20
     */
    public List<SysResource> queryForList(SysResourceQueryBo query) {
        return sqlSessionSlave.selectList("SYS_RESOURCE.SELECT_RESOURCE_LIST", query);
    }

    /**
     * 功能描述: 获取满足条件的资源数量
     * 
     * @param query
     * @return
     * @Author gy
     * @Date 2016年7月14日上午1:42:52
     */
    public Integer queryForCount(SysResourceQueryBo query) {
        return sqlSessionSlave.selectOne("SYS_RESOURCE.SELECT_RESOURCE_COUNT", query);
    }

}
