package split.com.app.ui.main.adapter.group_detail_adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.data.model.group_detail.DataItem;
import split.com.app.utils.Constants;
import split.com.app.utils.Svg;

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
        holder.score.setText(String.valueOf(Math.round(dataItem.getGroupAdmin().getSpliturScore())));
        String member = String.valueOf(dataItem.getTotalMembers());
        if (member.equalsIgnoreCase("1")) {
            holder.one.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.one.setTextColor(Color.WHITE);
            holder.one.setAlpha(1);
        } else if (member.equalsIgnoreCase("2")) {
            holder.two.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.two.setTextColor(Color.WHITE);
            holder.two.setAlpha(1);

        } else if (member.equalsIgnoreCase("3")) {
            holder.three.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.three.setTextColor(Color.WHITE);
            holder.three.setAlpha(1);

        } else if (member.equalsIgnoreCase("4")) {
            holder.four.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.four.setTextColor(Color.WHITE);
            holder.four.setAlpha(1);

        } else if (member.equalsIgnoreCase("5")) {
            holder.five.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.five.setTextColor(Color.WHITE);
            holder.five.setAlpha(1);

        } else if (member.equalsIgnoreCase("6")) {
            holder.six.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#282C4A")));
            holder.six.setTextColor(Color.WHITE);
            holder.six.setAlpha(1);

        }

        if (dataItem.getGroupAdmin().isOnlineOflineStatus()) {
            holder.online_offline.setText("Online");
            holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14FF00")));
        } else {
            holder.online_offline.setText("Offline");
            holder.online_icon.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
        }

        holder.group_id.setText(String.valueOf(dataItem.getGroupId()));

        Glide.with(context)
                .load(Constants.IMG_PATH + dataItem.getGroupAdmin().getAvatar())
                .placeholder(R.color.images_placeholder)
                .into(holder.user);

//        Svg.INSTANCE.loadUrl(Constants.IMG_PATH + dataItem.getSubCategory().getCategory().getIcon(), holder.rate_icon);

        holder.rate_icon.setImageResource(
                Constants.getCategoryIcon(context,dataItem.getSubCategory().getCategory().getId()));

        holder.rateOf_id.setText(String.format("@%s", dataItem.getGroupAdmin().getUserId()));

        String coin = String.valueOf(dataItem.getCostPerMember());
        Double coinFloat = Double.parseDouble(coin);
        String value = String.valueOf(Math.round(((coinFloat * 30) / 100) + coinFloat));
        holder.coins.setText(value + " Coins");


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
