package com.example.githubapp.ui.repolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.githubapp.data.NetworkServiceManager
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.util.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoListViewModel @Inject constructor(private val networkServiceManager: NetworkServiceManager) : ViewModel() {

    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    private val response: MutableLiveData<List<GitNode>> = MutableLiveData()
    private val status: MutableLiveData<RequestStatus> = MutableLiveData()


    fun getResponse(): MutableLiveData<List<GitNode>>{
            return response
    }

    fun getStatus(): MutableLiveData<RequestStatus> {
        return status
    }


    /*
        Method used for fetching data from api
     */
    fun fetchTopRepos() {

        if(status.value == RequestStatus.SUCCESS) {
            return
        }
            status.value = RequestStatus.IN_PROGRESS
            compositeDisposable.add(networkServiceManager.fetchRepoList()
                .flatMapIterable { x -> x }
                .take(20)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onSuccess, this::onFailure)
            )

    }


    private fun onSuccess(response: List<GitNode>){
        this.response.value = response
        status.value = RequestStatus.SUCCESS
    }

    private fun onFailure(error : Throwable){
        this.response.value = null
        status.value = RequestStatus.FAILURE

    }

    /*
        Clear observers on activity destroy call
     */
    fun onDestroy() {
        compositeDisposable.clear()
    }


}
