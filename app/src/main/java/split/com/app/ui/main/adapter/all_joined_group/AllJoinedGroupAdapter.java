package split.com.app.ui.main.adapter.all_joined_group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import split.com.app.R;
import split.com.app.data.model.all_created_groupx.DataItem;

public class AllJoinedGroupAdapter extends RecyclerView.Adapter<AllJoinedGroupAdapter.GroupVH> {

    private final List<DataItem> dataItems;
    private final Context context;

    private AllJoinedGroupAdapter.ItemClickListener mListener;



    public void setOnCategorySelectListener(AllJoinedGroupAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public AllJoinedGroupAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.dataItems = list;
    }

    @NonNull
    @Override
    public AllJoinedGroupAdapter.GroupVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_created_group_design, parent, false);
        return new AllJoinedGroupAdapter.GroupVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllJoinedGroupAdapter.GroupVH holder, int position) {
        DataItem current_item =  dataItems.get(position);
        holder.name.setText(current_item.getGroupTitle());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public interface ItemClickListener{
        void onCategorySelect(int position);
    }

    public static class GroupVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;

        public GroupVH(@NonNull View itemView, AllJoinedGroupAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.catName);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCategorySelect(getAdapterPosition());
                    }
                }
            });

        }
    }
}

