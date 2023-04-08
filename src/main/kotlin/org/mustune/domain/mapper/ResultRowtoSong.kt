package org.mustune.domain.mapper

import org.jetbrains.exposed.sql.ResultRow
import org.mustune.domain.data.songs.FavouriteSongs
import org.mustune.domain.data.songs.ShareSongs
import org.mustune.domain.data.songs.Songs
import org.mustune.domain.model.Song
import org.mustune.util.extentions.toDate
import java.util.*

fun ResultRow.toSong(userId: UUID?) = Song(
    id = this[Songs.id].value,
    title = this[Songs.title],
    artist = this[Songs.artist],
    isDownloadable = this[Songs.isDownloadable],
    isFavourite = getOrNull(FavouriteSongs.userId) != null,
    isShared = getOrNull(ShareSongs.userId) != null,
    isCreator = getOrNull(Songs.createdBy) == userId,
    shareType = this[Songs.shareType],
    createdAt = this[Songs.createdAt].toDate(),
    updatedAt = this[Songs.updatedAt].toDate(),
    createdBy = this[Songs.createdBy]
)