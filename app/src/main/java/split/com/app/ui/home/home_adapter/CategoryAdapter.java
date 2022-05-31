package split.com.app.ui.home.home_adapter;

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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVH> {

    private final List<Integer> Icons;
    private final List<String> Names;
    private final Context context;


    public CategoryAdapter(Context context, List<Integer> popIcons, List<String> popNames) {
        this.context = context;
        this.Icons = popIcons;
        this.Names = popNames;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_list_item, parent, false);
        return new CategoryAdapter.CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryVH holder, int position) {
        final Integer currentIcon = Icons.get(position);
        final String currentName = Names.get(position);
        holder.icon.setImageResource(currentIcon);
        holder.name.setText(currentName);
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }

    public static class CategoryVH extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;

        public CategoryVH(@NonNull View itemView) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.catImage);
            name = itemView.findViewById(R.id.catName);

        }
    }//vh
}
