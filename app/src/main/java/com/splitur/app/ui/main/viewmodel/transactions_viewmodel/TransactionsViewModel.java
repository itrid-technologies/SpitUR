package com.splitur.app.ui.main.viewmodel.transactions_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.TransactionsModel;
import com.splitur.app.data.repository.transactionRepository.TransactionRepository;

public class TransactionsViewModel extends ViewModel {

    private MutableLiveData<TransactionsModel> transaction_data;
    private TransactionRepository transactionRepository;

    public TransactionsViewModel() {
        transactionRepository = new TransactionRepository();
    }

    public void init() {
        if (this.transaction_data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        transaction_data = transactionRepository.transactions();
    }

    public MutableLiveData<TransactionsModel> getTransaction_data() {
        return this.transaction_data;
    }
}
