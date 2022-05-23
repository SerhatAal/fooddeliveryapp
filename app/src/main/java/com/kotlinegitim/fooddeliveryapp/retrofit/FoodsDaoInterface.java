package com.kotlinegitim.fooddeliveryapp.retrofit;

import com.kotlinegitim.fooddeliveryapp.entity.CRUDAnswer;
import com.kotlinegitim.fooddeliveryapp.entity.FoodAnswer;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasketAnswer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodsDaoInterface {
    @GET("yemekler/tumYemekleriGetir.php")
    Call<FoodAnswer> allFoods();

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    Call<CRUDAnswer> addFoodToBasket(@Field("yemek_adi") String foodName,
                                     @Field("yemek_resim_adi") String foodImageName,
                                     @Field("yemek_fiyat") int foodPrice,
                                     @Field("yemek_siparis_adet") int foodAmount,
                                     @Field("kullanici_adi") String userName);

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    Call<FoodBasketAnswer> getBasketList(@Field("kullanici_adi") String userName);

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    Call<CRUDAnswer> foodDelete(@Field("sepet_yemek_id") int foodId,
                                @Field("kullanici_adi") String userName);
}
