package com.splitur.app.ui.main.view.working_slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.splitur.app.R;
import com.splitur.app.data.model.HomeDataItem;

import java.util.List;

public class GroupWorkingSliderAdapter extends RecyclerView.Adapter<GroupWorkingSliderAdapter.ViewHolder> {

    private final List<SliderData> data;
    private final Context context;


    public GroupWorkingSliderAdapter(Context appContext, List<SliderData> list) {
        this.context = appContext;
        this.data = list;
    }

    @NonNull
    @Override
    public GroupWorkingSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructions_slider_design, parent, false);
        return new GroupWorkingSliderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupWorkingSliderAdapter.ViewHolder holder, int position) {

        SliderData sliderData = data.get(position);

        holder.title.setText(sliderData.title);
        SliderAdapter sliderAdapter = new SliderAdapter(context, sliderData.sliderImages);
        holder.sliderView.setSliderAdapter(sliderAdapter);
        holder.sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        holder.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        holder.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        holder.sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        holder.sliderView.setAutoCycle(true);
        holder.sliderView.startAutoCycle();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        SliderView sliderView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_group_create_flow);
            sliderView = itemView.findViewById(R.id.image_slider);

        }
    }
}
