package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchFilter(
    val searchInTabs: List<MusicTab> = emptyList(),
    val searchInText: SearchInText = SearchInText.TITLE_ARTIST
) {
    enum class SearchInText { TITLE_ARTIST, TITLE, ARTIST }
}