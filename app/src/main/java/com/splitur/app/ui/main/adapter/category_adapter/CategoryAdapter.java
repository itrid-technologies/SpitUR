package com.splitur.app.ui.main.adapter.category_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.home_categories.CategoryDataItems;
import com.splitur.app.utils.Constants;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private final List<CategoryDataItems> categoryDataItems;
    private final Context context;

    private CategoryAdapter.ItemClickListener mListener;



    public void setOnCategorySelectListener(CategoryAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public CategoryAdapter(Context appContext, List<CategoryDataItems> category_list) {
        this.context = appContext;
        this.categoryDataItems = category_list;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_list_item, parent, false);
        return new CategoryAdapter.CategoryVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryVH holder, int position) {

       try {

        CategoryDataItems current_item =  categoryDataItems.get(position);
//        Svg.INSTANCE.loadUrl(
//                Constants.IMG_PATH + current_item.getIcon(),
//                holder.icon
//        );
        holder.icon.setImageResource(
                Constants.getCategoryIcon(context,current_item.getId()));
        holder.name.setText(current_item.getTitle());

       }catch (NullPointerException e){
           Log.e("category", e.getMessage());
       }
    }

    @Override
    public int getItemCount() {
        return categoryDataItems.size();
    }

    public interface ItemClickListener{
        void onCategorySelect(int position);
    }

    public static class CategoryVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;

        public CategoryVH(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.catImage);
            name = itemView.findViewById(R.id.catName);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCategorySelect(getAdapterPosition());
                    }
                }
            });
        }
    }//vh
}
