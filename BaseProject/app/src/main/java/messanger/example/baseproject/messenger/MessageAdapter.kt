package messanger.example.baseproject.messenger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.databinding.ItemMessageIncomingBinding
import com.example.baseproject.databinding.ItemMessageOutgoingBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    companion object {
        const val OUTGOING_MESSAGE_TYPE = 1
        const val INCOMING_MESSAGE_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == OUTGOING_MESSAGE_TYPE)
            return OutgoingMessageViewHolder(
                ItemMessageOutgoingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
        else
            return IncomingMessageViewHolder(
                ItemMessageIncomingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OutgoingMessageViewHolder)
            holder.onBind()
        else if (holder is IncomingMessageViewHolder)
            holder.onBind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).incoming) {
            INCOMING_MESSAGE_TYPE
        } else {
            OUTGOING_MESSAGE_TYPE
        }
    }

    inner class OutgoingMessageViewHolder(
        private val binding: ItemMessageOutgoingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val itemMessage = getItem(adapterPosition)
            binding.tvMessage.text = itemMessage.message
            binding.tvDate.text = formatDate(itemMessage.date)
        }
    }

    inner class IncomingMessageViewHolder(
        private val binding: ItemMessageIncomingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val itemMessage = getItem(adapterPosition)
            binding.tvMessage.text = itemMessage.message
            binding.tvDate.text = formatDate(itemMessage.date)
        }
    }

    class MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    private fun formatDate(dateMillis: Long): String {
        val date = Date(dateMillis)

        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())

        val todayDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val isToday: Boolean = todayDateFormat.format(Date()) == todayDateFormat.format(date)

        val output: String = if (isToday) {
            "Today, ${(timeFormat.format(date))}"
        } else {
            "${dayFormat.format(date)}, ${timeFormat.format(date)}"
        }

        return output
    }
}