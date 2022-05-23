package com.kotlinegitim.fooddeliveryapp.ui.food_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kotlinegitim.fooddeliveryapp.entity.Food;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;
import com.kotlinegitim.fooddeliveryapp.repo.FoodsDaoRepository;

import java.util.List;

public class FoodListFragmentViewModel extends AndroidViewModel {
    private final FoodsDaoRepository foodsDaoRepository;
    private MutableLiveData<List<Food>> foodListLiveData;
    private MutableLiveData<List<FoodBasket>> basketListLiveData;

    public FoodListFragmentViewModel(@NonNull Application application) {
        super(application);
        foodsDaoRepository = new FoodsDaoRepository();
        loadFoods();
    }

    public void getBasketList() {
        foodsDaoRepository.getBasketList("serhatal", getBasketListLiveData());
    }

    public MutableLiveData<List<FoodBasket>> getBasketListLiveData() {
        if(basketListLiveData == null)
            basketListLiveData = new MutableLiveData<>();
        return basketListLiveData;
    }

    private void loadFoods() {
        foodsDaoRepository.getAllFoods(getFoodListLiveData());
    }

    public MutableLiveData<List<Food>> getFoodListLiveData() {
        if (foodListLiveData == null)
            foodListLiveData = new MutableLiveData<>();
        return foodListLiveData;
    }
}
