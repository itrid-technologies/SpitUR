package split.com.app.ui.main.view.search_create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import split.com.app.R;
import split.com.app.databinding.FragmentSearchBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.adapter.search.SearchListAdapter;
import split.com.app.utils.Split;


public class Search extends Fragment {

  FragmentSearchBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getList();
        initClickListeners();
        searchTextWatcher();
    }

    private void searchTextWatcher() {
        binding.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(),R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(),R.drawable.ic_close), null);
                } else {
                    binding.searchField.setCompoundDrawablesWithIntrinsicBounds( ContextCompat.getDrawable(Split.getAppContext(),R.drawable.search_icon), null, null, null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {
              //todo
            }
        });
    }

    private void initClickListeners() {
        binding.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void getList() {
        List<String> names = new ArrayList<>();

        names.add("Netflix Premium");
        names.add("Shopify Premium");

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.player_icon);
        images.add(R.drawable.song_icon);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.searchLis.setLayoutManager(layoutManager);
        SearchListAdapter adapter = new SearchListAdapter(Split.getAppContext(), images, names);
        binding.searchLis.setAdapter(adapter);
    }
}