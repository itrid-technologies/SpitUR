package com.splitur.app.ui.main.adapter.faq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.faq.DataItem;

public class FaqListAdapter extends RecyclerView.Adapter<FaqListAdapter.FaqVH> {

    private final List<DataItem> faqListData;
    private final Context context;


    public FaqListAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.faqListData = list;
    }

    @NonNull
    @Override
    public FaqListAdapter.FaqVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_list_item_design, parent, false);
        return new FaqListAdapter.FaqVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqListAdapter.FaqVH holder, int position) {

        try {

        DataItem faqQuery = faqListData.get(position);
        holder.query.setText(faqQuery.getQuestion());
        holder.answer.setText(faqQuery.getAnswer());

        holder.view.setOnClickListener(view -> {
            String status = holder.view.getTag().toString().trim();
            if (status.equalsIgnoreCase("closed")){
                holder.view.setImageResource(R.drawable.ic_open_view);
                holder.layout.setVisibility(View.VISIBLE);
                holder.view.setTag("opened");
            }else {
                holder.view.setImageResource(R.drawable.ic_close_view);
                holder.layout.setVisibility(View.GONE);
                holder.view.setTag("closed");
            }
        });

        }catch (NullPointerException e){
            Log.e("faq", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return faqListData.size();
    }

    public static class FaqVH extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView query, answer;
        public ImageView view;

        public FaqVH(@NonNull View itemView) {
            super(itemView);
            //find views
            query = itemView.findViewById(R.id.query);
            answer = itemView.findViewById(R.id.queryAnswer);

            layout = itemView.findViewById(R.id.queryResponse);
            view = itemView.findViewById(R.id.closeView);

        }
    }
}
