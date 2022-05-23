package com.kotlinegitim.fooddeliveryapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodAnswer {
    @SerializedName("yemekler")
    @Expose
    private List<Food> foodList;
    @SerializedName("success")
    @Expose
    private int success;

    public FoodAnswer() {
    }

    public FoodAnswer(List<Food> foodList, int success) {
        this.foodList = foodList;
        this.success = success;
    }

    public List<Food> getFoodsList() {
        return foodList;
    }

    public void setFoodsList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
