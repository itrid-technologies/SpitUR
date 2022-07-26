package com.splitur.app.ui.main.viewmodel.plan_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.plans.PlanModel;
import com.splitur.app.data.repository.plans.PlanRepository;

public class PlansViewModel extends ViewModel {

    String id;

    private MutableLiveData<PlanModel> data;
    private PlanRepository planRepository;

    public PlansViewModel(String sub_cat_id) {
        this.id = sub_cat_id;
        planRepository = new PlanRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = planRepository.getPlans(id);
    }

    public MutableLiveData<PlanModel> getPlan() {
        return this.data;
    }


}
