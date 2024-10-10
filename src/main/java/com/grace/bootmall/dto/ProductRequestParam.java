package com.grace.bootmall.dto;


import com.grace.bootmall.constant.ProductCategory;

public class ProductRequestParam {
    private ProductCategory category;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;

    public ProductCategory getCategory() {
        return category;
    }

    public String getSearch() {
        return search;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getSort() {
        return sort;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
