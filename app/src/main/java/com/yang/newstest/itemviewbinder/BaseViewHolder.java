package com.yang.newstest.itemviewbinder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder <DB extends ViewDataBinding> extends RecyclerView.ViewHolder {
    DB binding;

    public DB getBinding() {

        return binding;
    }

    public BaseViewHolder(@NonNull DB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
