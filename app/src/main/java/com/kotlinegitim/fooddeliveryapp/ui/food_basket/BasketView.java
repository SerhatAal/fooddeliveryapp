package com.kotlinegitim.fooddeliveryapp.ui.food_basket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.kotlinegitim.fooddeliveryapp.databinding.ViewBasketBinding;

public class BasketView extends LinearLayout {
    private ViewBasketBinding binding;
    public BasketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        binding = ViewBasketBinding.inflate(LayoutInflater.from(context), this, false);
        addView(binding.getRoot());
    }
}
