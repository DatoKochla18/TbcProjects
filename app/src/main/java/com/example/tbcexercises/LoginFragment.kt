package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

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
        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSignIn.setOnClickListener {
            parentFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            navigateToFragment(HomeFragment())
        }

        binding.txtSignUp.setOnClickListener {
            val fragmentExits = parentFragmentManager.findFragmentByTag(REGISTER_TAG)
            if (fragmentExits != null) {
                parentFragmentManager.popBackStack(REGISTER_TAG, 0)
            } else {
                navigateToFragment(RegisterFragment(), addToBackStack = true, tag = REGISTER_TAG)
            }
        }


    }

    private fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null,
        emptyStack: Boolean = false

    ) {
        parentFragmentManager.beginTransaction().apply {


            replace(R.id.container, fragment, tag)
            if (addToBackStack) addToBackStack(tag)

            commit()
        }
    }

}