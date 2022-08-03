package com.splitur.app.ui.main.view.friends;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.splitur.app.R;
import com.splitur.app.data.model.contact.ContactModel;
import com.splitur.app.data.model.friend_list.DataItem;
import com.splitur.app.databinding.FragmentFriendsBinding;
import com.splitur.app.ui.main.adapter.friend.FriendListAdapter;
import com.splitur.app.ui.main.view.dashboard.Dashboard;
import com.splitur.app.ui.main.viewmodel.friend_viewmodel.FriendViewModel;
import com.splitur.app.utils.Split;


public class Friends extends Fragment {


    FragmentFriendsBinding binding;
    FriendViewModel viewModel;
    private List<DataItem> friend_data;
    List<ContactModel> contactList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater, container, false);
        Dashboard.hideNav(true);
        binding.friendToolbar.title.setText("Friends");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactList = new ArrayList<>();
        initClickListeners();
      //  contactList.addAll(getContacts(requireContext()));
        if (contactList.size() > 0){
//            buildFriendRv1(contactList);
            binding.noFriendLayout.setVisibility(View.VISIBLE);
            binding.friendsList.setVisibility(View.GONE);
        }else {
            binding.noFriendLayout.setVisibility(View.VISIBLE);
            binding.friendsList.setVisibility(View.GONE);
        }

    }

    private void buildFriendRv1(List<ContactModel> contactList) {

        binding.noFriendLayout.setVisibility(View.GONE);
        binding.friendsList.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
        binding.friendsList.setLayoutManager(layoutManager);
        FriendListAdapter adapter = new FriendListAdapter(Split.getAppContext(), contactList);
        binding.friendsList.setAdapter(adapter);
    }

    private void initClickListeners() {

        binding.friendToolbar.back.setOnClickListener(view -> {
            Navigation.findNavController(view).navigateUp();
        });

        binding.friendSearchView.removeLetter.setOnClickListener(view -> {
            binding.friendSearchView.searchField.setText("");
        });

        binding.friendSearchView.searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    binding.friendSearchView.removeLetter.setVisibility(View.VISIBLE);
                } else {
                    binding.friendSearchView.removeLetter.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable data) {

                if (!data.toString().isEmpty()) {
                    //getSearchedData(data.toString());
                }
            }
        });
    }

    private void getSearchedData(String data) {
        viewModel = new FriendViewModel(data);
        viewModel.init();
        viewModel.getData().observe(getViewLifecycleOwner(), friendListModel -> {
            if (friendListModel.isStatus()) {
                friend_data = new ArrayList<>();
                if (friendListModel.getData().size() > 0) {
                    friend_data.addAll(friendListModel.getData());
                  //  buildFriendRv(friend_data);
                } else {
                    binding.noFriendLayout.setVisibility(View.VISIBLE);
                    binding.friendsList.setVisibility(View.GONE);
                }
            }
        });
    }
//
//    private void buildFriendRv(List<DataItem> friend_data) {
//
//        binding.noFriendLayout.setVisibility(View.GONE);
//        binding.friendsList.setVisibility(View.VISIBLE);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(Split.getAppContext(), RecyclerView.VERTICAL, false);
//        binding.friendsList.setLayoutManager(layoutManager);
//        FriendListAdapter adapter = new FriendListAdapter(Split.getAppContext(), friend_data);
//        binding.friendsList.setAdapter(adapter);
//    }




    @SuppressLint("Range")
    public List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        info.photo = photo;
                        info.photoURI = pURI;
                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }
}