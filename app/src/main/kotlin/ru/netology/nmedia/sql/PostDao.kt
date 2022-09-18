


package ru.netology.nmedia.sql

import ru.netology.nmedia.Post

interface PostDao {
    //Dao - Data Access Object
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
}