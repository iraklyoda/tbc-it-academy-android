package com.example.baseproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemGameButtonBinding


class GameButtonDiffUtil : DiffUtil.ItemCallback<GameButton>() {
    override fun areItemsTheSame(oldItem: GameButton, newItem: GameButton): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameButton, newItem: GameButton): Boolean {
        return oldItem == newItem
    }
}

class GameAdapter(): ListAdapter<GameButton, GameAdapter.GameViewHolder>(GameButtonDiffUtil()) {


    var itemClickListener: ((position: Int) -> Unit)? = null

    inner class GameViewHolder(private val binding: ItemGameButtonBinding): RecyclerView.ViewHolder(binding.root)
    {
        var itemClickListener: ((position: Int) -> Unit)? = null

        fun onBind(state: CellState) {
            binding.ibCell.setOnClickListener {
                itemClickListener?.invoke(adapterPosition)
            }

            when (state) {
                CellState.X -> binding.ibCell.setImageResource(R.drawable.x)
                CellState.O -> binding.ibCell.setImageResource(R.drawable.o)
                else -> binding.ibCell.setImageResource(0)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding: ItemGameButtonBinding = ItemGameButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener

        holder.onBind(
            state = getItem(position).state
        )
    }
}