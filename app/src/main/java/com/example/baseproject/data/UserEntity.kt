package com.example.baseproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.baseproject.user.ActivationStatus

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val avatar: String?,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    val about: String?,
    val status: ActivationStatus
)