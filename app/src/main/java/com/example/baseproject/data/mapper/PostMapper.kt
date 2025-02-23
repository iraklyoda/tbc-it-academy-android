package com.example.baseproject.data.mapper

import com.example.baseproject.data.remote.dto.PostDto
import com.example.baseproject.ui.home.post.Post

fun PostDto.toPostUi(): Post {
    return Post(
        id = this.id,
        images = this.images,
        title = this.title,
        comments = this.comments,
        likes = this.likes,
        shareContent = this.shareContent,
        owner = Post.Owner(
            fullName = "${this.owner.firstName} ${this.owner.lastName}",
            profile = this.owner.profile,
            postDate = this.owner.postDate
        )
    )
}