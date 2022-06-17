package split.com.app.ui.main.view.join_checkout_complete;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import split.com.app.R;
import split.com.app.data.model.join_group.Data;
import split.com.app.databinding.FragmentJoinCheckoutCompleteBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;
import split.com.app.utils.Split;


public class JoinCheckoutComplete extends Fragment {

   FragmentJoinCheckoutCompleteBinding binding;
   Data data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinCheckoutCompleteBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.jccToolbar.title.setText("Group Joined");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null){
            String response = getArguments().getString("group_credentials");
            Gson gson = new Gson();
            data = gson.fromJson(response, Data.class);
            setData(data);
        }

        initClickListeners();
    }

    private void initClickListeners() {

        binding.jccToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.copyEmail.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper",binding.groupEmail.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();
        });

        binding.copyPass.setOnClickListener(view -> {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("WordKeeper",binding.groupPass.getText().toString());
            clipboard.setPrimaryClip(clip);

            Toast.makeText(Split.getAppContext(), "Copied", Toast.LENGTH_SHORT).show();

        });

        binding.btnHome.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.home2);
        });

        binding.tvRequest.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_joinCheckoutComplete_to_chatroom);
        });
    }

    private void setData(Data data) {
        binding.groupEmail.setText(data.getEmail());
        binding.groupPass.setText(data.getPassword());

    }
}