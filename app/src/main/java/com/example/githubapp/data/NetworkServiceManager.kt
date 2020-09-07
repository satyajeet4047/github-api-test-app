package com.example.githubapp.data

import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import io.reactivex.Observable
import javax.inject.Inject




class NetworkServiceManager @Inject constructor(private val networkService: NetworkService) {

   fun fetchRepoList() : Observable<List<GitNode>>{
       return  networkService.fetchRepoList()
   }

    fun fetchRepositoryByContributor(userName : String) : Observable<List<GitNode>>{
       return  networkService.fetchRepositoryByContributor(userName)
   }


    fun fetchContributors(login:String,repoName : String, accessToken : String)
            : Observable<List<ContributorNode>>{
        return networkService.fetchContributors(login,repoName,accessToken)
    }
}