package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.intellij.lang.annotations.JdkConstants
//import ru.netology.databinding.CardPostBinding
import ru.netology.nmedia.Post
import ru.netology.nmedia.PostViewModel
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.PostViewHolder
import ru.netology.nmedia.ui.PostDiffCallback


//typealias OnLikeListener = (post: Post) -> Unit
//typealias OnShareListener = (post: Post) -> Unit
//typealias OnRemoveListener = (post: Post) -> Unit
interface PostInteractionListener {
    fun onEdit(post: Post)
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onShare(post: Post)
    fun onVideoLink(post: Post) {}
}

//class PostsAdapter (private val onLikeListener: OnLikeListener): RecyclerView.Adapter<PostViewHolder>(){
//    var list = emptyList<Post>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
//        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return PostViewHolder(binding, onLikeListener)
//    }
//
//    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
//        val post = list[position]
//        holder.bind(post)
//    }
//
//  override fun getItemCount(): Int = list.size
//}

class PostsAdapter(
    private val listener: PostInteractionListener
//    private val onLikeListener: OnLikeListener,
//    private val onShareListener: OnShareListener,
//
//    private val onRemoveListener: OnRemoveListener


) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}


