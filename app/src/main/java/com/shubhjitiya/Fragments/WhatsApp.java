package com.shubhjitiya.Fragments;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayoutMediator;
import com.shubhjitiya.whatsinstasaver.R;
import com.shubhjitiya.whatsinstasaver.databinding.FragmentWhatsAppBinding;

import java.util.ArrayList;
import java.util.List;

@Keep
public class WhatsApp extends Fragment {
    private FragmentWhatsAppBinding binding;
    private ViewPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWhatsAppBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initView();
        return view;
    }
    private void initView() {
        setupViewPager(binding.viewPager);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    tab.setText(adapter.fragmentTitleList.get(position));
//                    tab.setCustomView(R.layout.custom_tab);
                }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(getContext())
                    .inflate(R.layout.custom_tab, null);
            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),
                getActivity().getLifecycle());
        adapter.addFragment(new ImageFragment(), "Images");
        adapter.addFragment(new VideoFragment(), "Videos");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }

    class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }
}