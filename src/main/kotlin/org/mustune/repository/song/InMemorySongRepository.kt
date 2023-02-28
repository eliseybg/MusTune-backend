package org.mustune.repository.song

import org.mustune.entities.SongInfo

const val PAGE_SIZE = 10

class InMemorySongRepository : SongRepository {
    private val songs = generateSequence(0) { it + 1 }
        .map { SongInfo("$it", "name$it", "author$it", "link$it") }
        .take(35)
        .toList()

    override suspend fun getAllSongs(page: Int): List<SongInfo> = songs.drop(PAGE_SIZE * page).take(PAGE_SIZE)

    override suspend fun getSong(id: String): SongInfo? = songs.firstOrNull { it.id == id }
}