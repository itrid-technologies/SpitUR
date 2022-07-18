package split.com.app.ui.main.viewmodel.plan_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.plans.PlanModel;
import split.com.app.data.model.popular_subcategory.PopularSubCategoryModel;
import split.com.app.data.repository.create_search.SearchCreateRepository;
import split.com.app.data.repository.plans.PlanRepository;

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
