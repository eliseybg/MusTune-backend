package org.mustune.repository.song

import org.mustune.entities.SongInfo

interface SongRepository {
    suspend fun getAllSongs(page: Int = 0): List<SongInfo>
    suspend fun getSong(id: String): SongInfo?
}