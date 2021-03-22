package com.example.cloudinteractivenevic.model

import com.google.gson.annotations.SerializedName

data class SuccessResponse<T>(
    override val Meta: Meta,
    @SerializedName("Data") val data: T
) : BaseResponse
