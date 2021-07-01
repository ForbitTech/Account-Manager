package com.forbitbd.accountmanager.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class DashboardTransactionFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TopTransactionFragment topTransactions;
    private RecentTransactionsFragment recentTransactions;

    public DashboardTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_transaction, container, false);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpager);
        topTransactions  = new TopTransactionFragment();
        recentTransactions = new RecentTransactionsFragment();
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),0);
        viewPagerAdapter.addFragment(topTransactions, "Top 5 Transaction");
        viewPagerAdapter.addFragment(recentTransactions, " Recent Transaction");
        viewPager.setAdapter(viewPagerAdapter);

        return view;
    }
}