package com.splitur.app.ui.main.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.splitur.app.R;
import com.splitur.app.data.model.HomeDataItem;
import com.splitur.app.utils.Constants;

import java.util.List;

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
        return new HomeSectionAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionAdapter.ViewHolder holder, int position) {

        try {

            HomeDataItem dataItem = homeDataItems.get(position);

            holder.title.setText(dataItem.getTitle());

            if (dataItem.getSubCategory().size() > 0) {
                holder.title.setVisibility(View.VISIBLE);
                holder.categoryView.setVisibility(View.VISIBLE);
                holder.view_all.setVisibility(View.VISIBLE);


                if (dataItem.getSubCategory().size() == 1) {
                    holder.catPart1.setVisibility(View.VISIBLE);
                    holder.catPart2.setVisibility(View.INVISIBLE);
                    holder.catPart3.setVisibility(View.INVISIBLE);

                    holder.icon1.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name1.setText(dataItem.getSubCategory().get(0).getSubCatTitle());


                    holder.join1.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(0).getId()));
                        bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(0).getSubCatTitle()));
                        Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                    });


                } else if (dataItem.getSubCategory().size() == 2) {
                    holder.catPart1.setVisibility(View.VISIBLE);
                    holder.catPart2.setVisibility(View.VISIBLE);
                    holder.catPart3.setVisibility(View.INVISIBLE);

                    holder.icon1.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name1.setText(dataItem.getSubCategory().get(0).getSubCatTitle());

                    holder.icon2.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name2.setText(dataItem.getSubCategory().get(1).getSubCatTitle());


                    holder.join1.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(0).getId()));
                        bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(0).getSubCatTitle()));
                        Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                    });


                    holder.join2.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(1).getId()));
                        bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(1).getSubCatTitle()));
                        Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                    });

                } else if (dataItem.getSubCategory().size() == 3) {
                    holder.catPart1.setVisibility(View.VISIBLE);
                    holder.catPart2.setVisibility(View.VISIBLE);
                    holder.catPart3.setVisibility(View.VISIBLE);

                    holder.icon1.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name1.setText(dataItem.getSubCategory().get(0).getSubCatTitle());

                    holder.icon2.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name2.setText(dataItem.getSubCategory().get(1).getSubCatTitle());

                    holder.icon3.setImageResource(
                            Constants.getCategoryIcon(context, dataItem.getId()));
                    holder.name3.setText(dataItem.getSubCategory().get(2).getSubCatTitle());


                    holder.join1.setOnClickListener(view -> {

                        if (Constants.isNewUser_Join) {
                            Navigation.findNavController(view).navigate(R.id.joinGroupFlow);
                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(0).getId()));
                            bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(0).getSubCatTitle()));
                            Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                        }
                    });


                    holder.join2.setOnClickListener(view -> {

                        if (Constants.isNewUser_Join) {
                            Navigation.findNavController(view).navigate(R.id.joinGroupFlow);
                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(1).getId()));
                            bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(1).getSubCatTitle()));
                            Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                        }
                    });


                    holder.join3.setOnClickListener(view -> {

                        if (Constants.isNewUser_Join) {
                            Navigation.findNavController(view).navigate(R.id.joinGroupFlow);
                        } else {

                            Bundle bundle = new Bundle();
                            bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(2).getId()));
                            bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(2).getSubCatTitle()));
                            Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
                        }
                    });

                }


//
//            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//            holder.list.setLayoutManager(layoutManager);
//            HomeSectionSubCategoryAdapter adapter = new HomeSectionSubCategoryAdapter(Split.getAppContext(), dataItem.getSubCategory(),dataItem.getId());
//            holder.list.setAdapter(adapter);

//            int spacingInPixels = context.getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._11sdp);
                // holder.list.addItemDecoration(new SpacingItemDecorator(11));

//            adapter.setOnSubCategorySelectListener(position1 -> {
//                Bundle bundle = new Bundle();
//                bundle.putString("join_sub_cat_id", String.valueOf(dataItem.getSubCategory().get(position1).getId()));
//                bundle.putString("join_sub_cat_title", String.valueOf(dataItem.getSubCategory().get(position1).getSubCatTitle()));
//                Navigation.findNavController(adapter_view).navigate(R.id.action_home2_to_groupDetail, bundle);
//            });

            } else {
                holder.categoryView.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
                holder.view_all.setVisibility(View.GONE);
            }

        } catch (NullPointerException e) {
            Log.e("Home", e.getMessage());
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
        ConstraintLayout catPart1, catPart2, catPart3, categoryView;

        public ImageView icon1, icon2, icon3;
        public TextView name1, name2, name3, join1, join2, join3;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.section_title);
//            list = (itemView).findViewById(R.id.section_categories);
            view_all = (itemView).findViewById(R.id.section_view_all);
            catPart1 = (itemView).findViewById(R.id.cat_part1);
            catPart2 = (itemView).findViewById(R.id.cat_part2);
            catPart3 = (itemView).findViewById(R.id.cat_part3);
            categoryView = (itemView).findViewById(R.id.subCatList);


            icon1 = itemView.findViewById(R.id.cat_part1_icons);
            name1 = itemView.findViewById(R.id.cat_part1_name);
            join1 = itemView.findViewById(R.id.cat_part1_join);

            icon2 = itemView.findViewById(R.id.cat_part2_icons);
            name2 = itemView.findViewById(R.id.cat_part2_name);
            join2 = itemView.findViewById(R.id.cat_part2_join);

            icon3 = itemView.findViewById(R.id.cat_part3_icons);
            name3 = itemView.findViewById(R.id.cat_part3_name);
            join3 = itemView.findViewById(R.id.cat_part3_join);

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
