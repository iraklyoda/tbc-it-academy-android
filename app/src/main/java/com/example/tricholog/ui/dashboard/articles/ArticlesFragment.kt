package com.example.tricholog.ui.dashboard.articles

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentArticlesBinding
import com.example.tricholog.utils.showToast
import com.example.tricholog.utils.toggle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val articlesViewModel: ArticlesViewModel by viewModels()

    private val articlesAdapter by lazy {
        ArticlesAdapter { id ->
            onReadClick(id)
        }
    }

    override fun start() {
        setArticlesAdapter()
        observeArticles()
    }

    override fun listeners() {
    }

    private fun setArticlesAdapter() {
        binding.rvArticles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvArticles.adapter = articlesAdapter
    }

    private fun onReadClick(id: String) {
        val action = ArticlesFragmentDirections.actionArticlesFragmentToArticleFragment(id)
        findNavController().navigate(action)
    }

    private fun observeArticles() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                articlesViewModel.articlesStateFlow.collectLatest { state ->
                    when (state) {
                        is ArticlesUiState.Loading -> {
                            binding.pbArticles.toggle(true)
                        }

                        is ArticlesUiState.Success -> {
                            binding.pbArticles.toggle(false)
                            articlesAdapter.submitList(state.articles)
                        }

                        is ArticlesUiState.Error -> {
                            binding.pbArticles.toggle(false)
                            requireContext().showToast("${state.error}")
                        }

                        is ArticlesUiState.Idle -> {
                            binding.pbArticles.toggle(false)
                        }
                    }
                }
            }
        }
    }
}