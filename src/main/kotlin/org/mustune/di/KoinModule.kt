package org.mustune.di

import org.koin.dsl.module
import org.mustune.repository.song.InMemorySongRepository
import org.mustune.repository.song.SongRepository

val koinModule = module {
    single<SongRepository> { InMemorySongRepository() }
}