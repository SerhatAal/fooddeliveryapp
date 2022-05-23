package com.kotlinegitim.fooddeliveryapp.ui.food_list;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kotlinegitim.fooddeliveryapp.databinding.CardviewFoodListBinding;
import com.kotlinegitim.fooddeliveryapp.entity.Food;
import com.kotlinegitim.fooddeliveryapp.utils.listeners.FoodDetailClickListener;
import com.kotlinegitim.fooddeliveryapp.utils.managers.BasketManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.CardViewHolder> implements Filterable {
    private final Context mContext;
    private List<Food> foodList = new ArrayList<>();
    private List<Food> foodListFiltered = new ArrayList<>();
    private final FoodDetailClickListener listener;

    public FoodListAdapter(Context mContext, List<Food> foodList, FoodDetailClickListener listener) {
        this.mContext = mContext;
        this.foodList = foodList;
        this.foodListFiltered = foodList;
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    foodListFiltered = foodList;
                } else {
                    List<Food> filteredList = new ArrayList<>();
                    for (Food food : foodList) {
                        if (food.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(food);
                        }
                    }
                    foodListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = foodListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                foodListFiltered = (ArrayList<Food>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private CardviewFoodListBinding binding;

        public CardViewHolder(CardviewFoodListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        CardviewFoodListBinding binding = CardviewFoodListBinding.inflate(layoutInflater, parent, false);
        return new CardViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Food food = foodListFiltered.get(position);
        CardviewFoodListBinding b = holder.binding;
        b.textViewFoodNameBasket.setText(food.getName());
        b.textViewFoodPriceBasket.setText(food.getPrice() + " ₺");

        String foodImageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/" + food.getImageName();
        Picasso.get().load(foodImageUrl).into(b.imageViewFoodBasket);

        b.foodCard.setOnClickListener(view -> {
            listener.foodDetail(food);
        });

        if (BasketManager.getInstance().hasItem(food.getName())) {
            b.cardBasketItem.setVisibility(View.VISIBLE);
            b.textViewFoodCountBasket.setText(String.valueOf(BasketManager.getInstance().basketFoodCount(food.getName())));
        } else {
            b.cardBasketItem.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return foodListFiltered.size();
    }
}
