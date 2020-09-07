package com.example.githubapp.ui.repodetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.githubapp.data.NetworkServiceManager
import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.util.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(private val networkServiceManager: NetworkServiceManager, private val accessToken :String): ViewModel() {
    // TODO: Implement the ViewModel



    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    private val response: MutableLiveData<List<ContributorNode>> = MutableLiveData()
    private val status: MutableLiveData<RequestStatus> = MutableLiveData()


    fun getResponse(): MutableLiveData<List<ContributorNode>> {
        return response
    }

    fun getStatus(): MutableLiveData<RequestStatus> {
        return status
    }


    /*
        Method used for fetching data from api
     */
    fun fetchContributors(login:String,repo: String) {

        status.value = RequestStatus.IN_PROGRESS
        compositeDisposable.add(networkServiceManager.fetchContributors(login,repo ,accessToken)
            .flatMapIterable { x -> x }
            .take(20)
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::onSuccess,this::onFailure)
        )

    }

    private fun onSuccess(response: List<ContributorNode>){
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

