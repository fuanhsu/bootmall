package com.grace.bootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderRequest {

    @NotEmpty
    List<BuyItem> buyItemList;

    public @NotEmpty List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(@NotEmpty List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
