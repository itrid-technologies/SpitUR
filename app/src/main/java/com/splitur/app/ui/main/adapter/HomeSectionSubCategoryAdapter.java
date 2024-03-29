package com.splitur.app.ui.main.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.SubCategoryItem;
import com.splitur.app.utils.Constants;
import com.splitur.app.utils.Split;

public class HomeSectionSubCategoryAdapter extends RecyclerView.Adapter<HomeSectionSubCategoryAdapter.PopularVH> {

    private final List<SubCategoryItem> homeDataItem;
    int subCat_icon;
    private int width,height;
    private final Context context;

    private ItemClickListener mListener;



    public void setOnSubCategorySelectListener(ItemClickListener listener) {
        mListener = listener;
    }

    public HomeSectionSubCategoryAdapter(Split appContext, List<SubCategoryItem> subCategory, int icon) {
        this.context = appContext;
        this.homeDataItem = subCategory;
        this.subCat_icon = icon;
    }

    @NonNull
    @Override
    public HomeSectionSubCategoryAdapter.PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_list_items, parent, false);

//        height = parent.getMeasuredHeight() / 4;
//        width = parent.getMeasuredWidth() / 3;
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0,0,12,0);
//        view.setLayoutParams(params);


        return new HomeSectionSubCategoryAdapter.PopularVH(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionSubCategoryAdapter.PopularVH holder, int position) {

        try {

//        Svg.INSTANCE.loadUrl(Constants.IMG_PATH + subCat_icon, holder.icon);
        holder.icon.setImageResource(
                Constants.getCategoryIcon(context,subCat_icon));
        SubCategoryItem subCategoryItem = homeDataItem.get(position);
        holder.name.setText(subCategoryItem.getSubCatTitle());

        }catch (NullPointerException e){
            Log.e("home_section", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return homeDataItem.size();
    }

    public interface ItemClickListener {
        void onSubCategorySelect(int position);
    }

    public static class PopularVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name, join;

        public PopularVH(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.popular_icons);
            name = itemView.findViewById(R.id.popular_name);
            join = itemView.findViewById(R.id.tv_join);

            join.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onSubCategorySelect(getAdapterPosition());
                    }
                }
            });

        }
    }//vh
}