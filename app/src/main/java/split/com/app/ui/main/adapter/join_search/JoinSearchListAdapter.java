package split.com.app.ui.main.adapter.join_search;

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

import split.com.app.R;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.utils.Constants;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;

public class JoinSearchListAdapter extends RecyclerView.Adapter<JoinSearchListAdapter.SearchVH> {

    private static List<DataItem> dataItems;
    private final Context context;
    private JoinSearchListAdapter.ItemClickListener mListener;


    public void setOnCreateClickListener(JoinSearchListAdapter.ItemClickListener listener) {
        mListener = listener;
    }

    public JoinSearchListAdapter(Context appContext, List<DataItem> popularSubCategoryList) {
        this.context = appContext;
        this.dataItems = popularSubCategoryList;
    }

    @NonNull
    @Override
    public JoinSearchListAdapter.SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.join_search_list_item_design, parent, false);
        return new JoinSearchListAdapter.SearchVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JoinSearchListAdapter.SearchVH holder, int position) {
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

    public interface ItemClickListener {
        void onCreate(int position);
    }


    public static class SearchVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name, join;

        public SearchVH(@NonNull View itemView, JoinSearchListAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.join_search_icons);
            name = itemView.findViewById(R.id.join_search_name);
            join = itemView.findViewById(R.id.tv_join);


            join.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCreate(getAdapterPosition());
                    }
                }
            });
        }
    }//vh
}
