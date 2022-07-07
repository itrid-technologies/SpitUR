package split.com.app.ui.main.viewmodel.join_checkout_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.PromoModel;
import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.model.join_group.JoinGroupModel;
import split.com.app.data.repository.JoinCheckoutRepository;
import split.com.app.data.repository.join_group.JoinGroupRepository;

public class JoinCheckoutViewModel extends ViewModel {


    private MutableLiveData<BasicModel> data;
    private MutableLiveData<PromoModel> promoData;

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

    public MutableLiveData<PromoModel> getPromoData() {
        return promoData;
    }
}
