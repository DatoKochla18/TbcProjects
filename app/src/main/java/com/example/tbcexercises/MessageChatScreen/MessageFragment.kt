package com.example.tbcexercises.MessageChatScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.Models.Message
import com.example.tbcexercises.databinding.FragmentMessageBinding


class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    private val messageData = mutableListOf<Message>(
    )
    private val messageAdapter by lazy {
        MessageAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMessageAdapter()
        listeners()

    }

    private fun listeners() {
        binding.btnSend.setOnClickListener {
            addMessage()
        }
    }

    private fun addMessage() {
        val text = binding.etEnterMessage.text.toString()

        if (text.isNotEmpty()) {

            val message = Message(text = text)

            messageData.add(message)
            messageAdapter.submitList(messageData.toList())

            binding.etEnterMessage.text?.clear()
        }

    }

    private fun setUpMessageAdapter() {
        binding.rvMessages.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                reverseLayout = true
                stackFromEnd = true
            }
            adapter = messageAdapter
        }
        messageAdapter.submitList(messageData.toList())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}