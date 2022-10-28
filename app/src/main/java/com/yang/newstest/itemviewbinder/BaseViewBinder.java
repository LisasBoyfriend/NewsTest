package com.yang.newstest.itemviewbinder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.drakeet.multitype.ItemViewBinder;

public abstract class BaseViewBinder <T, DB extends ViewDataBinding> extends ItemViewBinder<T, BaseViewHolder<DB>> {
    private int layoutId = 0;

    public abstract int setLayoutId();

    public BaseViewBinder(){
        layoutId = setLayoutId();
    }

    @NonNull
    @Override
    public BaseViewHolder<DB> onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        DB binding = DataBindingUtil.inflate(layoutInflater, layoutId, viewGroup, false);

        return new BaseViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<DB> dbBaseViewHolder, T t) {

    }
}
