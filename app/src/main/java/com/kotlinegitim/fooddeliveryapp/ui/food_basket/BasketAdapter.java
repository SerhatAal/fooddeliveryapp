package com.kotlinegitim.fooddeliveryapp.ui.food_basket;


import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.kotlinegitim.fooddeliveryapp.R;
import com.kotlinegitim.fooddeliveryapp.databinding.CardviewBasketBinding;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;
import com.kotlinegitim.fooddeliveryapp.utils.listeners.ClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.CardViewHolder> {
    private final Context mContext;
    private List<FoodBasket> foodBasketList;
    private final ClickListener listener;

    public BasketAdapter(Context mContext, List<FoodBasket> foodBasketList, ClickListener listener) {
        this.mContext = mContext;
        this.foodBasketList = foodBasketList;
        this.listener = listener;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private CardviewBasketBinding binding;

        public CardViewHolder(CardviewBasketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        CardviewBasketBinding binding = CardviewBasketBinding.inflate(layoutInflater, parent, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        FoodBasket foodBasket = foodBasketList.get(position);
        CardviewBasketBinding b = holder.binding;

        b.textViewFoodNameBasket.setText(foodBasket.getFoodName());
        b.textViewFoodPriceBasket.setText(foodBasket.getFoodPrice() * foodBasket.getFoodOrderAmount() + " ₺ ");

        String foodImageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/" + foodBasket.getFoodImageName();
        Picasso.get().load(foodImageUrl).into(b.imageViewFoodBasket);

        b.tVAmount.setText(foodBasket.getFoodOrderAmount() + "x");

        b.imageViewDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(R.string.delete_are_you_sure)
                    .setPositiveButton(R.string.delete_positive,
                            (dialogInterface, i) -> listener.onClickData(foodBasket.getBasketId(), foodBasket.getUserName()))
                    .setNegativeButton(R.string.cancel_delete, (dialogInterface, i) -> {
                    }).show();
        });
    }

    @Override
    public int getItemCount() {
        return foodBasketList.size();
    }

    public void setList(List<FoodBasket> list) {
        this.foodBasketList = list;
        notifyDataSetChanged();
    }
}
