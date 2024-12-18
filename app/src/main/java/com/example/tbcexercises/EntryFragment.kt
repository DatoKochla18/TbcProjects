package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentEntryBinding

class EntryFragment : Fragment() {
    private var _binding: FragmentEntryBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEntryBinding.inflate(layoutInflater, container, false)
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
        binding.btnSignIn.setOnClickListener {
            navigateToFragment(LoginFragment(), addToBackStack = true, LOGIN_TAG)
        }

        binding.txtSignUp.setOnClickListener {
            navigateToFragment(RegisterFragment(), addToBackStack = true, REGISTER_TAG)
        }
    }

    private fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment,tag)
            if (addToBackStack) addToBackStack(tag)

            commit()
        }
    }

}