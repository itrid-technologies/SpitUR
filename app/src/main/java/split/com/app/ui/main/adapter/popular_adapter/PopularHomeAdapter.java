package split.com.app.ui.main.adapter.popular_adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.SubCategoryItem;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;

public class PopularHomeAdapter extends RecyclerView.Adapter<PopularHomeAdapter.PopularVH> {

    private static List<DataItem> dataItems;
    boolean status = false;

    private final Context context;


    public PopularHomeAdapter(Split appContext, List<DataItem> popularSubCategoryList) {
        this.context = appContext;
        this.dataItems = popularSubCategoryList;
    }



    @NonNull
    @Override
    public PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_list_items, parent, false);
        return new PopularVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularVH holder, int position) {


            final DataItem current_data = dataItems.get(position);
            if (current_data.getCategory() != null){
                Glide.with(context)
                        .load(Constants.IMG_PATH + current_data.getCategory().getIcon())
                        .placeholder(R.color.images_placeholder)
                        .into(holder.icon);
            }
            holder.name.setText(current_data.getTitle());

    }

    @Override
    public int getItemCount() {
        //return dataItems.size();
        int size = dataItems.size();
        // Return at most 5 items from the ArrayList
        return (Math.min(size, 3));
    }

    public static class PopularVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name, join;

        public PopularVH(@NonNull View itemView) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.popular_icons);
            name = itemView.findViewById(R.id.popular_name);
            join = itemView.findViewById(R.id.tv_join);

            join.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("join_sub_cat_id",String.valueOf(dataItems.get(getAdapterPosition()).getId()));//absolute adopter position
                bundle.putString("join_sub_cat_title",String.valueOf(dataItems.get(getAdapterPosition()).getTitle()));

                Navigation.findNavController(view).navigate(R.id.action_home2_to_groupDetail,bundle);
            });

        }
    }//vh
}