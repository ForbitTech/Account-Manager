package com.forbitbd.accountmanager.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.adapter.TransactionAdapter;
import com.forbitbd.accountmanager.model.Transaction;

import java.util.ArrayList;


public class TopTransactionFragment extends Fragment {

    private TransactionAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Transaction> transactionList;

    public TopTransactionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_top_transaction, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        transactionList = new ArrayList<>();
        transactionList.add(new Transaction("21/06/2021","432.00","Saimul Hoque","Meat"));
        transactionList.add(new Transaction("20/06/2021","655.00","Abid Ahmed","Fish"));
        transactionList.add(new Transaction("19/06/2021","520.00","Samim Ahmed","Chicken"));
        transactionList.add(new Transaction("18/06/2021","440.00","Abu Kashem","Dry Food"));
        transactionList.add(new Transaction("17/06/2021","850.00","Shadhin Sahriar","Vegetables"));

        adapter = new TransactionAdapter(getContext(), transactionList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
