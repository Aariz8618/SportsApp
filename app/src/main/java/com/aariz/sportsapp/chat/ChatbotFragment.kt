package com.aariz.sportsapp.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aariz.sportsapp.databinding.FragmentChatbotBinding
import kotlinx.coroutines.launch

class ChatbotFragment : Fragment() {

    private var _binding: FragmentChatbotBinding? = null
    private val binding get() = _binding!!

    private val adapter = ChatAdapter()
    private val repo by lazy { CricketChatRepository() }
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView with proper configuration
        layoutManager = LinearLayoutManager(requireContext()).apply {
            stackFromEnd = true
            reverseLayout = false
        }
        binding.rvChat.layoutManager = layoutManager
        binding.rvChat.adapter = adapter

        // Welcome message
        if (savedInstanceState == null) {
            // Post the welcome message to ensure RecyclerView is fully initialized
            binding.rvChat.post {
                addBotMessage("🏏 Welcome to CricketBot! I'm here to help you with live scores, match updates, player stats, and everything cricket. How can I assist you today?")
            }
        }

        // Send actions
        binding.btnSend.setOnClickListener { sendMessage() }
        binding.etInput.setOnEditorActionListener { _, _, _ ->
            sendMessage(); true
        }
    }

    private fun sendMessage() {
        val text = binding.etInput.text?.toString()?.trim().orEmpty()
        if (text.isBlank()) return
        binding.etInput.setText("")

        // Show user message
        addUserMessage(text)

        lifecycleScope.launch {
            val answer = repo.answer(text)
            // Show bot answer
            addBotMessage(answer)
        }
    }

    private fun quickAction(action: String) {
        // Reflect user action in chat
        addUserMessage(action)

        lifecycleScope.launch {
            val botText = when (action) {
                "Live Scores" -> repo.answer("Give current top live cricket scores succinctly")
                "Match Schedule" -> repo.answer("Upcoming international cricket matches schedule (concise)")
                "Player Stats" -> "👤 Which player's stats would you like to see? Popular: Virat Kohli, Babar Azam, Steve Smith"
                "News" -> repo.answer("Latest notable cricket news in 2-3 bullets")
                else -> repo.answer(action)
            }
            addBotMessage(botText)
        }
    }

    private fun addUserMessage(text: String) {
        adapter.submit(ChatMessage(role = ChatMessage.Role.USER, text = text))
        // Ensure smooth scroll to bottom
        binding.rvChat.post {
            binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    private fun addBotMessage(text: String) {
        adapter.submit(ChatMessage(role = ChatMessage.Role.BOT, text = text))
        // Ensure smooth scroll to bottom
        binding.rvChat.post {
            binding.rvChat.smoothScrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}