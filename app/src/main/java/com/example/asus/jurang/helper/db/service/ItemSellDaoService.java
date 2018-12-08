package com.example.asus.jurang.helper.db.service;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.helper.db.model.ItemSellTableModel;

import java.util.List;

@Dao
public interface ItemSellDaoService {

    @Query("SELECT * FROM " + Const.item_sell_table_name)
    List<ItemSellTableModel> getAllItemSellData();

    @Query("DELETE FROM " + Const.item_sell_table_name + " WHERE " + Const.item_sell_table_id + " = :id")
    void deleteItemById(int id);

    @Query("UPDATE " + Const.item_sell_table_name + " SET " + Const.item_sell_item_quantity + " = :quantity WHERE " + Const.item_sell_table_id + " = :id")
    void updateItemQuantityById(int quantity, int id);

    @Insert
    void insertItemSellData(ItemSellTableModel... itemSellTableModels);

    @Delete
    void deleteAllData(List<ItemSellTableModel> itemSellTableModelList);
}
