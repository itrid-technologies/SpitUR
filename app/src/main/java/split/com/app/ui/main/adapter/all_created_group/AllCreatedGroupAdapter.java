package split.com.app.ui.main.adapter.all_created_group;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import split.com.app.R;
import split.com.app.data.model.all_created_groupx.DataItem;
import split.com.app.data.model.home_categories.CategoryDataItems;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;

public class AllCreatedGroupAdapter extends RecyclerView.Adapter<AllCreatedGroupAdapter.GroupVH> {

    private final List<DataItem> dataItems;
    private final Context context;

    private AllCreatedGroupAdapter.ItemClickListener mListener;


    public void setOnCreatedGroupClickListener(AllCreatedGroupAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public AllCreatedGroupAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.dataItems = list;
    }

    @NonNull
    @Override
    public AllCreatedGroupAdapter.GroupVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_created_group_design, parent, false);
        return new AllCreatedGroupAdapter.GroupVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCreatedGroupAdapter.GroupVH holder, int position) {
        DataItem current_item =  dataItems.get(position);
        holder.name.setText(current_item.getGroupTitle());
        int space = current_item.getSlots() - current_item.getTotalMembers();

        if (space == 0){
            holder.slots.setText("Full");
            holder.slots.setTextColor(Color.parseColor("#0FB900"));

        }else {
            holder.slots.setText((String.valueOf(space)) + " Slots Free");
            holder.slots.setTextColor(Color.parseColor("#FFDD72"));
        }

  

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
        public TextView name,slots;

        public GroupVH(@NonNull View itemView, AllCreatedGroupAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.group_title);
            slots = itemView.findViewById(R.id.space);


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
