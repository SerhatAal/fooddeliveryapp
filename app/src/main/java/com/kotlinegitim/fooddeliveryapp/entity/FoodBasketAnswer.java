package com.kotlinegitim.fooddeliveryapp.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodBasketAnswer {
    @SerializedName("sepet_yemekler")
    @Expose
    private List<FoodBasket> foodBasketList;
    @SerializedName("success")
    @Expose
    private int success;

    public FoodBasketAnswer() {
    }

    public FoodBasketAnswer(List<FoodBasket> foodBasketList, int success) {
        this.foodBasketList = foodBasketList;
        this.success = success;
    }

    public List<FoodBasket> getFoodBasketList() {
        return foodBasketList;
    }

    public void setFoodBasketList(List<FoodBasket> foodBasketList) {
        this.foodBasketList = foodBasketList;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
