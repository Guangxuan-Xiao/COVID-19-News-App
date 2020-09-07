package com.example.covid_news.ui.expert;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExpertViewModel extends ViewModel {

    private static MutableLiveData<String> mText;

    public ExpertViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is expert fragment");
    }

    public static LiveData<String> getText() {
        return mText;
    }
}