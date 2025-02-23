package com.example.baseproject.ui.home.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemPostBinding

class PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffUtil()) {

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(post: Post) {
            binding.apply {
                tvBody.text = post.shareContent
                tvLikes.text = post.likes.toString()
                tvComments.text = post.comments.toString()
                tvFullName.text = post.owner.fullName
                tvDate.text = post.owner.postDate

                Glide.with(ivAuthorProfile.context)
                    .load(post.owner.profile)
                    .circleCrop()
                    .into(ivAuthorProfile)

                val images: List<String>? = post.images

                when(images?.size) {
                    3 -> {
                        Glide.with(ivPost01.context)
                            .load(images[0])
                            .into(ivPost01)

                        Glide.with(ivPost02.context)
                            .load(images[1])
                            .into(ivPost02)

                        Glide.with(ivPost03.context)
                            .load(images[2])
                            .into(ivPost03)
                    }
                    2 -> {
                        ivPost03.visibility = View.GONE

                        Glide.with(ivPost01.context)
                            .load(images[0])
                            .into(ivPost01)

                        Glide.with(ivPost02.context)
                            .load(images[1])
                            .into(ivPost02)
                    }

                    1 -> {
                        ivPost02.visibility = View.GONE
                        ivPost03.visibility = View.GONE

                        Glide.with(ivPost01.context)
                            .load(images[0])
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_foreground)
                            .into(ivPost01)
                    }

                    else -> {
                        ivPost01.visibility = View.GONE
                        ivPost02.visibility = View.GONE
                        ivPost03.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: ItemPostBinding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}