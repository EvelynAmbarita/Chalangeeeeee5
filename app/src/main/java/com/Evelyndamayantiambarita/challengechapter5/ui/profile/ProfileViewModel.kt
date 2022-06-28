package com.Evelyndamayantiambarita.challengechapter5.ui.profile

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Evelyndamayantiambarita.challengechapter5.database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private var db: MyDatabase? = null
    private var pref: SharedPreferences? = null
    val shouldShowDataUser: MutableLiveData<DataUser> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded(db: MyDatabase, preferences: SharedPreferences) {
        this.db = db
        this.pref = preferences
    }

    fun getUser(email: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDAO()?.getUsername(email)
            user?.let {
                val data  = DataUser(
                    username = it.name,
                    job = it.job,
                    email = it.email,
                    password = it.token.toString()
                )
                shouldShowDataUser.postValue(data)
            } ?: run {
                shouldShowError.postValue("Data Tidak Ditemukan")
            }

        }
    }
}