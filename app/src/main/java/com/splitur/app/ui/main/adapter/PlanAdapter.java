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

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.plans.PlanDataItem;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlansVH> {

    private final List<PlanDataItem> planDataItems;
    private final Context context;
    private ItemClickListener mListener;



    public void setOnPlanSelectListener(ItemClickListener listener) {
        mListener = listener;
    }


    public PlanAdapter(Context appContext, List<PlanDataItem> list) {
        this.context = appContext;
        this.planDataItems = list;
    }

    @NonNull
    @Override
    public PlanAdapter.PlansVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_list_item_design, parent, false);
        return new PlanAdapter.PlansVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.PlansVH holder, int position) {

        PlanDataItem dataItem = planDataItems.get(position);
        holder.layout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dataItem.getColor())));
        holder.join.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dataItem.getColor())));
//        Glide.with(context)
//                .load(Constants.IMG_PATH + dataItem.getIcon())
//                .placeholder(R.drawable.plan_icon)
//                .into(holder.icon);
        holder.icon_bg.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(dataItem.getColor())));
        holder.title.setText(dataItem.getName());
        holder.body.setText(dataItem.getDescription());
        holder.body.setTextColor(Color.parseColor(dataItem.getColor()));

    }

    @Override
    public int getItemCount() {
        return planDataItems.size();
    }

    public interface ItemClickListener{
        void onPlanSelect(int position);
    }

    public static class PlansVH extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public TextView title, body ;
        TextView join;
        ImageView icon_bg;
        ImageView icon;


        public PlansVH(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.planTitle);
            layout = itemView.findViewById(R.id.plan_stroke);
            icon = itemView.findViewById(R.id.plan_icon);
            icon_bg = itemView.findViewById(R.id.planbg);
            body = itemView.findViewById(R.id.planBody);
            join = itemView.findViewById(R.id.join);


            join.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onPlanSelect(getAdapterPosition());
                    }
                }
            });

        }
    }
}