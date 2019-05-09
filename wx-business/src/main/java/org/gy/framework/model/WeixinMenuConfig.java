package org.gy.framework.model;

import java.util.Date;

public class WeixinMenuConfig {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 菜单名称
     *
     * @mbggenerated
     */
    private String menuName;

    /**
     * 菜单类型
     *
     * @mbggenerated
     */
    private String menuType;

    /**
     * 父层ID
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     * 回复ID
     *
     * @mbggenerated
     */
    private Long replyId;

    /**
     * 排序码
     *
     * @mbggenerated
     */
    private Byte sortNo;

    /**
     * 是否生效，1是，0否
     *
     * @mbggenerated
     */
    private Byte enable;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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
     * 获取  菜单名称
     *
     * @return MENU_NAME 菜单名称
     *
     * @mbggenerated
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置  菜单名称
     *
     * @param menuName 菜单名称
     *
     * @mbggenerated
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    /**
     * 获取  菜单类型
     *
     * @return MENU_TYPE 菜单类型
     *
     * @mbggenerated
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 设置  菜单类型
     *
     * @param menuType 菜单类型
     *
     * @mbggenerated
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    /**
     * 获取  父层ID
     *
     * @return PARENT_ID 父层ID
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置  父层ID
     *
     * @param parentId 父层ID
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取  回复ID
     *
     * @return REPLY_ID 回复ID
     *
     * @mbggenerated
     */
    public Long getReplyId() {
        return replyId;
    }

    /**
     * 设置  回复ID
     *
     * @param replyId 回复ID
     *
     * @mbggenerated
     */
    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    /**
     * 获取  排序码
     *
     * @return SORT_NO 排序码
     *
     * @mbggenerated
     */
    public Byte getSortNo() {
        return sortNo;
    }

    /**
     * 设置  排序码
     *
     * @param sortNo 排序码
     *
     * @mbggenerated
     */
    public void setSortNo(Byte sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取  是否生效，1是，0否
     *
     * @return ENABLE 是否生效，1是，0否
     *
     * @mbggenerated
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置  是否生效，1是，0否
     *
     * @param enable 是否生效，1是，0否
     *
     * @mbggenerated
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    /**
     * 获取  创建时间
     *
     * @return CREATE_TIME 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置  创建时间
     *
     * @param createTime 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取  修改时间
     *
     * @return UPDATE_TIME 修改时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置  修改时间
     *
     * @param updateTime 修改时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}