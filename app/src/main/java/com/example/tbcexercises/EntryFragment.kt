package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {

    private var _binding: FragmentEntryBinding? = null

    private val binding get() = _binding!!
    private val loginFragment = LoginFragment()
    private val registerFragment = RegisterFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentEntryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, loginFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnRegister.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, registerFragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}
