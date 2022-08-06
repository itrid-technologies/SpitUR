package com.splitur.app.ui.main.adapter.group_detail_adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.splitur.app.R;
import com.splitur.app.data.model.group_detail.DataItem;
import com.splitur.app.utils.Constants;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.ViewHolder> {

    private final List<DataItem> data;
    private final Context context;
    DataItem dataItem;

    private ItemClickListener mListener;


    public void setOnGroupSelectListener(ItemClickListener listener) {
        mListener = listener;
    }


    public GroupDetailAdapter(Context appContext, List<DataItem> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public GroupDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plans_search_list_item_design, parent, false);
        return new GroupDetailAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDetailAdapter.ViewHolder holder, int position) {

        dataItem = data.get(position);
        holder.title.setText(dataItem.getTitle());
        if (dataItem.getGroupAdmin() != null) {

            try {
                int score_value = Integer.valueOf((int) Math.round(dataItem.getGroupAdmin().getSpliturScore()));
                if ( score_value < 30 ){
                    holder.user.setBorderColor(Color.parseColor("#FF3D00"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF3D00")));
                }else if (score_value < 70 ){
                    holder.user.setBorderColor(Color.parseColor("#FFD300"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFD300")));
                }else if (score_value > 70){
                    holder.user.setBorderColor(Color.parseColor("#14FF00"));
                    holder.score.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14FF00")));
                }else {

                }


            }catch (NullPointerException e){
                e.getMessage();
            }


            holder.score.setText(String.valueOf(Math.round(dataItem.getGroupAdmin().getSpliturScore())));
            if (dataItem.getGroupAdmin().isOnlineOflineStatus()) {
                holder.online_offline.setText("Online");
                holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14FF00")));
            } else {
                holder.online_offline.setText("Offline");
                holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
            }

            holder.user.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(dataItem.getGroupAdmin().getAvatar())));
//            Glide.with(context)
//                    .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
//                    .placeholder(R.color.images_placeholder)
//                    .into(holder.user);
            holder.rateOf_id.setText(String.format("@%s", dataItem.getGroupAdmin().getUserId()));
        }

        /*
         * Handle slots & joined members
         */

        final int memberCount = dataItem.getTotalMembers();
        final int slotsCount = dataItem.getSlots();

/*        if (slotsCount > 0) {
            LinearLayout laySlots = holder.llSlots;
            for (int i = 0; i <= slotsCount; i++) {

                //add views dynamically
                TextView tvSlot = new TextView(context);
                tvSlot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tvSlot.setGravity(Gravity.CENTER);
                tvSlot.setPadding(0, com.intuit.sdp.R.dimen._1sdp, 0, 0);
                tvSlot.setTextColor(context.getResources().getColor(R.color.white));
                tvSlot.setTextSize(com.intuit.sdp.R.dimen._7sdp);
                tvSlot.setBackground(AppCompatResources.getDrawable(context, R.drawable.ic_score_bg));
                tvSlot.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_semibold));
                tvSlot.setText(String.valueOf(i));
                if (i == memberCount) {
                    //add special bg effect
                    tvSlot.setAlpha(1F);
                    tvSlot.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                } else {
                    tvSlot.setAlpha(0.4F);
                    tvSlot.setBackgroundTintList(AppCompatResources.getColorStateList(context, R.color.transparent));
                }

                //add view to llSlots
                laySlots.addView(tvSlot, i);

            }//for
        }*/

        //jitni slots hn utny hi members hoty hn
        for (int i = 0; i < slotsCount; i++) {
            if (i == memberCount) {
                switch (i) {
                    case 1:
                        holder.one.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.one.setTextColor(Color.WHITE);
                        holder.one.setAlpha(1);
                        break;
                    case 2:
                        holder.two.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.two.setTextColor(Color.WHITE);
                        holder.two.setAlpha(1);
                        break;
                    case 3:
                        holder.three.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.three.setTextColor(Color.WHITE);
                        holder.three.setAlpha(1);
                        break;
                    case 4:
                        holder.four.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.four.setTextColor(Color.WHITE);
                        holder.four.setAlpha(1);
                        break;
                    case 5:
                        holder.five.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.five.setTextColor(Color.WHITE);
                        holder.five.setAlpha(1);
                        break;
                    case 6:
                        holder.six.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
                        holder.six.setTextColor(Color.WHITE);
                        holder.six.setAlpha(1);
                        break;

                }
            }
        }//for

        try {
            for (int i = slotsCount + 1; i <= 6; i++) {
                switch (i) {
                    case 1:
                        holder.one.setVisibility(View.GONE);
                        break;
                    case 2:
                        holder.two.setVisibility(View.GONE);
                        break;
                    case 3:
                        holder.three.setVisibility(View.GONE);
                        break;
                    case 4:
                        holder.four.setVisibility(View.GONE);
                        break;
                    case 5:
                        holder.five.setVisibility(View.GONE);
                        break;
                    case 6:
                        holder.six.setVisibility(View.GONE);
                        break;
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        holder.group_id.setText(String.valueOf(dataItem.getGroupId()));

        if (dataItem.getSubCategory() != null) {
            holder.rate_icon.setImageResource(
                    Constants.getCategoryIcon(context, dataItem.getSubCategory().getCategory().getId()));
        }
        String id = Constants.ID;
        if(dataItem.getGroupAdmin().getId() == Integer.parseInt(id)){
            String coin = String.valueOf(dataItem.getCostPerMember());
            holder.coins.setText(coin + " Coins");
        }else {
            String coin = String.valueOf(dataItem.getCostPerMember());
            double coinFloat = Double.parseDouble(coin);
            String value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
            holder.coins.setText(value + " Coins");
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener {
        void onGroupSelect(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, online_offline, group_id, rateOf_id, one, two, three, four, five, six, score, coins;
        ImageView online_icon, rate_icon;
        CircleImageView user;
        LinearLayout llSlots;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            title = itemView.findViewById(R.id.tv_username);
            online_offline = itemView.findViewById(R.id.tv_online);
            group_id = itemView.findViewById(R.id.tv_groupid_value);
            rateOf_id = itemView.findViewById(R.id.tv_rateOf);
            one = itemView.findViewById(R.id.tv_one);
            two = itemView.findViewById(R.id.tv_two);
            three = itemView.findViewById(R.id.tv_three);
            four = itemView.findViewById(R.id.tv_four);
            five = itemView.findViewById(R.id.tv_five);
            six = itemView.findViewById(R.id.tv_six);
            llSlots = itemView.findViewById(R.id.slots);
            online_icon = itemView.findViewById(R.id.online_icon);
            rate_icon = itemView.findViewById(R.id.icon);
            score = itemView.findViewById(R.id.tv_score);
            user = itemView.findViewById(R.id.iv_user);
            coins = itemView.findViewById(R.id.coins_value);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onGroupSelect(getAdapterPosition());
                    }
                }
            });


        }
    }
}
