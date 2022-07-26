package com.splitur.app.ui.main.viewmodel.balance_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.basic_model.BasicModel1;
import com.splitur.app.data.model.wallet_balance.WalletBalanceModel;
import com.splitur.app.data.repository.wallet_balance.WalletBalanceRepository;

public class WalletBalanceViewModel extends ViewModel {

    String coins , upi, refund;

    private MutableLiveData<WalletBalanceModel> balance;
    private MutableLiveData<BasicModel1> refund_data;

    private final WalletBalanceRepository balanceRepository;

    public WalletBalanceViewModel(String id, String upiId, String refund_amount) {
        this.coins = id;
        this.upi = upiId;
        this.refund = refund_amount;
        balanceRepository = new WalletBalanceRepository();
    }

    public void init() {
        if (this.balance != null) {
            return;
        }
        balance = balanceRepository.getBalance(coins);
    }

    public void initRefund() {
        if (this.refund_data != null) {
            return;
        }
        refund_data = balanceRepository.reFund(upi,refund);
    }

    public MutableLiveData<WalletBalanceModel> getBalance() {
        return balance;
    }

    public MutableLiveData<BasicModel1> getRefund_data() {
        return refund_data;
    }
}
