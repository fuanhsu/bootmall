package com.grace.bootmall.util;

import java.util.List;

public class Page <T>{
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
