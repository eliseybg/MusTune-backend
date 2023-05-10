package org.mustune.domain.repository

import org.mustune.domain.model.Song
import org.mustune.entities.MusicTab
import org.mustune.entities.SearchFilter
import java.util.*

interface SongsRepository {
    suspend fun getSong(userId: UUID?, songId: UUID): Song?
    suspend fun addSong(userId: UUID?, song: Song): Song?
    suspend fun editSong(userId: UUID, song: Song): Boolean
    suspend fun deleteSong(songId: UUID): Boolean
    suspend fun addSongToFavourite(userId: UUID, songId: UUID): Song?
    suspend fun removeSongFromFavourite(userId: UUID, songId: UUID): Song?
    suspend fun getSongsCategories(userId: UUID): List<MusicTab>
    suspend fun getAllSongs(userId: UUID, tab: MusicTab, page: Int, pageSize: Int): List<Song>
    suspend fun searchSongs(userId: UUID, searchText: String, searchFilter: SearchFilter, page: Int, pageSize: Int): List<Song>
}