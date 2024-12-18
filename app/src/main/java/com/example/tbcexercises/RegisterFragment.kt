package com.example.tbcexercises

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
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

        binding.btnSignUp.setOnClickListener {
            parentFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            navigateToFragment(HomeFragment())
        }

        binding.txtSignIn.setOnClickListener {
            val fragmentExits = parentFragmentManager.findFragmentByTag(LOGIN_TAG)
            Log.d("Fragment exits", (fragmentExits == null).toString())
            if (fragmentExits != null) {
                parentFragmentManager.popBackStack(LOGIN_TAG, 0)
            } else {
                navigateToFragment(LoginFragment(), addToBackStack = true, tag = LOGIN_TAG)
            }
        }


    }

    private fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null
    ) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment, tag)
            if (addToBackStack) addToBackStack(tag)

            commit()
        }
    }

}