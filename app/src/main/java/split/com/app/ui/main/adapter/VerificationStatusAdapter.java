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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.utils.Split;

public class VerificationStatusAdapter extends RecyclerView.Adapter<VerificationStatusAdapter.ViewHolder> {

    private final List<String> statusList;
    private final List<Integer> iconList;
    private final List<String> colorsList;
    private final Context context;


    public VerificationStatusAdapter(Split appContext, List<String> status, List<Integer> icons, List<String> colors) {
         this.context = appContext;
         this.statusList = status;
         this.iconList = icons;
         this.colorsList = colors;
    }

    @NonNull
    @Override
    public VerificationStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.password_verification_design, parent, false);
        return new VerificationStatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerificationStatusAdapter.ViewHolder holder, int position) {

        String status = statusList.get(position);
        String color = colorsList.get(position);
        Integer icon = iconList.get(position);

        holder.status.setText(status);
        holder.user.setBorderColor(Color.parseColor(color));
        holder.imageView.setImageResource(icon);
        holder.imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor((color))));




    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView status;
        CircleImageView user;
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //find views
            status = itemView.findViewById(R.id.status);
            imageView = (itemView).findViewById(R.id.iv_user_);
            user = (itemView).findViewById(R.id.iv_user);


        }
    }
}
