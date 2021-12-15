package com.example.managestorephone.ui.sell;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.managestorephone.databinding.FragmentHistoryBinding;
import com.example.managestorephone.databinding.FragmentSellBinding;
import com.example.managestorephone.ui.history.HistoryViewModel;

public class SellFragment extends Fragment {

    private SellViewModel sellViewModel;
    private FragmentSellBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sellViewModel =
                new ViewModelProvider(this).get(SellViewModel.class);

        binding = FragmentSellBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSell;
        sellViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}