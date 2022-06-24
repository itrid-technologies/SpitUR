package split.com.app.ui.main.viewmodel.faq_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.faq.FaqListModel;
import split.com.app.data.model.get_avatar.AvatarModel;
import split.com.app.data.repository.avatar.AvatarRepository;
import split.com.app.data.repository.faq.FaqRepository;

public class FaqViewModel extends ViewModel {

    private MutableLiveData<FaqListModel> data;
    private FaqRepository faqRepository;

    public FaqViewModel() {
        faqRepository = new FaqRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = faqRepository.getFaqList();
    }

    public MutableLiveData<FaqListModel> getData() {
        return this.data;
    }
}
