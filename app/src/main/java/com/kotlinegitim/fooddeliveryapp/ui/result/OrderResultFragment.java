package com.kotlinegitim.fooddeliveryapp.ui.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kotlinegitim.fooddeliveryapp.R;
import com.kotlinegitim.fooddeliveryapp.databinding.FragmentOrderResultBinding;

public class OrderResultFragment extends Fragment {
    private FragmentOrderResultBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderResultBinding.inflate(inflater, container, false);

        binding.buttonHomepage.setOnClickListener(view -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_orderResultFragment_to_foodListFragment);
        });

        return binding.getRoot();
    }
}