package com.example.githubapp.data

import com.example.githubapp.data.model.GitNode
import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkService {

    @GET("repositories")
    fun fetchRepoList(): Observable<List<GitNode>>
}