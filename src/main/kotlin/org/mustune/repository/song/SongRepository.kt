package org.mustune.repository.song

import org.mustune.entities.MusicTab
import org.mustune.entities.SongEntity

interface SongRepository {
    suspend fun getAllSongs(tab: MusicTab = MusicTab.EXPLORE, page: Int = 0, pageSize: Int): List<SongEntity>
    suspend fun getSong(id: String): SongEntity?
}