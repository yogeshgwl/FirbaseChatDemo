package com.aucto.model


import com.google.gson.annotations.SerializedName

data class SourceX(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)