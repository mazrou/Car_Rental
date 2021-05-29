package com.example.a1171832jumanafinalproj.ui.car;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is car list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}