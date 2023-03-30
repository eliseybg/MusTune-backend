package org.mustune.repository.song

import org.mustune.entities.MusicTab
import org.mustune.entities.SearchFilter
import org.mustune.entities.ShareType
import org.mustune.entities.SongEntity

class InMemorySongRepository : SongRepository {
    private val songs = generateSequence(0) { it + 1 }
        .map {
            val tab = MusicTab.values().random()
            SongEntity("$it", "$tab  $it", "artist  $it", "link$it", tab = tab, false, shareType = ShareType.ALL_USERS)
        }
        .take(1000)
        .toList()
        .shuffled()

    override suspend fun getSong(id: String): SongEntity = songs.first { it.id == id }

    override suspend fun addSong(song: SongEntity) {

    }

    override suspend fun editSong(song: SongEntity) {

    }

    override suspend fun deleteSong(songId: String) {

    }

    override suspend fun getAllSongs(tab: MusicTab, page: Int, pageSize: Int): List<SongEntity> {
        return songs.filter { it.tab == tab }.drop(pageSize * (page - 1)).take(pageSize)
    }

    override suspend fun searchSongs(
        searchText: String,
        searchFilter: SearchFilter,
        page: Int,
        pageSize: Int
    ): List<SongEntity> {
        return songs
            .filter {
                when (searchFilter.searchInText) {
                    SearchFilter.SearchInText.TITLE_ARTIST -> it.title.contains(searchText, true)
                            || it.artist.contains(searchText, true)
                    SearchFilter.SearchInText.TITLE -> it.title.contains(searchText, true)
                    SearchFilter.SearchInText.ARTIST -> it.artist.contains(searchText, true)
                }
            }
            .drop(pageSize * (page - 1))
            .take(pageSize)
    }
}