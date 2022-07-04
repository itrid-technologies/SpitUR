package split.com.app.ui.main.adapter;

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
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.group_score.GroupSplitScoreItem;
import split.com.app.utils.Constants;
import split.com.app.utils.Split;

public class VerificationStatusAdapter extends RecyclerView.Adapter<VerificationStatusAdapter.ViewHolder> {

    private final List<GroupSplitScoreItem> list;
    private final Context context;


    public VerificationStatusAdapter(Split appContext, List<GroupSplitScoreItem> scoreItems) {
         this.context = appContext;
         this.list = scoreItems;
    }


    @NonNull
    @Override
    public VerificationStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_verification_design, parent, false);
        return new VerificationStatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationStatusAdapter.ViewHolder holder, int position) {

        GroupSplitScoreItem splitScoreItem = list.get(position);
        int score = splitScoreItem.getSplitScore();
        if (splitScoreItem.getSplitGroupUserId() != null ){
            if (score <= 100 && score >= 80 ){
                Glide.with(context)
                        .load(Constants.IMG_PATH + splitScoreItem.getSplitGroupUserId().getAvatar())
                        .placeholder(R.color.images_placeholder)
                        .into(holder.user);

                holder.status.setText("Verified");
                holder.user.setBorderColor(Color.parseColor("#0FB900"));
                holder.imageView.setImageResource(R.drawable.ic_verify);
                holder.imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("#0FB900"))));
            }else if (score < 80 && score >= 50){
                Glide.with(context)
                        .load(Constants.IMG_PATH + splitScoreItem.getSplitGroupUserId().getAvatar())
                        .placeholder(R.color.images_placeholder)
                        .into(holder.user);

                holder.status.setText("Pending");
                holder.user.setBorderColor(Color.parseColor("#F7931A"));
                holder.imageView.setImageResource(R.drawable.ic_attach);
                holder.imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("#F7931A"))));
            }else if (score < 50 && score >= 0 ){
                Glide.with(context)
                        .load(Constants.IMG_PATH + splitScoreItem.getSplitGroupUserId().getAvatar())
                        .placeholder(R.color.images_placeholder)
                        .into(holder.user);

                holder.status.setText("Invalid");
                holder.user.setBorderColor(Color.parseColor("#FF0000"));
                holder.imageView.setImageResource(R.drawable.ic_close);
                holder.imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("#FF0000"))));
            }else {

            }
        }else {
//            Glide.with(context)
//                    .load(Constants.IMG_PATH + splitScoreItem.getSplitGroupUserId().getAvatar())
//                    .placeholder(R.color.blue)
//                    .into(holder.user);
//
//            holder.status.setText("No User");
//            holder.user.setBorderColor(Color.parseColor("#FF0000"));
//            holder.imageView.setImageResource(R.drawable.ic_close);
//            holder.imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(("FF0000"))));
        }








    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView status;
        CircleImageView user;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            status = itemView.findViewById(R.id.status);
            imageView = (itemView).findViewById(R.id.iv_user_status);
            user = (itemView).findViewById(R.id.iv_user);


        }
    }
}
