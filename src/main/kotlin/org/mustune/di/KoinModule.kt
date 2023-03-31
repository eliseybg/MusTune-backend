package org.mustune.di

import org.koin.dsl.module
import org.mustune.domain.data.files.FilesRepositoryImpl
import org.mustune.domain.data.songs.SongsRepositoryImpl
import org.mustune.domain.data.users.UsersRepositoryImpl
import org.mustune.domain.repository.FilesRepository
import org.mustune.domain.repository.SongsRepository
import org.mustune.domain.repository.UsersRepository

val koinModule = module {
    single<SongsRepository> { SongsRepositoryImpl() }
    single<UsersRepository> { UsersRepositoryImpl() }
    single<FilesRepository> { FilesRepositoryImpl() }
}