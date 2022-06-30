package split.com.app.ui.main.adapter.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.data.model.contact.ContactModel;
import split.com.app.utils.Constants;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private final List<ContactModel> list;
    private final Context context;



    public FriendListAdapter(Context appContext, List<ContactModel> friend_data) {
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
        ContactModel current_item = list.get(position);

        if (current_item.photoURI != null) {
            Glide.with(context)
                    .load(current_item.photoURI)
                    .placeholder(R.color.blue)
                    .into(holder.image);
        }

        holder.name.setText(current_item.name);
        holder.userid.setText(current_item.mobileNumber);
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
