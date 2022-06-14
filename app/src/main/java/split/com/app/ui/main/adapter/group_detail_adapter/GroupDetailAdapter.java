package split.com.app.ui.main.adapter.group_detail_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.group_detail.DataItem;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {

    private final List<DataItem> data;
    private final Context context;

    public GroupDetailAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public GroupDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_search_list_item_design, parent, false);
        return new GroupDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDetailAdapter.ViewHolder holder, int position) {

        DataItem dataItem = data.get(position);
        holder.title.setText(dataItem.getTitle());





    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.tv_username);

            itemView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.action_groupDetail_to_groupInformation);
            });

        }
    }
}
