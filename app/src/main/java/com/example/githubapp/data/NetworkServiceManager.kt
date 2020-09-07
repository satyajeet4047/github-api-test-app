package com.example.githubapp.data

import com.example.githubapp.data.model.GitNode
import io.reactivex.Observable
import javax.inject.Inject




class NetworkServiceManager @Inject constructor(private val networkService: NetworkService) {

   fun fetchRepoList() : Observable<List<GitNode>>{
       return  networkService.fetchRepoList()
   }
}