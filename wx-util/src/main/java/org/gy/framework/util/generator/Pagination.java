package org.gy.framework.util.generator;

/**
 * 功能描述：分页对象封装
 * 
 */
public class Pagination {
    /**
     * 页面记录最大数量
     */
    private static final Integer MAX_PAGE_SIZE     = 500;
    /**
     * 默认页面大小
     */
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 每页结果数量
     */
    private int                  pageSize          = DEFAULT_PAGE_SIZE;
    /**
     * 目前第几页，下标从1开始
     */
    private int                  pageNo            = 1;

    /**
     * 总共结果数量
     */
    private int                  total             = 0;
    /**
     * 返回结果封装集
     */
    private Object               items;

    public Pagination() {
        this(1, DEFAULT_PAGE_SIZE);
    }

    public Pagination(int pageNo, int pageSize) {
        this.pageNo = pageNo < 1 ? 1 : pageNo;
        if (pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize > MAX_PAGE_SIZE ? MAX_PAGE_SIZE : pageSize;
        }
    }

    /**
     * 获取起始索引
     * 
     * @return
     */
    public int getIndex() {
        return (getPageNo() - 1) * getPageSize();
    }

    /**
     * 获取总页数
     * 
     * @return
     */
    public int getTotalPages() {
        if (this.getTotal() > 0) {
            return this.getTotal() / this.getPageSize() + (this.getTotal() % this.getPageSize() == 0 ? 0 : 1);
        } else {
            return 0;
        }
    }

    /**
     * 设置总共结果数量
     * 
     * @param total 总共结果数量
     */
    public void setTotal(int total) {
        this.total = total;
        if (getPageNo() > getTotalPages()) {
            setPageNo(1);
        }
    }

    /**
     * 获取每页结果数量
     * 
     * @return pageSize 每页结果数量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页结果数量
     * 
     * @param pageSize 每页结果数量
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取目前第几页，下标从1开始
     * 
     * @return pageNo 目前第几页，下标从1开始
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置目前第几页，下标从1开始
     * 
     * @param pageNo 目前第几页，下标从1开始
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取总共结果数量
     * 
     * @return total 总共结果数量
     */
    public int getTotal() {
        return total;
    }

    /**
     * 获取返回结果封装集
     * 
     * @return items 返回结果封装集
     */
    public Object getItems() {
        return items;
    }

    /**
     * 设置返回结果封装集
     * 
     * @param items 返回结果封装集
     */
    public void setItems(Object items) {
        this.items = items;
    }

}
