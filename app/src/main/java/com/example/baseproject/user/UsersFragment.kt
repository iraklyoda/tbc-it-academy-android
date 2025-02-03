package com.example.baseproject.user

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.BaseFragment
import com.example.baseproject.R
import com.example.baseproject.client.RetrofitClient
import com.example.baseproject.data.AppDatabase
import com.example.baseproject.data.DataRepository
import com.example.baseproject.databinding.FragmentUsersBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter()
    }
    private val usersViewModel: UserViewModel by viewModels() {
        UserViewModel.Factory(
            dataRepository = DataRepository(
                RetrofitClient.userService,
                AppDatabase.getDatabase(requireContext().applicationContext).userDao()
            )
        )
    }

    private fun isWifiConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.run {
            val networkCapabilities = activeNetwork?.let { getNetworkCapabilities(it) }
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
        } ?: false
    }

    override fun start() {
        setUsers()

        if(isWifiConnected(requireContext())) {
            usersViewModel.refreshUsers()
            binding.tvStatus.text = getString(R.string.you_are_online)
            observeUsers()
        } else {
            observeCachedUsers()
            binding.tvStatus.text = getString(R.string.you_are_offline)
        }
    }

    private fun setUsers() {
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsers.adapter = usersAdapter
    }

    private fun observeCachedUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.users.collectLatest { users ->
                usersAdapter.submitList(users)
            }
        }
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            usersViewModel.usersState.collect { uiState ->
                when (uiState) {
                    is UIState.Loading -> {
                        binding.pb.visibility = View.VISIBLE
                        binding.rvUsers.visibility = View.GONE
                    }

                    is UIState.Success -> {
                        binding.pb.visibility = View.GONE
                        binding.rvUsers.visibility = View.VISIBLE
                        uiState.users.collectLatest { users ->
                            usersAdapter.submitList(users)
                        }
                    }

                    is UIState.Error -> {
                        binding.pb.visibility = View.GONE
                        binding.rvUsers.visibility = View.GONE
                        Toast.makeText(context, "Error: ${uiState.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else -> {

                    }
                }
            }
        }
    }

}