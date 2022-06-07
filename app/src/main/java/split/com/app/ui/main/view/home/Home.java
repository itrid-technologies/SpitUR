package split.com.app.ui.main.view.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.data.model.home_categories.CategoriesModel;
import split.com.app.data.model.home_categories.CategoryDataItems;
import split.com.app.data.model.register.RegisterModel;
import split.com.app.data.repository.home.HomeRepository;
import split.com.app.databinding.FragmentHomeBinding;
import split.com.app.ui.main.adapter.category_adapter.CategoryAdapter;
import split.com.app.ui.main.adapter.popular_adapter.PopularHomeAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.home_viewmodel.HomeViewModel;
import split.com.app.ui.main.viewmodel.register_viewmodel.RegisterViewModel;
import split.com.app.utils.MySharedPreferences;
import split.com.app.utils.Split;


public class Home extends Fragment {

   FragmentHomeBinding binding;

    private HomeViewModel mViewModel;
    private List<CategoryDataItems> category_list = new ArrayList<>();

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

        mViewModel = new HomeViewModel();
        getCategories();

        setProfileData();


        getPopularList();
        getMoviesList();
    }

    private void getCategories() {
        mViewModel.init();
        mViewModel.getCategoryData().observe(getViewLifecycleOwner() , categoriesModel -> {
            if (categoriesModel.isSuccess()){
                if (categoriesModel.getData().size() > 0){
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

    }

    private void setProfileData() {
        MySharedPreferences preferences = new MySharedPreferences(Split.getAppContext());
        String user_name = preferences.getData(Split.getAppContext(), "userName");
        String avatar = preferences.getData(Split.getAppContext(), "userAvatar");

        binding.name.setText(user_name);
        Glide.with(Split.getAppContext()).load(avatar).placeholder(R.drawable.user).into(binding.userImage);


    }

    private void getMoviesList() {
        List<String> names = new ArrayList<>();

        names.add("Amazon Prime");
        names.add("Shopify Premium");
        names.add("Nord Vpn Premium");

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.player_icon);
        images.add(R.drawable.song_icon);
        images.add(R.drawable.drive_icon);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.moviesList.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), images, names);
        binding.moviesList.setAdapter(adapter);
    }


    private void getPopularList() {
        List<String> names = new ArrayList<>();

        names.add("Amazon Prime");
        names.add("Shopify Premium");
        names.add("Nord Vpn Premium");

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.player_icon);
        images.add(R.drawable.song_icon);
        images.add(R.drawable.drive_icon);



        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.HORIZONTAL, false);
        binding.popularLst.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(Split.getAppContext(), images, names);
        binding.popularLst.setAdapter(adapter);
    }
}