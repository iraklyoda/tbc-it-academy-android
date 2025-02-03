package com.example.baseproject.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
enum class ActivationStatus(val code: Int) {
    INACTIVE(-1),
    ONLINE(1),
    RECENT(2),
    TODAY(3),
    GONE(4);

    companion object {
        fun fromCode(code: Double): ActivationStatus {
            return when {
                code < 0 -> INACTIVE
                code == 1.0 -> ONLINE
                code == 2.0 -> RECENT
                code in 3.00..22.00 -> TODAY
                else -> GONE
            }
        }
    }
}

@Serializable
data class UserDto(
    val id: Int,
    val avatar: String? = null,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    val about: String,
    @SerialName("activation_status")
    val activationStatus: Double
)

@Serializable
data class UserResponse(
    val users: List<UserDto>
)