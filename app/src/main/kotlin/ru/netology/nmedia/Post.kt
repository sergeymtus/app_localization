package ru.netology.nmedia

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likesCount: Int,
    val shareCountValue: Int,
    val viewCountValue: Int,
    val likedByMe: Boolean = false,
    val video: String = ""

)