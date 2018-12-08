package com.example.asus.jurang.helper.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.asus.jurang.Const;
import com.example.asus.jurang.helper.db.model.ItemSellTableModel;
import com.example.asus.jurang.helper.db.service.ItemSellDaoService;

@Database(entities = {ItemSellTableModel.class}, version = 1, exportSchema = false)
public abstract class DbHolder extends RoomDatabase {

    private static DbHolder instance;

    public abstract ItemSellDaoService itemSellDaoService();

    public static DbHolder getDbHolderInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DbHolder.class, Const.item_sell_db_name)
                    .build();
        }
        return instance;
    }

}
