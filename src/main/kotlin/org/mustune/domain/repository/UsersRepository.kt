package org.mustune.domain.repository

import org.mustune.domain.model.User
import java.util.UUID

interface UsersRepository {
    suspend fun getUser(id: UUID): User?
    suspend fun getUserByEmailOrUsername(text: String): User?
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(username: String, email: String, password: String): User?
    suspend fun editUser(id: UUID, username: String, email: String, password: String): Boolean
    suspend fun deleteUser(id: UUID): Boolean
}