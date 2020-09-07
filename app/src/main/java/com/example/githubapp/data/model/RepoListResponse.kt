package com.example.githubapp.data.model

import com.google.gson.annotations.SerializedName


 data class GitNode(

    @SerializedName("id")
    val id : Int,

    @SerializedName("name")
    val name : String,

    @SerializedName("full_name")
    val fullName : String,

    @SerializedName("description")
    val description : String,

    @SerializedName("owner")
    val owner: Owner
)

data class Owner(

    @SerializedName("avatar_url")
    val avatarUrl : String
)
