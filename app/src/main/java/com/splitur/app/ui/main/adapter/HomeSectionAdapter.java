package com.splitur.app.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.HomeDataItem;
import com.splitur.app.utils.Split;

public class HomeSectionAdapter extends RecyclerView.Adapter<HomeSectionAdapter.ViewHolder> {

    private final List<HomeDataItem> homeDataItems;
    private final Context context;
    View adapter_view;
    private ItemClickListener mListener;



    public void setOnViewAllClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public HomeSectionAdapter(Context appContext, List<HomeDataItem> list, View view) {
        this.context = appContext;
        this.homeDataItems = list;
        this.adapter_view = view;
    }

    @NonNull
    @Override
    public HomeSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_section_design, parent, false);
        return new HomeSectionAdapter.ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionAdapter.ViewHolder holder, int position) {

        HomeDataItem dataItem = homeDataItems.get(position);
        holder.title.setText(dataItem.getTitle());
        if (dataItem.getSubCategory().size() > 0) {



            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            holder.list.setLayoutManager(layoutManager);
            HomeSectionSubCategoryAdapter adapter = new HomeSectionSubCategoryAdapter(Split.getAppContext(), dataItem.getSubCategory(),dataItem.getId());
            holder.list.setAdapter(adapter);

//            int spacingInPixels = context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._11sdp);
           // holder.list.addItemDecoration(new SpacingItemDecorator(11));

            adapter.setOnSubCategorySelectListener(position1 -> {
                Bundle bundle = new Bundle();
                bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(position1).getId()));
                bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(position1).getSubCatTitle()));
                Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
            });

        }


    }

    @Override
    public int getItemCount() {
        return homeDataItems.size();
    }

    public interface ItemClickListener {
        void onViewAllClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, view_all;
        RecyclerView list;


        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.section_title);
            list = (itemView).findViewById(R.id.section_categories);
            view_all = (itemView).findViewById(R.id.section_view_all);


            view_all.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onViewAllClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
