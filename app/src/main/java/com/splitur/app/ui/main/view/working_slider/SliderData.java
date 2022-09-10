package com.splitur.app.ui.main.view.working_slider;

import java.util.List;

public class SliderData {

    String title;
    List<Integer> sliderImages;

    public SliderData(String title, List<Integer> sliderImages) {
        this.title = title;
        this.sliderImages = sliderImages;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getSliderImages() {
        return sliderImages;
    }
}
