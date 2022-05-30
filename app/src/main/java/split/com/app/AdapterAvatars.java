package split.com.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAvatars extends RecyclerView.Adapter<AdapterAvatars.AvatarsVH> {

    private final List<Integer> mAvatars;

    public AdapterAvatars(List<Integer> mAvatars) {
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
        holder.avatar.setImageResource(currentAvatar);
    }

    @Override
    public int getItemCount() {
        return mAvatars.size();
    }

    public static class AvatarsVH extends RecyclerView.ViewHolder {
        public ImageView avatar;

        public AvatarsVH(@NonNull View itemView) {
            super(itemView);
            //find views
            avatar = itemView.findViewById(R.id.item_avatar_img);
        }
    }//vh
}
