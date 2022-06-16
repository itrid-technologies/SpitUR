package split.com.app.ui.main.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.plans.PlanDataItem;
import split.com.app.ui.main.adapter.popular_adapter.PopularHomeAdapter;
import split.com.app.utils.Split;

public class HomeSectionAdapter extends RecyclerView.Adapter<HomeSectionAdapter.ViewHolder> {

    private final List<HomeDataItem> homeDataItems;
    private final Context context;

    public HomeSectionAdapter(Context appContext, List<HomeDataItem> list) {
        this.context = appContext;
        this.homeDataItems = list;
    }

    @NonNull
    @Override
    public HomeSectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_section_design, parent, false);
        return new HomeSectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionAdapter.ViewHolder holder, int position) {

        HomeDataItem dataItem = homeDataItems.get(position);
        holder.title.setText(dataItem.getTitle());
        if (dataItem.getSubCategory().size() > 0){
            LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
            holder.list.setLayoutManager(layoutManager);
            HomeSectionSubCategoryAdapter adapter = new HomeSectionSubCategoryAdapter(Split.getAppContext(), dataItem.getSubCategory(), dataItem.getIcon());
            holder.list.setAdapter(adapter);
        }



    }

    @Override
    public int getItemCount() {
        return homeDataItems.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        RecyclerView list;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.section_title);
            list = (itemView).findViewById(R.id.section_categories);


        }
    }
}
