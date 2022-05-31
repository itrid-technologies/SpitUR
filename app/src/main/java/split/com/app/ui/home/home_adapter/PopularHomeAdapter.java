package split.com.app.ui.home.home_adapter;

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

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;

public class PopularHomeAdapter extends RecyclerView.Adapter<PopularHomeAdapter.PopularVH> {

    private final List<Integer> Icons;
    private final List<String> Names;
    private final Context context;


    public PopularHomeAdapter(Context context , List<Integer> popIcons, List<String> popNames) {
        this.context = context;
        this.Icons = popIcons;
        this.Names = popNames;
    }

    @NonNull
    @Override
    public PopularVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_popular_list_items, parent, false);
        return new PopularVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularVH holder, int position) {
        final Integer currentIcon = Icons.get(position);
        final String currentName = Names.get(position);
        holder.icon.setImageResource(currentIcon);
        holder.name.setText(currentName);
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public static class PopularVH extends RecyclerView.ViewHolder {
        public CircleImageView icon;
        public TextView name;

        public PopularVH(@NonNull View itemView) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.popular_icons);
            name = itemView.findViewById(R.id.popular_name);

            itemView.setOnClickListener(view -> {
                Navigation.findNavController(view).navigate(R.id.action_home2_to_search2);
            });

        }
    }//vh
}