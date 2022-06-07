package split.com.app.ui.main.adapter.avatar_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import split.com.app.R;
import split.com.app.utils.Constants;

public class AdapterAvatars extends RecyclerView.Adapter<AdapterAvatars.AvatarsVH> {

    private final List<String> mAvatars;
    private final Context ctx;


    public AdapterAvatars(Context context,List<String> mAvatars) {
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
        final String currentAvatar = mAvatars.get(position);
        Glide.with(ctx).load(Constants.IMG_PATH +currentAvatar).into(holder.avatar);
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
