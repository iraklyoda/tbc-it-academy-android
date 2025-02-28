package com.example.tricholog.ui.dashboard.articles

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.tricholog.BaseFragment
import com.example.tricholog.databinding.FragmentArticlesBinding
import com.example.tricholog.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<FragmentArticlesBinding>(FragmentArticlesBinding::inflate) {

    private val articlesViewModel: ArticlesViewModel by viewModels()

    override fun start() {
        observeArticles()
    }

    override fun listeners() {
    }

    private fun observeArticles() {
        viewLifecycleOwner.lifecycleScope.launch {
            articlesViewModel.articlesStateFlow.collectLatest { state ->
                when(state) {
                    is ArticlesUiState.Loading -> {
                        requireContext().showToast("Loading")
                        binding.tvHeader.text = "Loading"
                    }
                    is ArticlesUiState.Success -> {
                        requireContext().showToast("${state.articles}")
                        binding.tvHeader.text = "${state.articles}"
                    }
                    is ArticlesUiState.Error -> {
                        requireContext().showToast("${state.error}")
                        binding.tvHeader.text = "${state.error}"
                    }
                    is ArticlesUiState.Idle -> {}
                }
            }
        }
    }
}