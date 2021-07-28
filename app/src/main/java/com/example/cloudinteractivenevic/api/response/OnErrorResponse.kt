package com.example.cloudinteractivenevic.api.response

import com.google.gson.annotations.SerializedName

data class OnErrorResponse(
    @SerializedName("Error")
    val error: AppError,
    override val Meta: Meta
) : BaseResponse

data class AppError(
    @SerializedName("Status")
    val status: String = "",
    @SerializedName("Title")
    val title: String = "",
    @SerializedName("Detail")
    val detail: String = ""
)
