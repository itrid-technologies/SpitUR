package split.com.app.ui.main.view.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentChatroomBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class Chatroom extends Fragment {


    FragmentChatroomBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatroomBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.crToolbar.title.setText("Chat member");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cliclKisteners();
    }

    private void cliclKisteners() {
        binding.crToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });
    }
}