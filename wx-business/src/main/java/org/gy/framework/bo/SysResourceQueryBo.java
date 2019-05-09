/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: SysResourceQueryBo.java
 *
 * @Author gy
 * @Date 2016年7月14日上午1:25:47
 */
package org.gy.framework.bo;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：系统资源查询BO
 * 
 * @Author gy
 * @Date 2016年7月14日上午1:25:47
 */
public class SysResourceQueryBo extends Pagination {

    /**
     * 节点名称
     * 
     * @mbggenerated
     */
    private String name;

    /**
     * 状态，0未启用，1启用
     * 
     * @mbggenerated
     */
    private Short  status;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
