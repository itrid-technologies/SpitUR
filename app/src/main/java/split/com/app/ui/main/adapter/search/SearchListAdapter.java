package split.com.app.ui.main.adapter.search;

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
import com.google.android.material.badge.BadgeUtils;

import java.util.List;

import split.com.app.R;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchVH> {

    private static List<DataItem> dataItems;
    private final Context context;


    public SearchListAdapter(Context appContext, List<DataItem> popularSubCategoryList) {
        this.context = appContext;
        this.dataItems = popularSubCategoryList;
    }

    @NonNull
    @Override
    public SearchListAdapter.SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_design, parent, false);
        return new SearchListAdapter.SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.SearchVH holder, int position) {
        final DataItem current_data = dataItems.get(position);
        Glide.with(context)
                .load(Constants.IMG_PATH + current_data.getCategory().getIcon())
                .placeholder(R.color.blue)
                .into(holder.icon);
        holder.name.setText(current_data.getTitle());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }



    public static class SearchVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;

        public SearchVH(@NonNull View itemView) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.search_icons);
            name = itemView.findViewById(R.id.search_name);

            itemView.setOnClickListener(view -> {
                MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
                pm.saveData(Split.getAppContext(), "SUB_CATEGORY_ID", String.valueOf(dataItems.get(getAbsoluteAdapterPosition()).getId()));
                Navigation.findNavController(view).navigate(R.id.action_search2_to_plans2);
            });
        }
    }//vh
}
