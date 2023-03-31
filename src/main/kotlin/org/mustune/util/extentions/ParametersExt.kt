package org.mustune.util.extentions

import io.ktor.http.*
import io.ktor.server.plugins.*
import org.mustune.entities.MusicTab
import org.mustune.util.Constants
import java.util.UUID
import kotlin.math.min

val Parameters.page: Int
    get() {
        val page = this["page"]?.toInt() ?: Constants.INITIAL_PAGE
        require(page >= Constants.INITIAL_PAGE) { "Page should be >= ${Constants.INITIAL_PAGE}" }
        return page
    }

val Parameters.pageSize: Int
    get() {
        val pageSize = min(this["pageSize"]?.toInt() ?: Constants.DEFAULT_PAGE_SIZE, Constants.MAX_PAGE_SIZE)
        require(pageSize >= 0) { "Page size should be >= 0" }
        return pageSize
    }

val Parameters.tab: MusicTab
    get() {
        val tab = this["tab"] ?: return MusicTab.EXPLORE
        require(MusicTab.values().any { it.name == tab }) { "Tab should be one of " + MusicTab.values().joinToString() }
        return MusicTab.valueOf(tab)
    }

val Parameters.songId: UUID
    get() {
        return UUID.fromString(this["songId"] ?: throw BadRequestException("no argument songId"))
    }