package com.example.tricholog.ui.dashboard.logs.display

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tricholog.databinding.ItemLogBinding
import com.example.tricholog.ui.dashboard.logs.model.TrichoLogUi

class LogsDiffUtil : DiffUtil.ItemCallback<TrichoLogUi>() {
    override fun areItemsTheSame(oldItem: TrichoLogUi, newItem: TrichoLogUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrichoLogUi, newItem: TrichoLogUi): Boolean {
        return oldItem == newItem
    }

}

class LogsAdapter : ListAdapter<TrichoLogUi, LogsAdapter.LogViewHolder>(LogsDiffUtil()) {

    inner class LogViewHolder(private val binding: ItemLogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(trichoLog: TrichoLogUi) {
            binding.apply {
                tvTrigger.text = trichoLog.trigger
                tvBody.text = trichoLog.body
                tvDate.text = trichoLog.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding = ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}