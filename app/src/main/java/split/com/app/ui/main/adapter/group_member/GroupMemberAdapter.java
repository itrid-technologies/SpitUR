package split.com.app.ui.main.adapter.group_member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.utils.Constants;


public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> {

    private final List<split.com.app.data.model.group_member.DataItem> list;
    private final Context context;
    private ItemClickListener mListener;


    public void setOnRemoveListener(ItemClickListener listener) {
        mListener = listener;
    }


    public GroupMemberAdapter(Context appContext, List<split.com.app.data.model.group_member.DataItem> membersList) {
        this.context = appContext;
        this.list = membersList;
    }


    @NonNull
    @Override
    public GroupMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_list_design, parent, false);
        return new GroupMemberAdapter.ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMemberAdapter.ViewHolder holder, int position) {
        split.com.app.data.model.group_member.DataItem current_item = list.get(position);

        Glide.with(context)
                .load(Constants.IMG_PATH + current_item.getUser().getAvatar())
                .placeholder(R.color.blue)
                .into(holder.image);

        holder.name.setText(current_item.getUser().getUserId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onRemove(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView image;
        public ImageView remove;
        public TextView name, date;
        public Button chat;

        public ViewHolder(@NonNull View itemView, ItemClickListener mListener) {
            super(itemView);
            //find views
            image = itemView.findViewById(R.id.member_image);
            remove = itemView.findViewById(R.id.delete_member);
            name = itemView.findViewById(R.id.member_name);
            date = itemView.findViewById(R.id.tv_coins_date);
            chat = itemView.findViewById(R.id.chat_button);


            remove.setOnClickListener(view -> {
                if(mListener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        mListener.onRemove(getAdapterPosition());
                    }
                }
            });
        }
    }
}
