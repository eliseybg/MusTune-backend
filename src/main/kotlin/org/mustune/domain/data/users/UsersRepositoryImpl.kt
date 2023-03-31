package org.mustune.domain.data.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.mustune.domain.data.dbQuery
import org.mustune.domain.mapper.toUser
import org.mustune.domain.model.User
import org.mustune.domain.repository.UsersRepository
import org.mustune.util.extentions.toLocalDateTime
import java.util.*

class UsersRepositoryImpl : UsersRepository {
    override suspend fun getUser(id: UUID): User? = dbQuery {
        Users.select { Users.id eq id }.map(ResultRow::toUser).singleOrNull()
    }

    override suspend fun getUserByEmailOrUsername(text: String): User? = dbQuery {
        Users.select { (Users.email eq text) or (Users.username eq text) }.map(ResultRow::toUser).singleOrNull()
    }

    override suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map(ResultRow::toUser)
    }

    override suspend fun addUser(username: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.password] = password
            it[createdAt] = Calendar.getInstance().time.toLocalDateTime()
        }
        insertStatement.resultedValues?.singleOrNull()?.let(ResultRow::toUser)
    }

    override suspend fun editUser(id: UUID, username: String, email: String, password: String): Boolean = dbQuery {
        Users.update({ Users.id eq id }) {
            it[Users.username] = username
            it[Users.email] = email
            it[Users.password] = password
        } > 0
    }

    override suspend fun deleteUser(id: UUID): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}