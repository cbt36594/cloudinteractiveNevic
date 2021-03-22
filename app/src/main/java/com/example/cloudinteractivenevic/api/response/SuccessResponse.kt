package com.example.cloudinteractivenevic.api.response

import com.google.gson.annotations.SerializedName

data class SuccessResponse<T>(
    override val Meta: Meta,
    @SerializedName("Data") val data: T
) : BaseResponse
