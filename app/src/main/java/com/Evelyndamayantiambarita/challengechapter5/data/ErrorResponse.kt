package com.Evelyndamayantiambarita.challengechapter5.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null,
)