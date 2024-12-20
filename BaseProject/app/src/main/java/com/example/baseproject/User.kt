package com.example.baseproject

data class User(
    val id: Int = 20,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val address: String,
    val email: String,
    val desc: String? = ""
) {

    init {
        currentId++
    }

    companion object {
        var currentId: Int = 33
        val users: MutableList<User> = mutableListOf<User>(
            User(
                id = 1,
                firstName = "გრიშა",
                lastName = "ონიანი",
                birthday = "1724647601641",
                address = "სტალინის სახლმუზეუმი",
                email = "grisha@mail.ru"
            ),
            User(
                id = 2,
                firstName = "Jemal",
                lastName = "Kakauridze",
                birthday = "1714647601641",
                address = "თბილისი, ლილოს მიტოვებული ქარხანა",
                email = "jemal@gmail.com"
            ),
            User(
                id = 2,
                firstName = "Omger",
                lastName = "Kakauridze",
                birthday = "1724647701641",
                address = "თბილისი, ასათიანი 18",
                email = "omger@gmail.com"
            ),
            User(
                id = 32,
                firstName = "ბორის",
                lastName = "გარუჩავა",
                birthday = "1714947701641",
                address = "თბილისი, იაშვილი 14",
                email = ""
            ),
            User(
                id = 34,
                firstName = "აბთო",
                lastName = "სიხარულიძე",
                birthday = "1711947701641",
                address = "ფოთი",
                email = "tebzi@gmail.com",
                desc = null
            )
        )

        fun findUser(input: String): User? {
            val index: Int = users.indexOfFirst { it ->
                setOf(
                    it.id.toString(),
                    it.firstName,
                    it.lastName,
                    it.birthday,
                    it.address,
                    it.email,
                    it.desc
                ).any() { it.equals(input, ignoreCase = true) }
            }

            return if (index != -1) {
                users[index]
            } else {
                null
            }
        }
    }
}