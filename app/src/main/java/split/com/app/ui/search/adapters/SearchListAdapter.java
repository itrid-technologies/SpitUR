package split.com.app.ui.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import split.com.app.R;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchVH> {

    private final List<Integer> Icons;
    private final List<String> Names;
    private final Context context;


    public SearchListAdapter(Context context, List<Integer> popIcons, List<String> popNames) {
        this.context = context;
        this.Icons = popIcons;
        this.Names = popNames;
    }

    @NonNull
    @Override
    public SearchListAdapter.SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_design, parent, false);
        return new SearchListAdapter.SearchVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.SearchVH holder, int position) {
        final Integer currentIcon = Icons.get(position);
        final String currentName = Names.get(position);
        holder.icon.setImageResource(currentIcon);
        holder.name.setText(currentName);
    }

    @Override
    public int getItemCount() {
        return Names.size();
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
                Navigation.findNavController(view).navigate(R.id.action_search2_to_plans2);
            });
        }
    }//vh
}
