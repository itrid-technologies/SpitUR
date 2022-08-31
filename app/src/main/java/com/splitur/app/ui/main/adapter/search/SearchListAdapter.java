package com.splitur.app.ui.main.adapter.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.popular_subcategory.DataItem;
import com.splitur.app.utils.Constants;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchVH> {

    private static List<DataItem> dataItems;
    private final Context context;
    private ItemClickListener mListener;


    public void setOnCreateClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public SearchListAdapter(Context appContext, List<DataItem> popularSubCategoryList) {
        this.context = appContext;
        this.dataItems = popularSubCategoryList;
    }

    @NonNull
    @Override
    public SearchListAdapter.SearchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item_design, parent, false);
        return new SearchListAdapter.SearchVH(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.SearchVH holder, int position) {
        final DataItem current_data = dataItems.get(position);
        if (current_data.getCategory() != null) {

//            Svg.INSTANCE.loadUrl(
//                    Constants.IMG_PATH + current_data.getCategory().getIcon(),
//                    holder.icon);
            holder.icon.setImageResource(
                    Constants.getCategoryIcon(context,current_data.getCategory().getId()));
        }
        holder.name.setText(current_data.getTitle());
        if (current_data.getNumberOfGroups() != null){
            holder.no_memberLayout.setVisibility(View.VISIBLE);
            holder.members.setVisibility(View.GONE);

            String groups = current_data.getNumberOfGroups().toString();
                if (groups.equalsIgnoreCase("0")){
                    holder.no_memberLayout.setVisibility(View.VISIBLE);
                    holder.members.setVisibility(View.GONE);
                }else {
                    holder.no_memberLayout.setVisibility(View.GONE);
                    holder.members.setVisibility(View.VISIBLE);

                    if (groups.equalsIgnoreCase("1")){
                        holder.member1.setVisibility(View.VISIBLE);
                    }else if (groups.equalsIgnoreCase("2")){
                        holder.member1.setVisibility(View.VISIBLE);
                        holder.member2.setVisibility(View.VISIBLE);

                    }else if (groups.equalsIgnoreCase("3")){
                        holder.member1.setVisibility(View.VISIBLE);
                        holder.member2.setVisibility(View.VISIBLE);
                        holder.member3.setVisibility(View.VISIBLE);
                    }else if (groups.equalsIgnoreCase("4")){
                        holder.member1.setVisibility(View.VISIBLE);
                        holder.member2.setVisibility(View.VISIBLE);
                        holder.member3.setVisibility(View.VISIBLE);
                        holder.member4.setVisibility(View.VISIBLE);
                    }else if (groups.equalsIgnoreCase("5")){
                        holder.member1.setVisibility(View.VISIBLE);
                        holder.member2.setVisibility(View.VISIBLE);
                        holder.member3.setVisibility(View.VISIBLE);
                        holder.member4.setVisibility(View.VISIBLE);
                        holder.member5.setVisibility(View.VISIBLE);
                        holder.remaining.setVisibility(View.VISIBLE);

                        int total  = Integer.parseInt(current_data.getNumberOfGroups()) - 4;
                        holder.remaining.setText("+"+total);
                    }else {
                        holder.member1.setVisibility(View.VISIBLE);
                        holder.member2.setVisibility(View.VISIBLE);
                        holder.member3.setVisibility(View.VISIBLE);
                        holder.member4.setVisibility(View.VISIBLE);
                        holder.member5.setVisibility(View.VISIBLE);
                        holder.remaining.setVisibility(View.VISIBLE);

                        int total  = Integer.parseInt(current_data.getNumberOfGroups()) - 4;
                        holder.remaining.setText("+"+total);
                    }
                }
        }else {
            holder.no_memberLayout.setVisibility(View.VISIBLE);
            holder.members.setVisibility(View.GONE);

        }
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
        public TextView name, create;
        ConstraintLayout no_memberLayout, members;
        CircleImageView member1,member2, member3 , member4 , member5;
        TextView remaining;

        public SearchVH(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.search_icons);
            name = itemView.findViewById(R.id.search_name);
            create = itemView.findViewById(R.id.tv_create);
            member1 = itemView.findViewById(R.id.member1);
            member2 = itemView.findViewById(R.id.member2);
            member3 = itemView.findViewById(R.id.member3);
            member4 = itemView.findViewById(R.id.member4);
            member5 = itemView.findViewById(R.id.member5);
            remaining = itemView.findViewById(R.id.count);
            no_memberLayout = itemView.findViewById(R.id.no_member_view01);
            members = itemView.findViewById(R.id.users01);


            create.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCreate(getAdapterPosition());
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onCreate(getAdapterPosition());
                    }
                }
            });
        }
    }//vh
}
