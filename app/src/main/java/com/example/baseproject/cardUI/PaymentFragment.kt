package com.example.baseproject.cardUI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.baseproject.BaseFragment
import com.example.baseproject.card.CardViewModel
import com.example.baseproject.card.CardViewPagerAdapter
import com.example.baseproject.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {
    private val viewModel: CardViewModel by activityViewModels()

    private lateinit var adapter: CardViewPagerAdapter
    private var currentPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }

    private fun setup(view: View) {
        setCards(view)
        setAddCard(view)
        observeCardList()
        checkDeleteCard()
    }

    private fun setCards(view: View) {
        adapter = CardViewPagerAdapter() { position: Int ->
            currentPosition = position
            showDeleteCardDialog(view)
        }

        adapter.submitList(viewModel.cards.value)
        binding.vp2Cards.adapter = adapter
    }

    private fun setAddCard(view: View) {
        binding.btnAddNew.setOnClickListener {
            val action = PaymentFragmentDirections.actionFirstFragmentToCreateCardFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun showDeleteCardDialog(view: View) {
        val action =
            PaymentFragmentDirections.actionFirstFragmentToDeleteBottomSheetDialogFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun checkDeleteCard() {
        setFragmentResultListener("deleteRequest") { _, bundle ->
            val result = bundle.getBoolean("deleteConfirmed", false)
            if (result) {
                viewModel.deleteCard(currentPosition)
            }
        }
    }

    private fun observeCardList() {
        viewModel.cards.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)

            Handler(Looper.getMainLooper()).postDelayed({
                binding.vp2Cards.setCurrentItem(0, true)
            }, 100)
        })
    }
}