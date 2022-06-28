package com.Evelyndamayantiambarita.challengechapter5.data.api.auth

import com.google.gson.annotations.SerializedName

/**
 * com.Evelyndamayantiambarita.challengchapter5.common
 *
 * Created by Evelyndamayantiambarita on 29/06/2022.
 * https://github.com/EvelynAmbarita
 *
 */

data class LogoutResponse(
    @SerializedName("objectId") var objectId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("job") var job: String? = null,
    @SerializedName("image") var image: String? = null
)