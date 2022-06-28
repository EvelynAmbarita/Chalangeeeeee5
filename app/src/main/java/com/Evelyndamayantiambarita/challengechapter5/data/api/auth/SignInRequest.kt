package com.Evelyndamayantiambarita.challengechapter5.data.api.auth

import com.google.gson.annotations.SerializedName

/**
 * com.Evelyndamayantiambarita.challengchapter5.common
 *
 * Created by Evelyndamayantiambarita on 29/06/2022.
 * https://github.com/EvelynAmbarita
 *
 */

data class SignInRequest(
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("device_name") var device_name: String? = null,
)