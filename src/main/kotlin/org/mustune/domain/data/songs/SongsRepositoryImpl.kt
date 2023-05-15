package org.mustune.domain.data.songs

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.mustune.domain.data.dbQuery
import org.mustune.domain.data.users.Users
import org.mustune.domain.mapper.toSong
import org.mustune.domain.model.Song
import org.mustune.domain.repository.SongsRepository
import org.mustune.entities.MusicTab
import org.mustune.entities.SearchFilter
import org.mustune.entities.ShareType
import org.mustune.util.extentions.toLocalDateTime
import java.util.*

class SongsRepositoryImpl : SongsRepository {
    override suspend fun getSong(userId: UUID?, songId: UUID): Song? = dbQuery {
        Songs.leftJoin(Users, { Songs.createdBy }, { Users.id })
            .leftJoin(FavouriteSongs, { Songs.id }, { FavouriteSongs.songId })
            .leftJoin(ShareSongs, { Songs.id }, { ShareSongs.songId })
            .select { Songs.id eq songId }
            .map { it.toSong(userId) }
            .singleOrNull()
    }

    override suspend fun addSong(userId: UUID?, song: Song): Song? = transaction {
        val songId = Songs.insertAndGetId {
            it[title] = song.title
            it[artist] = song.artist
            it[isDownloadable] = song.isDownloadable
            it[shareType] = song.shareType
            it[createdAt] = Calendar.getInstance().time.toLocalDateTime()
            it[updatedAt] = Calendar.getInstance().time.toLocalDateTime()
            it[createdBy] = userId
        }.value

        (Songs leftJoin Users)
            .select { Songs.id eq songId }
            .map { it.toSong(userId) }
            .singleOrNull()
    }

    override suspend fun editSong(userId: UUID, song: Song): Boolean = dbQuery {
        val result = Songs.update({ Songs.id eq song.id }) {
            it[title] = song.title
            it[artist] = song.artist
            it[isDownloadable] = song.isDownloadable
            it[shareType] = song.shareType
            it[updatedAt] = Calendar.getInstance().time.toLocalDateTime()
        } > 0
        result
    }

    override suspend fun deleteSong(songId: UUID): Boolean = dbQuery {
        Songs.deleteWhere { Songs.id eq songId } > 0
    }

    override suspend fun addSongToFavourite(userId: UUID, songId: UUID): Song? {
        dbQuery {
            FavouriteSongs.insert {
                it[FavouriteSongs.userId] = userId
                it[FavouriteSongs.songId] = songId
            }
        }
        return getSong(userId, songId)
    }

    override suspend fun removeSongFromFavourite(userId: UUID, songId: UUID): Song? {
        dbQuery {
            FavouriteSongs.deleteWhere {
                (FavouriteSongs.userId eq userId) and (FavouriteSongs.songId eq songId)
            }
        }
        return getSong(userId, songId)
    }

    override suspend fun getSongsCategories(userId: UUID): List<MusicTab> = dbQuery {
        Users.select { Users.id eq userId }.count() > 0
        val isExplore = Songs.select { Songs.shareType eq ShareType.ALL_USERS }.count() > 0
        val isFavourite = FavouriteSongs.select { FavouriteSongs.userId eq userId }.count() > 0
        val isShared = ShareSongs.select { ShareSongs.userId eq userId }.count() > 0
        val isPersonal = Songs.select { Songs.createdBy eq userId }.count() > 0
        buildList {
            if (isExplore) add(MusicTab.EXPLORE)
            if (isFavourite) add(MusicTab.FAVOURITE)
            if (isShared) add(MusicTab.SHARED)
            if (isPersonal) add(MusicTab.PERSONAL)
        }
    }

    override suspend fun getAllSongs(userId: UUID, tab: MusicTab, page: Int, pageSize: Int): List<Song> = dbQuery {
        Songs.leftJoin(Users, { Songs.createdBy }, { Users.id })
            .leftJoin(FavouriteSongs, { Songs.id }, { FavouriteSongs.songId })
            .leftJoin(ShareSongs, { Songs.id }, { ShareSongs.songId }).select {
                when (tab) {
                    MusicTab.EXPLORE -> Songs.shareType eq ShareType.ALL_USERS
                    MusicTab.FAVOURITE -> FavouriteSongs.userId eq userId
                    MusicTab.SHARED -> ShareSongs.userId eq userId
                    MusicTab.PERSONAL -> Songs.createdBy eq userId
                }
            }
            .orderBy(Songs.createdAt)
            .drop(pageSize * (page - 1))
            .take(pageSize)
            .map { it.toSong(userId) }
    }

    override suspend fun searchSongs(
        userId: UUID,
        searchText: String,
        searchFilter: SearchFilter,
        page: Int,
        pageSize: Int
    ): List<Song> = dbQuery {
        Songs.leftJoin(Users, { Songs.createdBy }, { Users.id })
            .leftJoin(FavouriteSongs, { Songs.id }, { FavouriteSongs.songId })
            .leftJoin(ShareSongs, { Songs.id }, { ShareSongs.songId })
            .select {
                val query = when (searchFilter.searchInText) {
                    SearchFilter.SearchInText.TITLE_ARTIST -> (Songs.title like "%$searchText%") or (Songs.artist like "%$searchText%")
                    SearchFilter.SearchInText.TITLE -> Songs.title like "%$searchText%"
                    SearchFilter.SearchInText.ARTIST -> Songs.artist like "%$searchText%"
                }
                var searchInTabs: Op<Boolean> = Op.FALSE
                if (searchFilter.searchInTabs.isEmpty()) searchInTabs = Op.TRUE
                if (MusicTab.EXPLORE in searchFilter.searchInTabs)
                    searchInTabs = searchInTabs or (Songs.shareType eq ShareType.ALL_USERS)
                if (MusicTab.FAVOURITE in searchFilter.searchInTabs)
                    searchInTabs = searchInTabs or (FavouriteSongs.userId eq userId)
                if (MusicTab.SHARED in searchFilter.searchInTabs)
                    searchInTabs = searchInTabs or (ShareSongs.userId eq userId)
                if (MusicTab.PERSONAL in searchFilter.searchInTabs)
                    searchInTabs = searchInTabs or (Songs.createdBy eq userId)
                query and searchInTabs
            }
            .orderBy(Songs.createdAt)
            .drop(pageSize * (page - 1))
            .take(pageSize)
            .map { it.toSong(userId) }
    }
}