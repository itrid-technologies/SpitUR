package split.com.app.ui.main.view.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import split.com.app.R;
import split.com.app.databinding.FragmentTransactionsBinding;
import split.com.app.ui.main.view.dashboard.Dashboard;


public class Transactions extends Fragment {

    FragmentTransactionsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);

        binding.transactionToolbar.title.setText("Payments");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initClickListeners();
    }

    private void initClickListeners() {
        binding.transactionToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.transactionToolbar.info.setOnClickListener(view -> {
            final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
            View carDetailView = LayoutInflater.from(requireContext()).inflate(R.layout.contact_by_dialogue, null, false);

            ConstraintLayout layout = carDetailView.findViewById(R.id.contact_BY);
            ConstraintLayout chatLayout = layout.findViewById(R.id.chat_layout);
            ConstraintLayout faqLayout = layout.findViewById(R.id.faq_layout);

            chatLayout.setOnClickListener(view1 -> {
                bt.cancel();
               // Navigation.findNavController(requireView()).navigate(R.id.chatroom);
            });

            faqLayout.setOnClickListener(view1 -> {
                bt.cancel();
                 Navigation.findNavController(requireView()).navigate(R.id.action_transactions_to_FAQ);
            });


            bt.setContentView(carDetailView);
            bt.show();
        });

    }
}