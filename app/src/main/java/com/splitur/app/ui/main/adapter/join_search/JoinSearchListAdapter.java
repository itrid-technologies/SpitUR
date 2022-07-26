package com.splitur.app.ui.main.adapter.join_search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.popular_subcategory.DataItem;
import com.splitur.app.utils.Constants;

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
        if (current_data.getCategory() != null) {
            holder.icon.setImageResource(
                    Constants.getCategoryIcon(context,current_data.getCategory().getId()));
        }
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
