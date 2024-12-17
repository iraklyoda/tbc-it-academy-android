package com.example.userapp

import android.content.Context
import android.widget.Toast

data class User(val firstName: String, val lastName: String, val age: Int, val email: String) {
    companion object {
        private val usersList: MutableList<User> = mutableListOf<User>()

        var activeUsers: Int = 0
        var deletedUsers: Int = 0

        fun addUser(context: Context, user: User): Boolean {
            val userExists: Boolean = usersList.any { it.email == user.email }
            if (!userExists) {
                usersList.add(user)
                activeUsers = usersList.size
                Toast.makeText(
                    context,
                    context.getString(R.string.user_added_successfully), Toast.LENGTH_SHORT
                ).show()
                return true
            }
            Toast.makeText(
                context,
                context.getString(R.string.user_already_exists), Toast.LENGTH_SHORT
            ).show()
            return false
        }

        fun updateUser(context: Context, user: User): Boolean {
            val index = usersList.indexOfFirst { it.email == user.email }

            // If the user is the same, don't update
            if (usersList.any { it == user }) {
                Toast.makeText(
                    context,
                    context.getString(R.string.user_is_up_to_date), Toast.LENGTH_SHORT
                ).show()
                return false
            }

            if (index != -1) {
                usersList[index] = user
                Toast.makeText(
                    context,
                    context.getString(R.string.user_updated_successfully), Toast.LENGTH_SHORT
                ).show()
                return true
            }

            Toast.makeText(
                context,
                context.getString(R.string.user_doesn_t_exist),
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        }

        fun removeUser(context: Context, email: String): Boolean {
            val index = usersList.indexOfFirst { it.email == email }
            if (index != -1) {
                usersList.removeAt(index)
                deletedUsers++
                activeUsers = usersList.size

                Toast.makeText(
                    context,
                    context.getString(R.string.user_deleted_successfully),
                    Toast.LENGTH_SHORT
                )
                    .show()
                return true
            }

            Toast.makeText(
                context,
                context.getString(R.string.user_doesn_t_exist),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }
}