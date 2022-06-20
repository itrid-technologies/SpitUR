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

    private final List<split.com.app.data.model.all_joined_groups.DataItem> dataItems;
    private final Context context;

    private AllJoinedGroupAdapter.ItemClickListener mListener;



    public void setOnJoinedClixkListener(AllJoinedGroupAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public AllJoinedGroupAdapter(Context appContext, List<split.com.app.data.model.all_joined_groups.DataItem> list) {
        this.context = appContext;
        this.dataItems = list;
    }

    @NonNull
    @Override
    public AllJoinedGroupAdapter.GroupVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_joined_list_design, parent, false);
        return new AllJoinedGroupAdapter.GroupVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllJoinedGroupAdapter.GroupVH holder, int position) {
        split.com.app.data.model.all_joined_groups.DataItem current_item =  dataItems.get(position);
        holder.name.setText(current_item.getGroup().getGroupTitle());
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
        public TextView name,open;

        public GroupVH(@NonNull View itemView, AllJoinedGroupAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.join_title);
            open = itemView.findViewById(R.id.open);

            open.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCategorySelect(getAdapterPosition());
                    }
                }
            });

        }
    }
}

