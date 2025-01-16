package com.example.baseproject.field

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemFieldCardBinding

class FieldCardAdapter(
    private val fragmentManager: FragmentManager,
    private val cards: List<List<FieldDto>>
) : RecyclerView.Adapter<FieldCardAdapter.FieldCardViewHolder>() {

    inner class FieldCardViewHolder(private val binding: ItemFieldCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val adapter = FieldAdapter(fragmentManager, cards[adapterPosition])
            binding.rvFields.layoutManager = LinearLayoutManager(itemView.context)
            binding.rvFields.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldCardViewHolder {
        val binding =
            ItemFieldCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FieldCardViewHolder(binding)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder: FieldCardViewHolder, position: Int) {
        holder.onBind()
    }
}