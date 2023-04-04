package com.book.bus.common;


import lombok.Data;

import java.io.Serializable;

/**
 * layui分页
 * @author coffee
 */
@Data
public class DataGridView implements Serializable {
    /**
     * 当前页
     */
    private long curr;
    /**
     * 每页页数
     */
    private long limit;
    /**
     * 总数
     */
    private long count;
    /**
     * 集合
     */
    private Object data;

    /**
     * 首页展示列表模式
     */
    private Integer viewType;

    /**
     *小说类型
     */
    private Integer type;

    public DataGridView(long curr, long limit, long count, Object data, Integer viewType, Integer type) {
        this.curr = curr;
        this.limit = limit;
        this.count = count;
        this.data = data;
        this.viewType = viewType;
        this.type = type;
    }

    public DataGridView(Integer curr, Integer limit, Integer count, Object data) {

        this.curr = curr;
        this.limit = limit;
        this.count = count;
        this.data = data;
    }

}
