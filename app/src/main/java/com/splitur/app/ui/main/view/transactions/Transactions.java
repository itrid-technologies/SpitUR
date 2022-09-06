package com.splitur.app.ui.main.view.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.transaction.DataItem;
import com.splitur.app.databinding.FragmentTransactionsBinding;
import com.splitur.app.ui.main.adapter.transactions.TransactionAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.transactions_viewmodel.TransactionsViewModel;
import com.splitur.app.utils.Split;


public class Transactions extends Fragment {

    FragmentTransactionsBinding binding;
    TransactionsViewModel viewModel;

    List<DataItem> transactions;

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

        viewModel = new TransactionsViewModel();
        viewModel.init();
        viewModel.getTransaction_data().observe(getViewLifecycleOwner(),transactionsModel -> {
            if (transactionsModel.isStatus()){
                if (transactionsModel.getData().size() > 0){
                    binding.noTransactionLayout.setVisibility(View.GONE);
                    transactions = new ArrayList<>();
                    transactions.addAll(transactionsModel.getData());
                    buildRv(transactions);

                }else {
                    binding.noTransactionLayout.setVisibility(View.VISIBLE);
                }
            }else {

            }
        });

    }

    private void buildRv(List<DataItem> transactions) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        binding.transactionsList.setLayoutManager(layoutManager);
        TransactionAdapter adapter = new TransactionAdapter(Split.getAppContext(), transactions);
        binding.transactionsList.setAdapter(adapter);

    }

    private void initClickListeners() {
        binding.transactionToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.joinButtonGlobal.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.joinSearch);
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