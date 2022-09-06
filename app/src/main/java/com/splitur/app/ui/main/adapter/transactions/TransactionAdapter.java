package com.splitur.app.ui.main.adapter.transactions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.transaction.DataItem;
import com.splitur.app.utils.Constants;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private final List<DataItem> dataItems;
    private final Context context;


    public TransactionAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.dataItems = list;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_item_design, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {

        DataItem dataItem = dataItems.get(position);
        if (dataItem.getPaymentType().equalsIgnoreCase("debit")){
            holder.amount.setText( " -₹" + String.valueOf(dataItem.getAmount()));
        }else {
            holder.amount.setText( " +₹" + String.valueOf(dataItem.getAmount()));
        }

        holder.date.setText(Constants.getDate(dataItem.getCreatedAt()));

        if (dataItem.getGroupsPaymentTransactions().getSubCategory() != null) {

            holder.name.setText(dataItem.getGroupsPaymentTransactions().getSubCategory().getSubCatTitle());

            String url = Constants.IMG_PATH + dataItem.getGroupsPaymentTransactions().getSubCategory().getCategory().getIcon();


            // Svg.INSTANCE.loadUrl(url , holder.icon);
            holder.icon.setImageResource(
                    Constants.getCategoryIcon(context, dataItem.getGroupsPaymentTransactions().getSubCategory().getCategory().getId()));

        }
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date;
        TextView amount;
        ImageView icon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.payment_name);
            icon = itemView.findViewById(R.id.payment_item_icon);
            date = itemView.findViewById(R.id.payment_date);
            amount = itemView.findViewById(R.id.payment_amount);


        }
    }
}
