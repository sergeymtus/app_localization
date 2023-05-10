package ru.netology.nmedia.sqlRoom

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post
import androidx.room.Embedded
import ru.netology.nmedia.dto.Attachment


@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String? = null,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likes: Int = 0,
    val shares: Int = 0,
    @Embedded
    val attachment: Attachment? = null,
) {
//    fun toDto() = Post(id, author, content, published, likedByMe, likes, shares, video)
fun toDto() =
    Post(
        id = id,
        author = author,
        content = content,
        authorAvatar = authorAvatar,
        published = published,
        likedByMe = likedByMe,
        likes = likes,
        shares = shares,
        attachment = attachment
    )

    companion object {
        fun fromDto(dto: Post) =
//            PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likedByMe, dto.likes, dto.shares, dto.video)
            PostEntity(
                id = dto.id,
                author = dto.author,
                authorAvatar = dto.authorAvatar,
                content = dto.content,
                published = dto.published,
                likedByMe = dto.likedByMe,
                likes = dto.likes,
                shares = dto.shares,
                attachment = dto.attachment
            )

    }
}