package com.errands.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private List<Fragment> views;
    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> views) {
        super(fragmentManager, lifecycle);
        this.views=views;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return views.get(position);
    }

    @Override
    public int getItemCount() {
        return views.size();
    }
}
