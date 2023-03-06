package com.kaligotla.oms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RegisterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Register fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}