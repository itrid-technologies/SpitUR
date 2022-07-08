package split.com.app.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import split.com.app.R;
import split.com.app.data.model.SubCategoryItem;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;
import split.com.app.utils.Svg;

public class HomeSectionSubCategoryAdapter extends RecyclerView.Adapter<HomeSectionSubCategoryAdapter.PopularVH> {

    private final List<SubCategoryItem> homeDataItem;
    String subCat_icon;

    private final Context context;

    private ItemClickListener mListener;


    public void setOnSubCategorySelectListener(ItemClickListener listener) {
        mListener = listener;
    }

    public HomeSectionSubCategoryAdapter(Split appContext, List<SubCategoryItem> subCategory, String icon) {
        this.context = appContext;
        this.homeDataItem = subCategory;
        this.subCat_icon = icon;
    }

    @NonNull
    @Override
    public HomeSectionSubCategoryAdapter.PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_list_items, parent, false);
        return new HomeSectionSubCategoryAdapter.PopularVH(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSectionSubCategoryAdapter.PopularVH holder, int position) {

        Svg.INSTANCE.loadUrl(Constants.IMG_PATH + subCat_icon, holder.icon);
        SubCategoryItem subCategoryItem = homeDataItem.get(position);
        holder.name.setText(subCategoryItem.getSubCatTitle());

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