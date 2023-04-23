package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.sql.PostDao
import ru.netology.nmedia.sqlRoom.PostEntity

class PostRepositorySQLiteImpl(
    private val dao: PostDao
):PostRepository {


    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }
    override fun getPostById(id: Long): Post {
        //val post1Element = posts.filter { it.id == id }
        //if (post1Element.size == 0) return null

        return Post(0, "", "", "")
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
    }

    override fun save(post: Post) {
        dao.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }

    override fun editById(id: Long, content: String) {
        dao.updateContentById(id, content)
    }


}