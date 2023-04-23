package ru.netology.nmedia.ui

import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onVideoLink(post: Post) {}
    fun onOpenPost(post: Post) {}
}