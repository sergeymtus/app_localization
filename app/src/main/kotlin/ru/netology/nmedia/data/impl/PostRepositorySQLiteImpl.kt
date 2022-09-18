import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.sql.PostDao


class PostRepositorySQLiteImpl(
    private val dao: PostDao
): PostRepository {
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun getPostById(id: Long): Post {
        val post1Element = posts.filter { it.id == id }
        //if (post1Element.size == 0) return null

        return post1Element.first()
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map {
            if (it.id != id) it else
                if (it.likedByMe) it.copy(likedByMe = !it.likedByMe, likesCount = it.likesCount - 1)
                else it.copy(likedByMe = !it.likedByMe, likesCount = it.likesCount + 1)
        }

        data.value = posts
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(shareCountValue = it.shareCountValue + 1)
        }

        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map { if (it.id != id) it else saved }
        }

        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun editById(id: Long, content: String) {
        posts = posts.map {
            if (it.id != id) it else it.copy(content = content)
        }

        data.value = posts
        dao.save(posts.filter { it.id == id }.first())
    }

}