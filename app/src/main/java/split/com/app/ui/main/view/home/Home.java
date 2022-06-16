package split.com.app.ui.main.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.HomeDataItem;
import split.com.app.data.model.home_categories.CategoryDataItems;
import split.com.app.data.model.popular_subcategory.DataItem;
import split.com.app.databinding.FragmentHomeBinding;
import split.com.app.ui.main.adapter.HomeSectionAdapter;
import split.com.app.ui.main.adapter.category_adapter.CategoryAdapter;
import split.com.app.ui.main.adapter.popular_adapter.PopularHomeAdapter;
import split.com.app.ui.main.adapter.search.SearchListAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.category_viewmodel.CategoryViewModel;
import split.com.app.ui.main.viewmodel.home_viewmodel.HomeViewModel;
import split.com.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Home extends Fragment {

    FragmentHomeBinding binding;

    private CategoryViewModel mViewModel;
    private List<HomeDataItem> homeDataItems;

    private HomeViewModel homeViewModel;
    private List<CategoryDataItems> category_list;

    private List<DataItem> popularSubCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Dashboard.hideNav(false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        category_list = new ArrayList<>();
        homeDataItems = new ArrayList<>();

        homeViewModel = new HomeViewModel();
        homeViewModel.init();
        homeViewModel.getHomeData().observe(getViewLifecycleOwner(), homeContentModel -> {
            if (homeContentModel.isSuccess()) {
                homeDataItems.addAll(homeContentModel.getData());
                getMoviesList();
            }
        });

        mViewModel = new CategoryViewModel();
        getCategories();

        setProfileData();


        initClickListeners();
        getPopularList();
    }

    private void initClickListeners() {
        binding.search.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });

        binding.viewAll.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_home2_to_joinSearch);
        });
    }

    private void getCategories() {
        mViewModel.init();
        mViewModel.getCategoryData().observe(getViewLifecycleOwner(), categoriesModel -> {
            if (categoriesModel.isSuccess()) {
                if (categoriesModel.getData().size() > 0) {
                    category_list.addAll(categoriesModel.getData());
                }

                buildCategoryRv();
            }
        });

    }

    private void buildCategoryRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.categoriesLst.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(Split.getAppContext(), category_list);
        binding.categoriesLst.setAdapter(adapter);

        adapter.setOnCategorySelectListener(position -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_home2_to_joinSearch);
        });

    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");

        binding.name.setText(user_name);
        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.drawable.user).into(binding.userImage);


    }

    private void getMoviesList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.homeSections.setLayoutManager(layoutManager);
        HomeSectionAdapter adapter = new HomeSectionAdapter(Split.getAppContext(), homeDataItems);
        binding.homeSections.setAdapter(adapter);
    }


    private void getPopularList() {

        popularSubCategoryList = new ArrayList<>();

        homeViewModel = new HomeViewModel();
        homeViewModel.initPopular();
        homeViewModel.getPopularCategoryData().observe(getViewLifecycleOwner(), popularSubCategoryModel -> {
            if (popularSubCategoryModel.isSuccess()) {
                if (popularSubCategoryModel.getData().size() > 0) {
                    popularSubCategoryList.addAll(popularSubCategoryModel.getData());
                }

                buildPopularCatRv();
            }
        });

//        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
//        binding.popularLst.setLayoutManager(layoutManager);
//        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), images, names);
//        binding.popularLst.setAdapter(adapter);
    }

    private void buildPopularCatRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.popularLst.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), popularSubCategoryList);
        binding.popularLst.setAdapter(adapter);

    }
}