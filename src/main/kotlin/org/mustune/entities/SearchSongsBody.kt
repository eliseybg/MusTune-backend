package org.mustune.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchSongsBody(
    val searchText: String,
    val searchFilter: SearchFilter,
    val page: Int,
    val pageSize: Int
)