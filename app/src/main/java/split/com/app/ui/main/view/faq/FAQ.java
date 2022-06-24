package split.com.app.ui.main.view.faq;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import split.com.app.data.model.faq.DataItem;
import split.com.app.databinding.FragmentFAQBinding;
import split.com.app.ui.main.adapter.faq.FaqListAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.faq_viewmodel.FaqViewModel;
import split.com.app.utils.Split;


public class FAQ extends Fragment {


    FragmentFAQBinding binding;
    FaqViewModel viewModel;
    private List<DataItem> faq_data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFAQBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        faq_data = new ArrayList<>();
        viewModel = new FaqViewModel();
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(), faqListModel -> {
            if (faqListModel.isSuccess()){
                if (faqListModel.getData().size() > 0){
                    faq_data.addAll(faqListModel.getData());
                    buildFaqRv(faq_data);
                }
            }
        });
        initClickListeners();

    }

    private void buildFaqRv(List<DataItem> faq_data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.faqsList.setLayoutManager(layoutManager);
        FaqListAdapter adapter = new FaqListAdapter(Split.getAppContext(), faq_data);
        binding.faqsList.setAdapter(adapter);
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }


}