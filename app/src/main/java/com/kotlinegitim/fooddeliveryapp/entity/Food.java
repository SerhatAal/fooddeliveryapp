package com.kotlinegitim.fooddeliveryapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Food implements Serializable {
    @SerializedName("yemek_id")
    @Expose
    private int id;
    @SerializedName("yemek_adi")
    @Expose
    private String name;
    @SerializedName("yemek_resim_adi")
    @Expose
    private String imageName;
    @SerializedName("yemek_fiyat")
    @Expose
    private int price;

    public Food() {
    }

    public Food(int id, String name, String imageName, int price) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.price = price;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}