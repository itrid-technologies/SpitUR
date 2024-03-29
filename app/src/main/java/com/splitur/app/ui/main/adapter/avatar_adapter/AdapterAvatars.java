package com.splitur.app.ui.main.adapter.avatar_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.splitur.app.R;
import com.splitur.app.utils.Constants;

public class AdapterAvatars extends RecyclerView.Adapter<AdapterAvatars.AvatarsVH> {

    private final List<Integer> mAvatars;
    private final Context ctx;


    public AdapterAvatars(Context context,List<Integer> mAvatars) {
        this.ctx = context;
        this.mAvatars = mAvatars;
    }

    @NonNull
    @Override
    public AvatarsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_avatar, parent, false);
        return new AvatarsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarsVH holder, int position) {
        final Integer currentAvatar = mAvatars.get(position);
        holder.avatar.setImageResource(Constants.getAvatarIcon(ctx,currentAvatar));
    }

    @Override
    public int getItemCount() {
        return mAvatars.size();
    }

    public static class AvatarsVH extends RecyclerView.ViewHolder {
        public ImageView avatar;

        public AvatarsVH(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.item_avatar_img);
        }
    }//vh
}
