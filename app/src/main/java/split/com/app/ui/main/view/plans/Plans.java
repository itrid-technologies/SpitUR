package split.com.app.ui.main.view.plans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.plans.PlanDataItem;
import split.com.app.data.model.plans.PlanModel;
import split.com.app.databinding.FragmentPlansBinding;
import split.com.app.ui.main.adapter.PlanAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Constants;
import split.com.app.utils.GridAutofitLayoutManager;
import split.com.app.utils.Split;


public class Plans extends Fragment {


    FragmentPlansBinding binding;
//    private PlansViewModel mViewModel;
    private List<PlanDataItem> planModelList;

    String sub_cat_id;
    private PlanModel planModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlansBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.plansToolbar.title.setText("Plans");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
        planModelList = new ArrayList<>();

        if (getArguments() != null){
          //  sub_cat_id = getArguments().getString("SUB_CATEGORY_ID");
            String data  = getArguments().getString("plansDATA");
            Gson gson = new Gson();
            planModel = gson.fromJson(data,PlanModel.class);
            planModelList.addAll(planModel.getData());
            buildsPlansRv();
        }

//        mViewModel = new PlansViewModel(sub_cat_id);
//        mViewModel.init();
//        mViewModel.getPlan().observe(getViewLifecycleOwner(), planModel -> {
//            if (planModel.isSuccess()) {
//                if (planModel.getData().size() > 0) {
//                    planModelList.addAll(planModel.getData());
//                    buildsPlansRv();
//                }else {
//                    Constants.PLAN_ID = "";
//                    Navigation.findNavController(requireView()).navigate(R.id.action_plans2_to_visibility2);
//
//                }
//
//            }
//        });


    }

    private void buildsPlansRv() {
        GridLayoutManager glm = new GridLayoutManager(Split.getAppContext(), 2);
        binding.PlansList.setLayoutManager(glm);
        PlanAdapter adapter = new PlanAdapter(Split.getAppContext(), planModelList);
        binding.PlansList.setAdapter(adapter);

        adapter.setOnPlanSelectListener(position -> {
            //  MySharedPreferences pm = new MySharedPreferences(Split.getAppContext());
          //  pm.saveData(Split.getAppContext(), "PLAN_ID", plan_id);
            Constants.PLAN_ID = String.valueOf(planModelList.get(position).getId());
            Constants.SUB_CAT_TITLE = planModelList.get(position).getName();
           // pm.saveData(Split.getAppContext(), "TITLE", planModelList.get(position).getName());

            Navigation.findNavController(requireView()).navigate(R.id.action_plans2_to_visibility2);

        });

    }

    private void initClickListeners() {

        binding.plansToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

//        binding.join.setOnClickListener(view1 -> {
//            Navigation.findNavController(view1).navigate(R.id.action_plans2_to_visibility2);
//        });
    }
}