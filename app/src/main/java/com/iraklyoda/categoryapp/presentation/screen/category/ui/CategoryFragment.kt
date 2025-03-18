package com.iraklyoda.categoryapp.presentation.screen.category.ui

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.iraklyoda.categoryapp.databinding.FragmentCategoryBinding
import com.iraklyoda.categoryapp.presentation.BaseFragment
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryAdapter
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryEvent
import com.iraklyoda.categoryapp.presentation.screen.category.CategoryUiEvent
import com.iraklyoda.categoryapp.presentation.utils.collect
import com.iraklyoda.categoryapp.presentation.utils.collectLatest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val categoryAdapter by lazy {
        CategoryAdapter()
    }

    override fun start() {
        categoryViewModel.onEvent(CategoryEvent.FetchCategories)
        setCategoriesAdapter()
        setUpSearch()
    }

    override fun observers() {
        observeCategoryState()
        observeCategoryEvents()
    }

    private fun setCategoriesAdapter() {
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun setUpSearch() {
        binding.svCategories.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    categoryViewModel.onEvent(CategoryEvent.SearchCategories(query = newText))
                }
                return true
            }
        })
    }

    private fun observeCategoryState() {
        collectLatest(flow = categoryViewModel.state) { state ->
            manageProgressBar(loader = state.loader, searchLoader = state.searchLoader)
            categoryAdapter.submitList(state.categories)
        }
    }

    private fun observeCategoryEvents() {
        collect(flow = categoryViewModel.uiEvents) { event ->
            when(event) {
                is CategoryUiEvent.ShowErrorSnackBar -> Snackbar.make(requireView(), event.errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun manageProgressBar(loader: Boolean, searchLoader: Boolean) {
        binding.pbCategories.isVisible = loader
        binding.pbSearch.isVisible = searchLoader
    }
}
