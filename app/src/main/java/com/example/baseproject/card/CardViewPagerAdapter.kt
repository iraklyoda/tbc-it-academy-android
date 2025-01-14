package com.example.baseproject.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemCardBinding

class CardDiffUtil : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem == newItem
    }

}

class CardViewPagerAdapter(
    private val onClick: (position: Int) -> Unit = {}
) :
    ListAdapter<Card, CardViewPagerAdapter.CardViewHolder>(CardDiffUtil()) {

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            name: String,
            number: String,
            expireMonth: Int,
            expireYear: Int,
            cardType: CardType
        ) {
            binding.apply {
                textCardName.text = name
                textCardNumber.text = number
                ivCardType.setImageResource(cardType.logo)
                ivCard.setImageResource(cardType.image)
                ivCard.setBackgroundResource(cardType.backGround)
                root.setOnLongClickListener {
                    onClick.invoke(adapterPosition)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ItemCardBinding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card: Card = getItem(position)
        holder.onBind(
            name = card.name,
            number = card.number.toString().chunked(4).joinToString(" "),
            expireMonth = card.expireMonth,
            expireYear = card.expireYear,
            cardType = card.cardType
        )
    }

}