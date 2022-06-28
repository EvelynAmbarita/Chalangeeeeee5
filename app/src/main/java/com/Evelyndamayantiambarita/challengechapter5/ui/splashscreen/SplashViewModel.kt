package com.Evelyndamayantiambarita.challengechapter5.ui.splashscreen

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Evelyndamayantiambarita.challengechapter5.Constant

class SplashViewModel : ViewModel() {
    val shouldOpenOnMainActivity: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenLoginActivity: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded(sharedPreferences: SharedPreferences) {
        if (sharedPreferences.getString(Constant.Preferences.KEY.TOKEN, "").isNullOrEmpty()) {
            shouldOpenOnMainActivity.postValue(true)
        } else {
            shouldOpenLoginActivity.postValue(true)
        }
    }
}