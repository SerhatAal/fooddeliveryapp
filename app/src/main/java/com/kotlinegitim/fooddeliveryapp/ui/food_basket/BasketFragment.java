package com.kotlinegitim.fooddeliveryapp.ui.food_basket;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.kotlinegitim.fooddeliveryapp.R;
import com.kotlinegitim.fooddeliveryapp.databinding.FragmentBasketBinding;
import com.kotlinegitim.fooddeliveryapp.entity.FoodBasket;
import com.kotlinegitim.fooddeliveryapp.utils.listeners.ClickListener;
import com.kotlinegitim.fooddeliveryapp.utils.managers.BasketManager;

public class BasketFragment extends Fragment implements ClickListener {
    private FragmentBasketBinding binding;
    private BasketFragmentViewModel viewModel;
    private final String userName = "serhatal";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasketBinding.inflate(inflater, container, false);

        checkEmptyState();
        binding.rvBasket.setLayoutManager(new LinearLayoutManager(requireContext()));
        BasketAdapter adapter = new BasketAdapter(requireContext(), BasketManager.getInstance().getBasketList(), this);
        binding.rvBasket.setAdapter(adapter);

        binding.textViewTotalPrice.setText(BasketManager.getInstance().getBasketListTotalPrice());

        viewModel.getBasketListLiveData().observe(getViewLifecycleOwner(), list -> BasketManager.getInstance().setBasketList(list));

        viewModel.getDeletedInBasketLiveData().observe(getViewLifecycleOwner(), isDeleted -> {
            if (isDeleted) {
                viewModel.getBasketList();
            } else {
                Snackbar.make(requireView(), "Can't delete item!", Snackbar.LENGTH_SHORT).show();
            }
        });

        BasketManager.getInstance().getBasketLiveData().observe(getViewLifecycleOwner(), __ -> {
            checkEmptyState();
            adapter.setList(BasketManager.getInstance().getBasketList());
            binding.textViewTotalPrice.setText(BasketManager.getInstance().getBasketListTotalPrice());
        });

        binding.iVBackButton.setOnClickListener(view -> requireActivity().onBackPressed());

        binding.buttonOrder.setOnClickListener(view -> {
            if (BasketManager.getInstance().getBasketList().size() > 0) {
                for (FoodBasket basket : BasketManager.getInstance().getBasketList()) {
                    viewModel.deleteFood(basket.getBasketId(), userName);
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_basketFragment_to_orderResultFragment);
            } else {
                Snackbar.make(requireView(), "Please add food to your basket !", Snackbar.LENGTH_LONG).show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BasketFragmentViewModel.class);
    }

    @Override
    public void onClickData(int basketId, String userName) {
        viewModel.deleteFood(basketId, userName);
    }

    public void checkEmptyState() {
        if (BasketManager.getInstance().getBasketList().isEmpty()) {
            binding.rvBasket.setVisibility(View.GONE);
            binding.tVEmptyState.setVisibility(View.VISIBLE);
            binding.iVEmptyState.setVisibility(View.VISIBLE);
        } else {
            binding.rvBasket.setVisibility(View.VISIBLE);
            binding.tVEmptyState.setVisibility(View.GONE);
            binding.iVEmptyState.setVisibility(View.GONE);
        }
    }
}