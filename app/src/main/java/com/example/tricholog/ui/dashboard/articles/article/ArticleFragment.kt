package com.example.tricholog.ui.dashboard.articles.article


import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tricholog.BaseFragment
import com.example.tricholog.R
import com.example.tricholog.databinding.FragmentArticleBinding
import com.example.tricholog.ui.dashboard.articles.ArticlesUiState
import com.example.tricholog.ui.dashboard.articles.model.ArticleUi
import com.example.tricholog.utils.showToast
import com.example.tricholog.utils.toggle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>(FragmentArticleBinding::inflate) {

    private val args: ArticleFragmentArgs by navArgs()

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun start() {
        handleArticleStack()
        getArticle()
    }

    override fun listeners() {
        handleReturnBtn()
    }

    private fun handleArticleStack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.articlesFragment, false)
                }
            })
    }

    private fun handleReturnBtn() {
        binding.btnReturn.setOnClickListener {
            findNavController().popBackStack(R.id.articlesFragment, false)
        }
    }

    private fun getArticle() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleViewModel.getArticle(id = args.id)
                articleViewModel.articlesStateFlow.collectLatest { state ->
                    when (state) {
                        is ArticleUiState.Loading -> {
                            handleLoadingState()
                        }
                        is ArticleUiState.Success -> {
                            handleArticleSuccess(article = state.article)
                        }

                        is ArticleUiState.Error -> {
                            requireContext().showToast(state.error.toString())
                        }
                        is ArticleUiState.Idle -> {}
                    }
                }
            }
        }
    }

    private fun handleArticleSuccess(article: ArticleUi) {
        binding.apply {
            tvHeader.text = article.title
            tvContent.text = article.content
            tvAuthor.text = article.author
            tvDate.text = article.createdAt

            tvHeader.visibility = View.VISIBLE
            svArticle.visibility = View.VISIBLE
            pbArticle.toggle(false)
        }
    }

    private fun handleLoadingState() {
        binding.apply {
                pbArticle.toggle(true)
                tvHeader.visibility = View.GONE
                svArticle.visibility = View.GONE
            }
    }
}