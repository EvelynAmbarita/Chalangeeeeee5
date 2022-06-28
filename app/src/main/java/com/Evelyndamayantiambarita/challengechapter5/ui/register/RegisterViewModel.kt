package com.Evelyndamayantiambarita.challengechapter5.ui.register

import android.content.SharedPreferences
import android.util.Patterns
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.Evelyndamayantiambarita.challengechapter5.Constant
import com.Evelyndamayantiambarita.challengechapter5.data.ErrorResponse
import com.Evelyndamayantiambarita.challengechapter5.network.AuthApiClient
import com.Evelyndamayantiambarita.myapplication.data.api.auth.SignInRequest
import com.Evelyndamayantiambarita.challengechapter5.data.api.auth.SignUpRequest
import com.Evelyndamayantiambarita.challengechapter5.data.local.UserEntity
import com.Evelyndamayantiambarita.challengechapter5.database.MyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class RegisterViewModel : ViewModel() {
    private var db: MyDatabase? = null
    private var pref: SharedPreferences? = null

    private var name: String = ""
    private var job: String = ""
    private var email: String = ""
    private var password: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenUpdateProfile: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded(db: MyDatabase, preferences: SharedPreferences) {
        this.db = db
        this.pref = preferences
    }

    fun onChangeName(name: String) {
        this.name = name
    }

    fun onChangeJob(job: String) {
        this.job = job
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onValidate() {
        if (name.isEmpty() && name.length < 3) {
            shouldShowError.postValue("Nama tidak valid")
        } else if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            shouldShowError.postValue("Password tidak valid")
        } else {
            processToSignUp()
        }
    }

    private fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val request = SignUpRequest(
                name = name,
                email = email,
                job = job,
                password = password
            )
            val result = AuthApiClient.instanceAuth.signUp(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    processToSignIn()
                } else {
                    showErrorMessage(result.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    private fun processToSignIn() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = SignInRequest(
                email = email,
                password = password
            )
            val response = AuthApiClient.instanceAuth.signIn(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    signInResponse?.let {
                        // mempersiapkan untuk simpan token
                        insertToken(it.token)

                        // mempersiapkan untuk insert ke database
                        val userEntity = UserEntity(
                            id = it.user.id.toString(),
                            name = it.user.name,
                            job = it.user.job,
                            email = it.user.email,
                            token = it.token,
                            image = it.user.image
                        )
                        insertProfile(userEntity)
                    }
                    shouldShowLoading.postValue(false)
                } else {
                    showErrorMessage(response.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    private fun showErrorMessage(response: ResponseBody?) {
        val error =
            Gson().fromJson(response?.string(), ErrorResponse::class.java)
        shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            pref?.edit {
                putString(Constant.Preferences.KEY.TOKEN, token)
                apply()
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = db?.userDAO()?.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenUpdateProfile.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, Gagal insert ke dalam database")
                }
            }
        }
    }
}