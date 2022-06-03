package split.com.app.ui.main.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.databinding.FragmentHomeBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.adapter.category_adapter.CategoryAdapter;
import split.com.app.ui.main.adapter.popular_adapter.PopularHomeAdapter;


public class Home extends Fragment {

   FragmentHomeBinding binding;

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

        getPopularList();
        getCategoryList();
        getMoviesList();
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


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        binding.moviesList.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(requireContext(), images, names);
        binding.moviesList.setAdapter(adapter);
    }

    private void getCategoryList() {
        List<String> names = new ArrayList<>();

        names.add("Movies");
        names.add("Music");
        names.add("VPN");
        names.add("Cloud");
        names.add("Games");
        names.add("Others");


        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.player_icon);
        images.add(R.drawable.song_icon);
        images.add(R.drawable.drive_icon);
        images.add(R.drawable.player_icon);
        images.add(R.drawable.song_icon);
        images.add(R.drawable.drive_icon);



        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        binding.categoriesLst.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(requireContext(), images, names);
        binding.categoriesLst.setAdapter(adapter);


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



        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        binding.popularLst.setLayoutManager(layoutManager);
        PopularHomeAdapter adapter = new PopularHomeAdapter(requireContext(), images, names);
        binding.popularLst.setAdapter(adapter);
    }
}