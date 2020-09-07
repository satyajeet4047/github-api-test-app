package com.example.githubapp.ui.repodetail

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.githubapp.R
import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.ui.repolist.RepoListAdapter
import com.example.githubapp.util.ImageLoaderUtil
import com.example.githubapp.util.RequestStatus
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.repo_detail_fragment.*
import kotlinx.android.synthetic.main.repo_list_fragment.*
import kotlinx.android.synthetic.main.repo_list_item.*
import kotlinx.android.synthetic.main.repo_list_item.view.*
import javax.inject.Inject

class RepoDetailFragment : Fragment() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject lateinit var  imageLoaderUtil : ImageLoaderUtil
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: RepoDetailViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[RepoDetailViewModel::class.java]
    }

    private var gitNode: GitNode? = null

    private lateinit var adapter: RepoContributorAdapter
    private lateinit var alertDialog : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gitNode = arguments?.getSerializable("gitNode") as GitNode?
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repo_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var name: String? = null
        var login : String? = null
        gitNode?.name?.let { name = it }
        gitNode?.owner?.login?.let { login = it }
        if (name != null && login != null) {
                viewModel.fetchContributors(login!!, name!!)
        }
        setupUi()
        setupObservables()

    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    fun setupUi() {

        gitNode?.owner?.avatarUrl?.let { imageLoaderUtil.loadImage(iv_user_profile, it) }
        gitNode?.name?.let { tv_user_name.text = it }
        gitNode?.fullName?.let{tv_repo_link.text = it}
        gitNode?.description?.let { tv_description.text = it }

        rv_contributors.layoutManager = GridLayoutManager(activity,3) as RecyclerView.LayoutManager?
        adapter = RepoContributorAdapter(imageLoaderUtil)
        adapter.setUpListener(object : RepoContributorAdapter.ItemCLickedListener {
            override fun onItemClicked(contributorNode: ContributorNode) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        rv_contributors.adapter = adapter
    }


    private fun setupObservables() {

        viewModel.getResponse().observe(viewLifecycleOwner, Observer<List<ContributorNode>>{
                api : List<ContributorNode>? ->
            api?.let { adapter.setData(it as ArrayList<ContributorNode>) }

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
            true -> pg_contributors.visibility = View.VISIBLE
            else -> pg_contributors.visibility = View.GONE
        }
    }

}
