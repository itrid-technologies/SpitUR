package split.com.app.ui.main.view.member_chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentMemberChatBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class MemberChat extends Fragment {


    FragmentMemberChatBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMemberChatBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.mcrToolbar.title.setText("Chat Member");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}