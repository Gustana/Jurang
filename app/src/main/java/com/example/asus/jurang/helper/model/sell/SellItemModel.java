package com.example.asus.jurang.helper.model.sell;

import java.io.Serializable;

public class SellItemModel implements Serializable {
    private String itemName;
    private int itemQuantity, itemPrice, itemTotalPrice;

    public SellItemModel(String itemName, int itemQuantity, int itemPrice, int itemTotalPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.itemTotalPrice = itemTotalPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getItemTotalPrice() {
        return itemTotalPrice;
    }

    @Override
    public String toString() {
        return "SellItemModel{" +
                "itemName='" + itemName + '\'' +
                ", itemQuantity=" + itemQuantity +
                ", itemPrice=" + itemPrice +
                ", itemTotalPrice=" + itemTotalPrice +
                '}';
    }
}
