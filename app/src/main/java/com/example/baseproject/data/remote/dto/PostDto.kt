package com.example.baseproject.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("id") val id: Int,
    @SerialName("images") val images: List<String>?,
    @SerialName("title") val title: String,
    @SerialName("comments") val comments: Int,
    @SerialName("likes") val likes: Int,
    @SerialName("share_content") val shareContent: String,
    @SerialName ("owner") val owner: OwnerDto,
) {
    @Serializable
    data class OwnerDto(
        @SerialName("first_name") val firstName: String,
        @SerialName("last_name") val lastName: String,
        @SerialName("profile") val profile: String?,
        @SerialName("post_date") val postDate: Long
    )
}
