package com.example.baseproject.user

import com.example.baseproject.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class ActivationStatus(val statusColor: Int) {
    INACTIVE(R.color.casual_red),
    ONLINE(R.color.bud_green),
    RECENT(R.color.simple_gray),
    TODAY(R.color.glacier_gray),
    GONE(R.color.casual_red);
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
) {
    val status: ActivationStatus
        get() = when  {
            activationStatus  <= 0.0 -> ActivationStatus.INACTIVE
            activationStatus == 1.0 -> ActivationStatus.ONLINE
            activationStatus == 2.0 -> ActivationStatus.RECENT
            activationStatus > 2.0 && activationStatus < 23 -> ActivationStatus.TODAY
            else -> ActivationStatus.GONE
        }
}

@Serializable
data class UserResponse(
    val users: List<UserDto>
)