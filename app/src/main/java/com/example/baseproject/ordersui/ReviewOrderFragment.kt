package com.example.baseproject.ordersui

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentReviewOrderBinding
import com.example.baseproject.orders.Order
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewOrderFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentReviewOrderBinding? = null
    private val binding: FragmentReviewOrderBinding get() = _binding!!
    private var order: Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable("order_key")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReviewOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOrderData()
        closeReviewSheet()
    }

    private fun setOrderData() {
        order?.let {
            val itemColor: Int = ContextCompat.getColor(requireContext(), it.color)

            binding.itemLayout.apply {
                layoutItem.setBackgroundResource(R.drawable.order_review_bg)
                btnAction.visibility = View.GONE
                ivOrder.setImageResource(it.image)
                txtOrderName.text = it.name
                txtColor.text = it.colorName
                ivColor.background.setTint(itemColor)
                txtQtyAmount.text = it.quantity.toString()
                txtPrice.text = DecimalFormat("#.00").format(it.price)
            }
        }
    }

    private fun closeReviewSheet() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}