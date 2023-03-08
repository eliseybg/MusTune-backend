package org.mustune.repository.song

import org.mustune.entities.MusicTab
import org.mustune.entities.SongEntity

class InMemorySongRepository : SongRepository {
    private val songs = generateSequence(0) { it + 1 }
        .map {
            val tab = MusicTab.values().random()
            SongEntity("$it", "$tab  $it", "artist  $it", "link$it", tab = tab)
        }
        .take(1000)
        .toList()
        .shuffled()

    override suspend fun getAllSongs(tab: MusicTab, page: Int, pageSize: Int): List<SongEntity> {
        return songs.filter { it.tab == tab }.drop(pageSize * (page - 1)).take(pageSize)
    }

    override suspend fun searchSongs(searchText: String, page: Int, pageSize: Int): List<SongEntity> {
        return songs
            .filter { it.title.contains(searchText, true) || it.artist.contains(searchText, true) }
            .drop(pageSize * (page - 1))
            .take(pageSize)
    }

    override suspend fun getSong(id: String): SongEntity? = songs.firstOrNull { it.id == id }
}