package com.splitur.app.ui.main.view.notifications;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.splitur.app.R;
import com.splitur.app.data.model.notification.NotificationDataItem;
import com.splitur.app.ui.main.adapter.all_created_group.AllCreatedGroupAdapter;
import com.splitur.app.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationVH> {

    private final Context context;
    private final ArrayList<NotificationDataItem> data;
    private ItemClickListener mListener;


    public void setOnNotificationClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    public NotificationAdapter(Context appContext, ArrayList<NotificationDataItem> notificationData) {
        this.context = appContext;
        this.data = notificationData;
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item_design, parent, false);
        return new NotificationAdapter.NotificationVH(view,mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationVH holder, int position) {

        try {

        NotificationDataItem dataItem = data.get(position);
        if (dataItem.getUserNotification() != null){
            holder.image.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(dataItem.getUserNotification().getAvatar())));
        }

        holder.name.setText(dataItem.getTitle());

        holder.nTitle.setText(dataItem.getTypeMessage());
        holder.nDetail.setText(dataItem.getBody());

        holder.nTime.setText(Constants.getTime1(dataItem.getCreatedAt()));

        }catch (NullPointerException e){
            Log.e("Notification", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onNotificationClick(int position);
    }

    public static class NotificationVH extends RecyclerView.ViewHolder {
        TextView name, nTitle, nDetail, nTime;
        CircleImageView image;


        public NotificationVH(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            name = itemView.findViewById(R.id.notification_userrname);
            nTitle = itemView.findViewById(R.id.notification_title);
            nDetail = itemView.findViewById(R.id.notification_detail);
            nTime = itemView.findViewById(R.id.notification_time);
            image = itemView.findViewById(R.id.notificationUserImage);

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                       mListener.onNotificationClick(getAdapterPosition());
                    }
                }
            });

        }
    }
}
