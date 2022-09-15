package com.splitur.app.ui.main.adapter.friend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.splitur.app.R;
import com.splitur.app.data.model.contact.ContactModel;
import com.splitur.app.data.model.friend_list.DataItem;
import com.splitur.app.utils.Constants;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private final List<DataItem> list;
    private final Context context;



    public FriendListAdapter(Context appContext, List<DataItem> friend_data) {
        this.context = appContext;
        this.list = friend_data;
    }


    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_design, parent, false);
        return new FriendListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {

        try {

        DataItem current_item = list.get(position);

        if (current_item.getAvatar() != null) {
            holder.image.setImageResource(Constants.getAvatarIcon(context, Integer.parseInt(current_item.getAvatar())));
        }

        holder.name.setText(current_item.getName());
        holder.userid.setText("@" + current_item.getUserId());

        }catch (NullPointerException e){
            Log.e("friend", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public TextView name, userid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            image = itemView.findViewById(R.id.friend_image);
            name = itemView.findViewById(R.id.friend_name);
            userid = itemView.findViewById(R.id.friend_id);


        }
    }
}
