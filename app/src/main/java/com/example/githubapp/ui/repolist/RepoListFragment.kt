package com.example.githubapp.ui.repolist

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.R
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.util.RequestStatus
import dagger.android.*
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.repo_list_fragment.*
import javax.inject.Inject


class RepoListFragment : Fragment() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel: RepoListViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[RepoListViewModel::class.java]
    }

    private lateinit var adapter: RepoListAdapter
    private lateinit var alertDialog : AlertDialog



    companion object {
        fun newInstance() = RepoListFragment()
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupObservables()
        mainViewModel.fetchData()
    }

    private fun setupObservables() {

        mainViewModel.getResponse().observe(viewLifecycleOwner, Observer<List<GitNode>>{
                apiResponse : List<GitNode>? ->
            apiResponse?.let { adapter.setData(it as ArrayList<GitNode>) }

        })

        mainViewModel.getStatus().observe(this, Observer<RequestStatus> {
                requestStatus: RequestStatus? ->
            when(requestStatus){
                RequestStatus.IN_PROGRESS ->  onRequestInProgress()
                RequestStatus.SUCCESS ->  onRequestSuccess()
                RequestStatus.FAILURE -> onRequestError()
            }
        })

    }

    /*
        setup ui components
     */
    private fun setupUI() {

        rv_repo_list.layoutManager = LinearLayoutManager(activity)
        adapter = RepoListAdapter()
        rv_repo_list.addItemDecoration(
            DividerItemDecoration(
                rv_repo_list.context,
                (rv_repo_list.layoutManager as LinearLayoutManager).orientation
            )
        )
        rv_repo_list.adapter = adapter
    }


    private fun onRequestInProgress(){
        adapter.clearList()
        showProgressbar(true)
    }

    private fun onRequestSuccess(){
        showProgressbar(false)
    }

    private fun onRequestError() {

        showProgressbar(false)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.request_error_dialog_title))
            .setMessage(getString(R.string.request_error_dialog_message))
            .setPositiveButton(getString(R.string.request_error_dialog_ok_btn)) { dialog, whichButton ->
                dialog.dismiss()
            }
            .setCancelable(false)

        alertDialog = builder.create()
        alertDialog?.show()

    }

    private fun showProgressbar(visibility : Boolean?){

        when(visibility){
            true -> progressBar.visibility = View.VISIBLE
            else -> progressBar.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        alertDialog?.dismiss()
        mainViewModel.onDestroy()
    }




}
