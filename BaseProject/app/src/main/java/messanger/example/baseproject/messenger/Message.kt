package messanger.example.baseproject.messenger


import java.util.UUID

data class Message(
    val id: UUID = UUID.randomUUID(),
    val message: String,
    val incoming: Boolean,
    val date: Long = System.currentTimeMillis(),
)