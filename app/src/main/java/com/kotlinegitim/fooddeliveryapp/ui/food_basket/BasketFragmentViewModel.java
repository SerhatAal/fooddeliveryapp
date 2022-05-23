package com.kotlinegitim.fooddeliveryapp.ui.food_basket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;
import com.kotlinegitim.fooddeliveryapp.repo.FoodsDaoRepository;

import java.util.List;

public class BasketFragmentViewModel extends AndroidViewModel {
    private final FoodsDaoRepository foodsDaoRepository = new FoodsDaoRepository();
    private MutableLiveData<List<FoodBasket>> basketListLiveData;
    private MutableLiveData<Boolean> inDeletedBasketLiveData;
    private MutableLiveData<Boolean> inBasketLiveData;


    public BasketFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void addBasket(String food_name,
                          String food_image_name,
                          int food_price,
                          int food_amount,
                          String user_name) {
        foodsDaoRepository.addFoodBasket(
                food_name,
                food_image_name,
                food_price,
                food_amount,
                user_name,
                getInBasketLiveData());
    }

    public MutableLiveData<Boolean> getInBasketLiveData() {
        if(inBasketLiveData == null)
            inBasketLiveData = new MutableLiveData<>();
        return inBasketLiveData;
    }

    public void deleteFood(int id, String userName) {
        foodsDaoRepository.deleteFoodBasket(id, userName, getDeletedInBasketLiveData());
    }

    public void getBasketList() {
        foodsDaoRepository.getBasketList("serhatal", getBasketListLiveData());
    }

    public MutableLiveData<List<FoodBasket>> getBasketListLiveData() {
        if(basketListLiveData == null)
            basketListLiveData = new MutableLiveData<>();
        return basketListLiveData;
    }

    public MutableLiveData<Boolean> getDeletedInBasketLiveData() {
        if(inDeletedBasketLiveData == null)
            inDeletedBasketLiveData = new MutableLiveData<>();
        return inDeletedBasketLiveData;
    }
}
