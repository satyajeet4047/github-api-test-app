package com.example.githubapp.ui.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.R
import com.example.githubapp.data.model.GitNode
import com.example.githubapp.util.ImageLoaderUtil
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_item.*
import kotlinx.android.synthetic.main.repo_list_item.view.*
import javax.inject.Inject

class RepoListAdapter constructor(private val imageLoaderUtil : ImageLoaderUtil) : RecyclerView.Adapter<RepoListAdapter.DataViewHolder>() {


    private var list: ArrayList<GitNode> = ArrayList()

    lateinit var mItemCLicked: ItemCLickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return DataViewHolder(view)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            mItemCLicked.let {
                mItemCLicked.onItemClicked(list[position])
            }

        }
        imageLoaderUtil.loadImage(holder.itemView.iv_user_profile,list[position].owner.avatarUrl)

        holder.bind(list[position])
    }


    fun setData(list : ArrayList<GitNode>){
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

        private val userName: TextView = itemView.tv_user_name
        private val repoLink:TextView = itemView.tv_repo_link


        fun bind(response: GitNode) {
            userName.text = response.name
            repoLink.text = response.fullName
        }

    }


    interface ItemCLickedListener {
        fun onItemClicked(gitNode: GitNode )
    }


}