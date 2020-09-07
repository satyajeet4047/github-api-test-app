package com.example.githubapp.ui.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.R
import com.example.githubapp.data.model.GitNode
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.repo_list_item.view.*

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.DataViewHolder>() {

    private var list: ArrayList<GitNode> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return DataViewHolder(view)
    }


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        return holder.bind(list[position])
    }


    fun setData(list : ArrayList<GitNode>){
        this.list = list
        notifyDataSetChanged()
    }

    fun clearList() {
       list.clear()
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.iv_user_profile
        private val userName: TextView = itemView.tv_user_name
        private val repoLink:TextView = itemView.tv_repo_link


        fun bind(response: GitNode) {

            userName.text = response.name
            repoLink.text = response.fullName
            loadImage(response.owner.avatarUrl)
        }

        private fun loadImage(avatarUrl: String) {

            val context = imageView.context
            Picasso.with(context)
                .load(avatarUrl)
                .centerCrop()
                .resize(200,200)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.ic_error_black_24dp)
                .into(imageView, object : Callback {
                    override fun onSuccess() { //..image loaded from cache
                    }

                    override fun onError() { //Try again online if cache failed
                        Picasso.with(context)
                            .load(avatarUrl)
                            .resize(200,200)
                            .centerCrop()
                            .error(R.drawable.ic_error_black_24dp)
                            .into(imageView, object : Callback {
                                override fun onSuccess() { }
                                override fun onError() {}
                            })
                    }
                })
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}