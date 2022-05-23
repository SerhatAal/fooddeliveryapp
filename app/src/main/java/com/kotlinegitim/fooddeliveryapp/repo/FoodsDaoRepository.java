package com.kotlinegitim.fooddeliveryapp.repo;

import androidx.lifecycle.MutableLiveData;

import com.kotlinegitim.fooddeliveryapp.entity.CRUDAnswer;
import com.kotlinegitim.fooddeliveryapp.entity.Food;
import com.kotlinegitim.fooddeliveryapp.entity.FoodAnswer;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasketAnswer;
import com.kotlinegitim.fooddeliveryapp.retrofit.ApiUtils;
import com.kotlinegitim.fooddeliveryapp.retrofit.FoodsDaoInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodsDaoRepository {
    private final FoodsDaoInterface foodsDaoInterface;

    public FoodsDaoRepository() {
        foodsDaoInterface = ApiUtils.getFoodsDaoInterface();
    }

    public void getAllFoods(MutableLiveData<List<Food>> foodList) {
        foodsDaoInterface.allFoods().enqueue(new Callback<FoodAnswer>() {
            @Override
            public void onResponse(Call<FoodAnswer> call, Response<FoodAnswer> response) {
                List<Food> list = response.body().getFoodsList();
                foodList.setValue(list);
            }

            @Override
            public void onFailure(Call<FoodAnswer> call, Throwable t) {

            }
        });
    }

    public void getBasketList(String userName, MutableLiveData<List<FoodBasket>> basketList) {
        foodsDaoInterface.getBasketList(userName).enqueue(new Callback<FoodBasketAnswer>() {
            @Override
            public void onResponse(Call<FoodBasketAnswer> call, Response<FoodBasketAnswer> response) {
                List<FoodBasket> list = response.body().getFoodBasketList();
                basketList.setValue(list);
            }

            @Override
            public void onFailure(Call<FoodBasketAnswer> call, Throwable t) {
                basketList.setValue(new ArrayList<>());
            }
        });
    }

    public void addFoodBasket(String foodName, String foodImageName, int foodPrice, int foodAmount, String userName, MutableLiveData<Boolean> inBasket) {
        foodsDaoInterface.addFoodToBasket(foodName, foodImageName, foodPrice, foodAmount, userName).enqueue(new Callback<CRUDAnswer>() {
            @Override
            public void onResponse(Call<CRUDAnswer> call, Response<CRUDAnswer> response) {
                if (response.isSuccessful()) {
                    inBasket.setValue(true);
                } else {
                    inBasket.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<CRUDAnswer> call, Throwable t) {
                inBasket.setValue(false);
            }
        });
    }

    public void deleteFoodBasket(int id, String userName, MutableLiveData<Boolean> deletedFood) {
        foodsDaoInterface.foodDelete(id, userName).enqueue(new Callback<CRUDAnswer>() {
            @Override
            public void onResponse(Call<CRUDAnswer> call, Response<CRUDAnswer> response) {
                if (response.isSuccessful() && response.body().getSuccess() == 1)
                    deletedFood.setValue(true);
                else
                    deletedFood.setValue(false);
            }

            @Override
            public void onFailure(Call<CRUDAnswer> call, Throwable t) {
                deletedFood.setValue(false);
            }
        });
    }
}
