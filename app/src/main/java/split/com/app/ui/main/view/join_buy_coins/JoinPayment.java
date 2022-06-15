package split.com.app.ui.main.view.join_buy_coins;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import split.com.app.R;
import split.com.app.databinding.FragmentJoinPaymentBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class JoinPayment extends Fragment {

    FragmentJoinPaymentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJoinPaymentBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.dJoin.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_joinPayment_to_joinCheckoutComplete);
        });
    }
}