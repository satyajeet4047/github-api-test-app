package com.example.githubapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


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
) : Serializable

data class Owner(

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl : String
) : Serializable


data class ContributorNode(
    @SerializedName("login")
    val login : String,

    @SerializedName("avatar_url")
    val avatarUrl : String
)
