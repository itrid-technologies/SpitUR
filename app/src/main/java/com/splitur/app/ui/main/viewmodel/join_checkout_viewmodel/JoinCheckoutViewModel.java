package com.splitur.app.ui.main.viewmodel.join_checkout_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.basic_model.BasicModel;
import com.splitur.app.data.model.promo.PromoResponse;
import com.splitur.app.data.repository.JoinCheckoutRepository;

public class JoinCheckoutViewModel extends ViewModel {


    private MutableLiveData<BasicModel> data;
    private MutableLiveData<PromoResponse> promoData;

    private JoinCheckoutRepository joinCheckoutRepository;
    String user_id,code;

    public JoinCheckoutViewModel(String id,String promo_code) {
        this.user_id = id;
        this.code = promo_code;
        joinCheckoutRepository = new JoinCheckoutRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = joinCheckoutRepository.checkUserEmail(user_id);
    }

    public void initPromo() {
        if (this.promoData != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        promoData = joinCheckoutRepository.apply(code);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }

    public MutableLiveData<PromoResponse> getPromoData() {
        return promoData;
    }
}
