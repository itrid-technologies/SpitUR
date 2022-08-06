package com.splitur.app.ui.main.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.splitur.app.R;
import com.splitur.app.data.model.otp_request.DataItem;
import com.splitur.app.data.model.plans.PlanDataItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtpRequestsAdapter extends RecyclerView.Adapter<OtpRequestsAdapter.ViewHolder> {

    private final List<DataItem> data;
    private final Context context;
    private ItemClickListener mListener;



    public void setOnRequestSelectListener(ItemClickListener listener) {
        mListener = listener;
    }


    public OtpRequestsAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public OtpRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.otp_request_list_item_design, parent, false);
        return new OtpRequestsAdapter.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OtpRequestsAdapter.ViewHolder holder, int position) {

      DataItem dataItem = data.get(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onRequestSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;


        public ViewHolder(@NonNull View itemView, OtpRequestsAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            imageView = itemView.findViewById(R.id.otp_list_image);

            imageView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onRequestSelect(getAdapterPosition());
                    }
                }
            });

        }
    }
}

