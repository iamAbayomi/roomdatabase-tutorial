package com.appdot.io.roomdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Product.class}, version = 1)
public abstract class ProductRoomDatabase
      extends RoomDatabase {

    public  abstract ProductDao productDao();

    private static  ProductRoomDatabase INSTANCE;

    static ProductRoomDatabase getDatabase(final Context context){
         if(INSTANCE == null){
                synchronized (ProductRoomDatabase.class){
                    if(INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ProductRoomDatabase.class,
                                "product_database").build();
                    }

                }

            }

            return INSTANCE;
    }

}


