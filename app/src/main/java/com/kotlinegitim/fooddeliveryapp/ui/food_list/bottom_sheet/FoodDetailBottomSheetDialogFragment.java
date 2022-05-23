package com.kotlinegitim.fooddeliveryapp.ui.food_list.bottom_sheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.kotlinegitim.fooddeliveryapp.R;
import com.kotlinegitim.fooddeliveryapp.databinding.DialogFragmentBottomsheetFoodDetailBinding;
import com.kotlinegitim.fooddeliveryapp.entity.Food;
import com.kotlinegitim.fooddeliveryapp.ui.food_basket.BasketFragmentViewModel;
import com.kotlinegitim.fooddeliveryapp.utils.managers.BasketManager;
import com.squareup.picasso.Picasso;

public class FoodDetailBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private DialogFragmentBottomsheetFoodDetailBinding binding;
    private Food food;
    private BasketFragmentViewModel viewModel;
    private final String userName = "serhatal";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BasketFragmentViewModel.class);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogFragmentBottomsheetFoodDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tVFoodName.setText(food.getName());
        binding.tVFoodPrice.setText(food.getPrice() + " ₺");

        String foodImageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/" + food.getImageName();
        Picasso.get().load(foodImageUrl).into(binding.iVFood);

        if (BasketManager.getInstance().hasItem(food.getName())) {
            int foodCount = BasketManager.getInstance().basketFoodCount(food.getName());
            binding.editTextFoodAmount.setText(String.valueOf(foodCount));
        } else {
            binding.editTextFoodAmount.setText("1");
        }

        binding.buttonAdd.setOnClickListener(view1 -> {
            String amount = binding.editTextFoodAmount.getText().toString();
            if (amount.isEmpty()) {
                amount = "0";
            }
            int t = Integer.parseInt(amount);
            binding.editTextFoodAmount.setText(String.valueOf(t + 1));
        });

        binding.buttonSubstract.setOnClickListener(view1 -> {
            String amount = binding.editTextFoodAmount.getText().toString();
            int foodAmount = 1;
            if (amount.isEmpty()) {
                amount = "1";
            }

            if (Integer.parseInt(amount) > 1) {
                foodAmount = Integer.parseInt(amount) - 1;
            }

            binding.editTextFoodAmount.setText(String.valueOf(foodAmount));
        });

        binding.buttonAddBasket.setOnClickListener(view1 -> {
            if (BasketManager.getInstance().hasItem(food.getName())) {
                viewModel.deleteFood(BasketManager.getInstance().getBasketId(food.getName()), userName);
            } else {
                viewModel.addBasket(
                        food.getName(),
                        food.getImageName(),
                        food.getPrice(),
                        Integer.parseInt(binding.editTextFoodAmount.getText().toString()),
                        userName);
            }
        });

        viewModel.getInBasketLiveData().observe(getViewLifecycleOwner(), inBasket -> {
            if (inBasket) {
                viewModel.getBasketList();
            } else {
                Snackbar.make(requireView(), "Please add food to your basket", Snackbar.LENGTH_SHORT).show();
            }
        });

        viewModel.getBasketListLiveData().observe(getViewLifecycleOwner(), list -> {
            BasketManager.getInstance().setBasketList(list);
            dismiss();
        });

        viewModel.getDeletedInBasketLiveData().observe(getViewLifecycleOwner(), __ -> {
            viewModel.addBasket(
                    food.getName(),
                    food.getImageName(),
                    food.getPrice(),
                    Integer.parseInt(binding.editTextFoodAmount.getText().toString()),
                    userName);
        });
    }

    public void setData(Food food) {
        this.food = food;
    }
}
