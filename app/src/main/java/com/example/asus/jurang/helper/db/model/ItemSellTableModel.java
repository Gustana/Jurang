package com.example.asus.jurang.helper.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.asus.jurang.Const;

@Entity(tableName = Const.item_sell_table_name)
public class ItemSellTableModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Const.item_sell_table_id)
    private int itemSell_Id;

    @ColumnInfo(name = Const.item_sell_item_name)
    private String itemName;

    @ColumnInfo(name = Const.item_sell_item_code)
    private String itemCode;

    @ColumnInfo(name = Const.item_sell_item_price)
    private int itemPrice;

    @ColumnInfo(name = Const.item_sell_item_stock)
    private int itemStock;

    @ColumnInfo(name = Const.item_sell_item_quantity)
    private int itemQuantity;

    @ColumnInfo(name = Const.item_sell_item_total_price)
    private int itemTotalPrice;

    public ItemSellTableModel(String itemName, String itemCode, int itemPrice, int itemStock, int itemQuantity, int itemTotalPrice) {
        this.itemName = itemName;
        this.itemCode = itemCode;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.itemQuantity = itemQuantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public void setItemSell_Id(int itemSell_Id) {
        this.itemSell_Id = itemSell_Id;
    }

    public int getItemSell_Id() {
        return itemSell_Id;
    }


    public String getItemName() {
        return itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public int getItemStock() {
        return itemStock;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public int getItemTotalPrice() {
        return itemTotalPrice;
    }

    @Override
    public String toString() {
        return "ItemSellTableModel{" +
                "itemSell_Id=" + itemSell_Id +
                ", itemName='" + itemName + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemStock=" + itemStock +
                ", itemQuantity=" + itemQuantity +
                ", itemTotalPrice=" + itemTotalPrice +
                '}';
    }
}
