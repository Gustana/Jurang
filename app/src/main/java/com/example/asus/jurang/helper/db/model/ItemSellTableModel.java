package com.example.asus.jurang.helper.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.asus.jurang.Const;

@Entity (tableName = Const.item_sell_table_name)
public class ItemSellDbModel {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = Const.item_sell_table_id)
    private int itemSellId;

    @ColumnInfo (name = Const.item_sell_item_name)
    private String itemName;

    @ColumnInfo (name = Const.item_sell_item_price)
    private int itemPrice;

    @ColumnInfo (name = Const.item_sell_item_quantity)
    private int itemQuantity;

    @ColumnInfo (name = Const.item_sell_item_total_price)
    private int itemTotalPrice;

    public int getItemSellId() {
        return itemSellId;
    }

    public void setItemSellId(int itemSellId) {
        this.itemSellId = itemSellId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(int itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }
}
