package com.framework.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页数据类
 *
 * @author : Jarvis
 * @date : 2018/6/1
 */
public class GridData<T> {
    /**
     * 当前页
     */
    private Integer pageNum = 1;
    /**
     * 每页显示的总条数
     */
    private Integer pageSize = 10;
    /**
     * 总条数
     */
    private Integer totalNum;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 是否有下一页
     */
    private Boolean isMore;
    /**
     * 分页结果
     */
    private List<T> items;

    public GridData() {
        super();
    }

    public GridData(PageInfo info) {
        this.totalNum = (int) info.getTotal();
        this.pageNum = info.getPageNum();
        this.totalPage = info.getPages();
        this.items = info.getList();
        this.isMore = info.getPageNum() >= info.getPages() ? false : true;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Boolean getIsMore() {
        return isMore;
    }

    public void setIsMore(Boolean isMore) {
        this.isMore = isMore;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}