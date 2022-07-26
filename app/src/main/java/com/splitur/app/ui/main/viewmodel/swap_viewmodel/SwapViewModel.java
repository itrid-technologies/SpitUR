package com.splitur.app.ui.main.viewmodel.swap_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.repository.swap_coins.SwapCoinsRepository;

public class SwapViewModel extends ViewModel {

    String coins;

    private MutableLiveData<BasicModel> coins_data;
    private final SwapCoinsRepository swapCoinsRepository;

    public SwapViewModel(String data) {
        this.coins = data;
        swapCoinsRepository = new SwapCoinsRepository();
    }

    public void init() {
        if (this.coins_data != null) {
            return;
        }
        coins_data = swapCoinsRepository.swap(coins);
    }

    public MutableLiveData<BasicModel> getCoins_data() {
        return this.coins_data;
    }

}
