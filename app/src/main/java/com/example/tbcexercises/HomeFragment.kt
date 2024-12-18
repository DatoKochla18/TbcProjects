package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()
        binding.txtWelcome.text =
            resources.getString(R.string.welcome_back) + " " + firebaseAuth.currentUser?.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listeners() {
        binding.btnSignOut.setOnClickListener {
            firebaseAuth.signOut()
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            navigateToFragment(EntryFragment())
        }

    }

    private fun navigateToFragment(
        fragment: Fragment,
        addToBackStack: Boolean = false,
        tag: String? = null,

        ) {
        parentFragmentManager.beginTransaction().apply {

            replace(R.id.container, fragment, tag)
            if (addToBackStack) addToBackStack(tag)

            commit()
        }
    }

}