package com.tensquare.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/6/4 8:48 PM
 */
@Getter
@Setter
public class PageResponse<T> {

    private long total;

    private List<T> rows;

    public PageResponse(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResponse() {

    }

}
