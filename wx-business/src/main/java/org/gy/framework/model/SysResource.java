package org.gy.framework.model;

public class SysResource {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 节点名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 父节点ID
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     * url链接
     *
     * @mbggenerated
     */
    private String url;

    /**
     * 节点描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 状态，0未启用，1启用
     *
     * @mbggenerated
     */
    private Short status;

    /**
     * 获取  主键
     *
     * @return ID 主键
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置  主键
     *
     * @param id 主键
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取  节点名称
     *
     * @return NAME 节点名称
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * 设置  节点名称
     *
     * @param name 节点名称
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取  父节点ID
     *
     * @return PARENT_ID 父节点ID
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置  父节点ID
     *
     * @param parentId 父节点ID
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取  url链接
     *
     * @return URL url链接
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置  url链接
     *
     * @param url url链接
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取  节点描述
     *
     * @return DESCRIPTION 节点描述
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置  节点描述
     *
     * @param description 节点描述
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取  状态，0未启用，1启用
     *
     * @return STATUS 状态，0未启用，1启用
     *
     * @mbggenerated
     */
    public Short getStatus() {
        return status;
    }

    /**
     * 设置  状态，0未启用，1启用
     *
     * @param status 状态，0未启用，1启用
     *
     * @mbggenerated
     */
    public void setStatus(Short status) {
        this.status = status;
    }
}