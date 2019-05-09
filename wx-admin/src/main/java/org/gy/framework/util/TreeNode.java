package org.gy.framework.util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    /**
     * 节点Id
     */
    private String         id;
    /**
     * 节点名称
     */
    private String         text;
    /**
     * 节点状态，'open' 或 'closed'
     */
    private String         state    = "open";
    /**
     * 是否被选中
     */
    private boolean        checked  = false;
    /**
     * 子节点列表
     */
    private List<TreeNode> children = new ArrayList<TreeNode>();

    /**
     * url链接
     */
    private String         url;

    /**
     * 父节点ID
     * 
     * @mbggenerated
     */
    private Long           parentId;

    /**
     * 节点描述
     * 
     * @mbggenerated
     */
    private String         description;

    /**
     * 状态，0未启用，1启用
     * 
     * @mbggenerated
     */
    private Short          status;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
