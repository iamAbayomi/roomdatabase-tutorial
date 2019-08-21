package com.appdot.io.roomdatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name ="productId")
    private int id;

    @ColumnInfo(name="productName")
    private String name;
    private int quantity;

    //Product model to store values


    public Product(String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
