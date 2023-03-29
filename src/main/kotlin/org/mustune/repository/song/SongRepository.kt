package org.mustune.repository.song

import org.mustune.entities.MusicTab
import org.mustune.entities.SearchFilter
import org.mustune.entities.SongEntity

interface SongRepository {
    suspend fun getAllSongs(tab: MusicTab, page: Int, pageSize: Int): List<SongEntity>
    suspend fun searchSongs(searchText: String, searchFilter: SearchFilter, page: Int, pageSize: Int): List<SongEntity>
    suspend fun getSong(id: String): SongEntity?
}