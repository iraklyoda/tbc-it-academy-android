package com.example.baseproject.data

import com.example.baseproject.client.UserService
import kotlinx.coroutines.flow.Flow

class DataRepository(
    private val apiService: UserService,
    private val userDao: UserDao,
) {
    suspend fun fetchUsers() {
        val response = apiService.getUsersData()

        val users: MutableList<UserEntity> = mutableListOf()

        response.users.forEach { userDto ->
            val userEntity = UserEntity(
                id = userDto.id,
                avatar = userDto.avatar,
                firstName = userDto.firstName,
                lastName = userDto.lastName,
                about = userDto.about,
                status = userDto.status
            )
            users.add(userEntity)
        }
        userDao.insertAll(users)
    }

    fun getUsers(): Flow<List<UserEntity>> {
        return userDao.getAll()
    }
}
