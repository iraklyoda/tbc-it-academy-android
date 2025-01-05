package messanger.example.baseproject.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseproject.databinding.FragmentMainBinding

class MessengerFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val messageAdapter: MessageAdapter by lazy {
        MessageAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMessages()
        addMessage()
    }

    private fun setMessages() {
        binding.rvMessages.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = messageAdapter
        messageAdapter.submitList(messageList)
    }

    private fun addMessage() {
        binding.btnSend.setOnClickListener {
            val newMessage = Message(
                message = binding.etMessage.text.toString(),
                incoming = (messageAdapter.currentList.size + 1) % 2 == 0
            )

            val updatedMessages = listOf(newMessage) + messageAdapter.currentList
            messageList = updatedMessages.toMutableList()

            messageAdapter.submitList(updatedMessages)
            binding.etMessage.text?.clear()
            binding.rvMessages.smoothScrollToPosition(0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var messageList: MutableList<Message> = mutableListOf(
            Message(
                message = "Hello can you help me?",
                incoming = false,
                date = 1735992000000
            )
        )
    }
}