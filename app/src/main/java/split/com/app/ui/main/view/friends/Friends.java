package split.com.app.ui.main.view.friends;

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
import split.com.app.data.model.friend_list.DataItem;
import split.com.app.databinding.FragmentFriendsBinding;
import split.com.app.ui.main.adapter.chat.ChatAdapter;
import split.com.app.ui.main.adapter.friend.FriendListAdapter;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.ui.main.viewmodel.friend_viewmodel.FriendViewModel;
import split.com.app.ui.main.viewmodel.search_create_viewmodel.SearchCreateViewModel;
import split.com.app.utils.Split;


public class Friends extends Fragment {


    FragmentFriendsBinding binding;
    FriendViewModel viewModel;
    private List<DataItem> friend_data;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.friendToolbar.title.setText("Friends");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {

        binding.friendToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });


        binding.searchFriends.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.searchFriends.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, ContextCompat.getDrawable(Split.getAppContext(), R.drawable.ic_close), null);
                } else {
                    binding.searchFriends.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(Split.getAppContext(), R.drawable.search_icon), null, null, null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()) {
                    getSearchedData(data.toString());
                }
            }
        });
    }

    private void getSearchedData(String data) {
        viewModel = new FriendViewModel(data);
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(), friendListModel -> {
            if (friendListModel.isStatus()) {
                friend_data = new ArrayList<>();
                if (friendListModel.getData().size() > 0) {
                    friend_data.addAll(friendListModel.getData());
                    buildFriendRv(friend_data);
                }else {
                    binding.noFriendLayout.setVisibility(View.VISIBLE);
                    binding.friendsList.setVisibility(View.GONE);
                }
            }
        });
    }

    private void buildFriendRv(List<DataItem> friend_data) {

        binding.noFriendLayout.setVisibility(View.GONE);
        binding.friendsList.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.friendsList.setLayoutManager(layoutManager);
        FriendListAdapter adapter = new FriendListAdapter(Split.getAppContext(), friend_data);
        binding.friendsList.setAdapter(adapter);
    }
}