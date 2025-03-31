package com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.iraklyoda.transferapp.databinding.ItemAccountBinding
import com.iraklyoda.transferapp.presentation.extensions.loadImage
import com.iraklyoda.transferapp.presentation.screen.transfer_internally.bottom_sheet.from.model.AccountUi

class FromAccountAdapter(
    private val onAccountClick: (account: AccountUi) -> Unit
): ListAdapter<AccountUi, FromAccountAdapter.FromAccountViewHolder>(FromAccountDiffUtil()) {

    inner class FromAccountViewHolder(private val binding: ItemAccountBinding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(account: AccountUi) {
                    binding.apply {
                        ivFromCard.loadImage(source = account.cardLogo)
                        tvFromName.text = account.accountName
                        tvFromAmount.text = account.balance.toString()
                        tvFromCurrencySymbol.text = account.valueType.getSymbol()
                        tvFromDigits.text = account.accountNumber.takeLast(4)

                        btnFrom.setOnClickListener {
                            onAccountClick(account)
                        }
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FromAccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FromAccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FromAccountViewHolder, position: Int) {
        holder.bind(account = getItem(position))
    }

    class FromAccountDiffUtil: DiffUtil.ItemCallback<AccountUi>() {
        override fun areItemsTheSame(oldItem: AccountUi, newItem: AccountUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AccountUi, newItem: AccountUi): Boolean {
            return oldItem == newItem
        }

    }
}