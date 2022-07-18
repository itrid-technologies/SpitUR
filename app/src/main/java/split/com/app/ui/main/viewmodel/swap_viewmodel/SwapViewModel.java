package split.com.app.ui.main.viewmodel.swap_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.repository.swap_coins.SwapCoinsRepository;
import split.com.app.data.repository.user_id.UserIdRepository;

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
