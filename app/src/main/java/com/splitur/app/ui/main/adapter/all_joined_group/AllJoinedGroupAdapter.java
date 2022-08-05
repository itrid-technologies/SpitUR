package com.splitur.app.ui.main.adapter.all_joined_group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import com.splitur.app.R;
import com.splitur.app.utils.Constants;

public class AllJoinedGroupAdapter extends RecyclerView.Adapter<AllJoinedGroupAdapter.GroupVH> {

    private final List<com.splitur.app.data.model.all_joined_groups.DataItem> dataItems;
    private final Context context;

    private AllJoinedGroupAdapter.ItemClickListener mListener;



    public void setOnJoinedClixkListener(AllJoinedGroupAdapter.ItemClickListener listener) {
        mListener = listener;
    }


    public AllJoinedGroupAdapter(Context appContext, List<com.splitur.app.data.model.all_joined_groups.DataItem> list) {
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
        com.splitur.app.data.model.all_joined_groups.DataItem current_item = dataItems.get(position);
        if (current_item.getGroup() != null) {
            holder.layout.setVisibility(View.VISIBLE);


                if (!current_item.getPaymentStatus().isEmpty()){
                    if (current_item.getPaymentStatus().equalsIgnoreCase("paid")){
                        holder.open.setText("Open");
                        holder.open.setBackgroundResource(R.drawable.rect_blue_bg);

                    }else if (current_item.getPaymentStatus().equalsIgnoreCase("pending")){
                        holder.open.setText("Pending");
                        holder.open.setBackgroundResource(R.drawable.pending_bg);
                    }else {

                    }
                }

                holder.icon.setImageResource(
                        Constants.getCategoryIcon(context,current_item.getGroup().getSubCategory().getCategory().getId()));

                if (!current_item.getGroup().isStatus()){
                    holder.open.setText("Closed");
                    holder.open.setBackgroundResource(R.drawable.closed_bg);
                }

                if (current_item.getEnrollment_status().equals("left") ||current_item.getEnrollment_status().equals("removed")){
                    holder.open.setText("Closed");
                    holder.open.setBackgroundResource(R.drawable.closed_bg);
                }

                holder.name.setText(current_item.getGroup().getGroupTitle());

                if (current_item.getGroup().getTotalMembers() != null) {
                    holder.no_memberLayout.setVisibility(View.GONE);

                    String members = current_item.getGroup().getTotalMembers().toString();
                    if (members.equalsIgnoreCase("0")) {
                        holder.no_memberLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (members.equalsIgnoreCase("1")) {
                            holder.member1.setVisibility(View.VISIBLE);
                        } else if (members.equalsIgnoreCase("2")) {
                            holder.member1.setVisibility(View.VISIBLE);
                            holder.member2.setVisibility(View.VISIBLE);

                        } else if (members.equalsIgnoreCase("3")) {
                            holder.member1.setVisibility(View.VISIBLE);
                            holder.member2.setVisibility(View.VISIBLE);
                            holder.member3.setVisibility(View.VISIBLE);
                        } else if (members.equalsIgnoreCase("4")) {
                            holder.member1.setVisibility(View.VISIBLE);
                            holder.member2.setVisibility(View.VISIBLE);
                            holder.member3.setVisibility(View.VISIBLE);
                            holder.member4.setVisibility(View.VISIBLE);
                        } else if (members.equalsIgnoreCase("5")) {
                            holder.member1.setVisibility(View.VISIBLE);
                            holder.member2.setVisibility(View.VISIBLE);
                            holder.member3.setVisibility(View.VISIBLE);
                            holder.member4.setVisibility(View.VISIBLE);
                            holder.member5.setVisibility(View.VISIBLE);
                            holder.remaining.setVisibility(View.VISIBLE);

                            int total = Integer.parseInt(current_item.getGroup().getTotalMembers()) - 4;
                            holder.remaining.setText("+" + total);
                        } else {
                            holder.member1.setVisibility(View.VISIBLE);
                            holder.member2.setVisibility(View.VISIBLE);
                            holder.member3.setVisibility(View.VISIBLE);
                            holder.member4.setVisibility(View.VISIBLE);
                            holder.member5.setVisibility(View.VISIBLE);
                            holder.remaining.setVisibility(View.VISIBLE);

                            int total = Integer.parseInt(current_item.getGroup().getTotalMembers()) - 4;
                            holder.remaining.setText("+" + total);
                        }
                    }
                }else {
                    holder.no_memberLayout.setVisibility(View.VISIBLE);
                }

        }else {
            holder.layout.setVisibility(View.GONE);
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
        public TextView name,open;
        ConstraintLayout layout, no_memberLayout;
        ImageView icon;
        CircleImageView member1,member2, member3 , member4 , member5;
        TextView remaining;

        public GroupVH(@NonNull View itemView, AllJoinedGroupAdapter.ItemClickListener mListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.join_title);
            open = itemView.findViewById(R.id.open);
            layout = itemView.findViewById(R.id.JoinedItemLayout);
            icon = itemView.findViewById(R.id.join_icons);
            member1 = itemView.findViewById(R.id.member1);
            member2 = itemView.findViewById(R.id.member2);
            member3 = itemView.findViewById(R.id.member3);
            member4 = itemView.findViewById(R.id.member4);
            member5 = itemView.findViewById(R.id.member5);
            remaining = itemView.findViewById(R.id.count);
            no_memberLayout = itemView.findViewById(R.id.no_member_view);


            open.setOnClickListener(view -> {
                String isOpen = open.getText().toString().trim();
                if (isOpen.equalsIgnoreCase("Closed")) {

                } else{
                    if (mListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mListener.onCategorySelect(getAdapterPosition());
                        }
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                String isOpen = open.getText().toString().trim();
                if (isOpen.equalsIgnoreCase("Closed")) {

                } else if (isOpen.equalsIgnoreCase("Open")){
                    if (mListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mListener.onCategorySelect(getAdapterPosition());
                        }
                    }
                }else {

                }
            });

        }
    }
}

