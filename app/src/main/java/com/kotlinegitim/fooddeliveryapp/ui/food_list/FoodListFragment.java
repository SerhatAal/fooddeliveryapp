package com.kotlinegitim.fooddeliveryapp.ui.food_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kotlinegitim.fooddeliveryapp.R;
import com.kotlinegitim.fooddeliveryapp.databinding.FragmentFoodListBinding;
import com.kotlinegitim.fooddeliveryapp.entity.Food;
import com.kotlinegitim.fooddeliveryapp.ui.food_list.bottom_sheet.FoodDetailBottomSheetDialogFragment;
import com.kotlinegitim.fooddeliveryapp.utils.listeners.FoodDetailClickListener;
import com.kotlinegitim.fooddeliveryapp.utils.managers.BasketManager;

import java.util.ArrayList;
import java.util.List;

public class FoodListFragment extends Fragment implements FoodDetailClickListener {
    private FragmentFoodListBinding binding;
    private FoodListFragmentViewModel viewModel;
    private FoodListAdapter adapter = new FoodListAdapter(getContext(), new ArrayList<>(), this);
    private List<Food> foodList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodListBinding.inflate(inflater, container, false);

        viewModel.getBasketList();

        binding.rvFoodList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        binding.basketView.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_foodListFragment_to_basketFragment));

        viewModel.getFoodListLiveData().observe(getViewLifecycleOwner(), list -> {
            adapter = new FoodListAdapter(requireContext(), list, this);
            binding.rvFoodList.setAdapter(adapter);
            foodList = list;
        });

        viewModel.getBasketListLiveData().observe(getViewLifecycleOwner(), list -> {
            BasketManager.getInstance().setBasketList(list);
        });

        BasketManager.getInstance().getBasketLiveData().observe(getViewLifecycleOwner(), __ -> {
            adapter.notifyDataSetChanged();
            AppCompatTextView foodCount = binding.basketView.findViewById(R.id.text_view_food_count_basket);
            AppCompatTextView totalPrice = binding.basketView.findViewById(R.id.text_view_total_price);
            foodCount.setText(String.valueOf(BasketManager.getInstance().getBasketList().size()));
            totalPrice.setText(BasketManager.getInstance().getBasketListTotalPrice());
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FoodListFragmentViewModel.class);
    }

    @Override
    public void foodDetail(Food food) {
        FoodDetailBottomSheetDialogFragment fragment = new FoodDetailBottomSheetDialogFragment();
        fragment.setData(food);
        fragment.show(getChildFragmentManager(), "FoodDetailBottomSheetDialogFragment");
    }
}