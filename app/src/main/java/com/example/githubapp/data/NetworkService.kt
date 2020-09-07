package com.example.githubapp.data

import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("repositories")
    fun fetchRepoList(): Observable<List<GitNode>>

    @GET("repos/{login}/{name}/contributors?page=*&?")
    fun fetchContributors(@Path("login" , encoded = false) login : String,@Path("name" , encoded = false) repoName : String,
                          @Query("access_token") accessToken : String)
            : Observable<List<ContributorNode>>
}