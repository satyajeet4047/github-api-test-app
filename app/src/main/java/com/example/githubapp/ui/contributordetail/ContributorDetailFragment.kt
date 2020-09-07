package com.example.githubapp.ui.contributordetail

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.githubapp.R
import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.ui.adapater.CommonRepoListAdapter
import com.example.githubapp.util.ImageLoaderUtil
import com.example.githubapp.util.RequestStatus
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.contributor_lis_item.*
import kotlinx.android.synthetic.main.repo_list_fragment.*
import javax.inject.Inject

class ContributorDetailFragment : Fragment() {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>



    @Inject
    lateinit var  imageLoaderUtil : ImageLoaderUtil

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ContributorDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[ContributorDetailViewModel::class.java]
    }

    private  var alertDialog : AlertDialog?  = null
    private lateinit var adapter: CommonRepoListAdapter
    private var contributorNode: ContributorNode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contributorNode = arguments?.getSerializable("contributorNode") as ContributorNode?
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contributor_detail_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUI()
        setupObservables()
    }

    private fun setupUI() {

        contributorNode?.avatarUrl?.let {  imageLoaderUtil.loadImage(iv_contributor_thumbnail,it)}
        contributorNode?.login?.let {  tv_contributor_name.text = it}


        rv_repo_list.layoutManager = LinearLayoutManager(activity)
        adapter = CommonRepoListAdapter(imageLoaderUtil, false)
        rv_repo_list.adapter = adapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        contributorNode?.login?.let{viewModel.fetchRepoByContributor(it)}
    }


    private fun setupObservables() {

        viewModel.getResponse().observe(viewLifecycleOwner, Observer<List<GitNode>>{
                api : List<GitNode>? ->
            api?.let { adapter.setData(it as ArrayList<GitNode>) }

        })

        viewModel.getStatus().observe(this, Observer<RequestStatus> {
                requestStatus: RequestStatus? ->
            when(requestStatus){
                RequestStatus.IN_PROGRESS ->  onRequestInProgress()
                RequestStatus.SUCCESS ->  onRequestSuccess()
                RequestStatus.FAILURE -> onRequestError()
            }
        })

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

        alertDialog?.let { if(it.isShowing) { it.dismiss()} }
        viewModel.onDestroy()
    }
}
