package com.splitur.app.ui.main.adapter.join_search;

import android.content.Context;
import android.util.Log;
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

public class JoinSearchListAdapter extends RecyclerView.Adapter<JoinSearchListAdapter.SearchVH> {

    private final List<DataItem> dataItems;
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

        try {

        final DataItem current_data = dataItems.get(position);
        if (current_data.getCategory() != null) {
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

        }catch (NullPointerException e){
            Log.e("join_search", e.getMessage());
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
        public TextView name, join;
        ConstraintLayout no_memberLayout,members;
        CircleImageView member1,member2, member3 , member4 , member5;
        TextView remaining;


        public SearchVH(@NonNull View itemView, JoinSearchListAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            icon = itemView.findViewById(R.id.join_search_icons);
            name = itemView.findViewById(R.id.join_search_name);
            join = itemView.findViewById(R.id.tv_join);
            no_memberLayout = itemView.findViewById(R.id.no_member_view);
            members = itemView.findViewById(R.id.J_users);

            member1 = itemView.findViewById(R.id.member1);
            member2 = itemView.findViewById(R.id.member2);
            member3 = itemView.findViewById(R.id.member3);
            member4 = itemView.findViewById(R.id.member4);
            member5 = itemView.findViewById(R.id.member5);
            remaining = itemView.findViewById(R.id.count);

            join.setOnClickListener(view -> {
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
