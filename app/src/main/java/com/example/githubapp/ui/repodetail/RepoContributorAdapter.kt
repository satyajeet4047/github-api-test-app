package com.example.githubapp.ui.repodetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.R
import com.example.githubapp.data.model.ContributorNode
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.util.ImageLoaderUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.contributor_lis_item.view.*
import kotlinx.android.synthetic.main.repo_list_item.view.*
import javax.inject.Inject

class RepoContributorAdapter constructor(private val imageLoaderUtil : ImageLoaderUtil): RecyclerView.Adapter<RepoContributorAdapter.DataViewHolder>() {


    private var list: ArrayList<ContributorNode> = ArrayList()

    lateinit var mItemCLicked: ItemCLickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contributor_lis_item, parent, false)
        return DataViewHolder(view)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(list[position])
            }

        }
        imageLoaderUtil.loadImage(holder.itemView.imgur_thumbnail,list[position].avatarUrl)
        holder.bind(list[position])
    }


    fun setData(list : ArrayList<ContributorNode>){
        this.list = list
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return list.size
    }


    fun setUpListener(itemCLicked: ItemCLickedListener){
        mItemCLicked = itemCLicked
    }


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.tv_contributor_name

        fun bind(response: ContributorNode) {
            userName.text = response.login
        }
    }

    interface ItemCLickedListener {
        fun onItemClicked(contributorNode: ContributorNode)
    }

}