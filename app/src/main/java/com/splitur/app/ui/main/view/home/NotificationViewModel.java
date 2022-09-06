package com.splitur.app.ui.main.view.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.notification.NotificationModel;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<NotificationModel> notifications;
    private MutableLiveData<NotificationCountModel> notificationsCount;

    NotificationRepository notificationRepository;
    int page;



    public NotificationViewModel(int nextPage) {
        this.page = nextPage;
        notificationRepository = new NotificationRepository();
    }

    public void init() {
        if (this.notifications != null) {
            return;
        }
        notifications = notificationRepository.getNotificationData(page);
    }

    public void initCount() {
        if (this.notificationsCount != null) {
            return;
        }
        notificationsCount = notificationRepository.getNotificationCountData();
    }

    public MutableLiveData<NotificationModel> getNotifications() {
        return notifications;
    }

    public MutableLiveData<NotificationCountModel> getNotificationsCount() {
        return notificationsCount;
    }
}
