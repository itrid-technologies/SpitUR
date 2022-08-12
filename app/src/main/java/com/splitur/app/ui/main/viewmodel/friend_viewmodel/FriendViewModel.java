package com.splitur.app.ui.main.viewmodel.friend_viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.splitur.app.data.model.friend_list.FriendListModel;
import com.splitur.app.data.repository.friends.FriendsRepository;

public class FriendViewModel extends ViewModel {

    private MutableLiveData<FriendListModel> data;
    private FriendsRepository friendsRepository;

    public FriendViewModel() {
        friendsRepository = new FriendsRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = friendsRepository.getFriendList();
    }

    public MutableLiveData<FriendListModel> getData() {
        return this.data;
    }
}
