package split.com.app.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.data.model.SubCategoryItem;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;

public class HomeSectionSubCategoryAdapter extends RecyclerView.Adapter<HomeSectionSubCategoryAdapter.PopularVH> {

    private static List<SubCategoryItem> homeDataItem;
    String subCat_icon;

    private final Context context;



    public HomeSectionSubCategoryAdapter(Split appContext, List<SubCategoryItem> subCategory, String icon) {
        this.context = appContext;
        this.homeDataItem = subCategory;
        this.subCat_icon = icon;
    }

    @NonNull
    @Override
    public HomeSectionSubCategoryAdapter.PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_list_items, parent, false);
        return new HomeSectionSubCategoryAdapter.PopularVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionSubCategoryAdapter.PopularVH holder, int position) {

            Glide.with(context)
                    .load(Constants.IMG_PATH + subCat_icon)
                    .placeholder(R.color.blue)
                    .into(holder.icon);
            SubCategoryItem subCategoryItem = homeDataItem.get(position);
            holder.name.setText(subCategoryItem.getSubCatTitle());
            
    }

    @Override
    public int getItemCount() {
        return homeDataItem.size();
    }

    public static class PopularVH extends RecyclerView.ViewHolder {
        public CircleImageView icon;
        public TextView name, join;

        public PopularVH(@NonNull View itemView) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.popular_icons);
            name = itemView.findViewById(R.id.popular_name);
            join = itemView.findViewById(R.id.tv_join);

            join.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.action_home2_to_groupDetail);
            });

        }
    }//vh
}