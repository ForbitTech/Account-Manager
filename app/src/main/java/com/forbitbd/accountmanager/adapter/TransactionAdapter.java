package com.forbitbd.accountmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.accountmanager.R;
import com.forbitbd.accountmanager.model.Transaction;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

  private Context context;
  private List<Transaction> transactionList;

    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false);
        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionAdapter.TransactionHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionHolder extends RecyclerView.ViewHolder {

        TextView date, price, name, product;

        public TransactionHolder( View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.customer_name);
            product = itemView.findViewById(R.id.products);
        }

        public void bind(Transaction transaction) {
            date.setText(transaction.getDate());
            price.setText(transaction.getPrice());
            name.setText(transaction.getName());
            product.setText(transaction.getProduct());
        }
    }
}
