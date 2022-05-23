package com.kotlinegitim.fooddeliveryapp.utils.managers;

import androidx.lifecycle.MutableLiveData;

import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BasketManager {

    private static BasketManager basketManager;
    private List<FoodBasket> basketList = new ArrayList<>();
    private MutableLiveData<Boolean> basketLiveData;

    public static BasketManager getInstance() {
        if (basketManager == null)
            basketManager = new BasketManager();
        return basketManager;
    }

    public MutableLiveData<Boolean> getBasketLiveData() {
        if (basketLiveData == null)
            basketLiveData = new MutableLiveData<>();
        return basketLiveData;
    }

    public void setBasketList(List<FoodBasket> basketList) {
        this.basketList = basketList;

        basketLiveData.postValue(true);
    }

    public List<FoodBasket> getBasketList() {
        return basketList;
    }

    public String getBasketListTotalPrice() {
        int totalAmount = 0;
        for (FoodBasket basket : basketList) {
            totalAmount += basket.getFoodOrderAmount() * basket.getFoodPrice();
        }
        return totalAmount + " ₺ ";
    }

    public boolean hasItem(String foodName) {
        for (FoodBasket basket : basketList) {
            if (Objects.equals(foodName, basket.getFoodName())) {
                return true;
            }
        }
        return false;
    }

    public int basketFoodCount(String foodName) {
        for (FoodBasket basket : basketList) {
            if (Objects.equals(foodName, basket.getFoodName())) {
                return basket.getFoodOrderAmount();
            }
        }
        return 0;
    }

    public int getBasketId(String foodName) {
        for (FoodBasket basket : basketList) {
            if (Objects.equals(foodName, basket.getFoodName())) {
                return basket.getBasketId();
            }
        }
        return 0;
    }
}