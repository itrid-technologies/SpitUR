package com.splitur.app.ui.main.viewmodel.faq_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.faq.FaqListModel;
import com.splitur.app.data.repository.faq.FaqRepository;

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
