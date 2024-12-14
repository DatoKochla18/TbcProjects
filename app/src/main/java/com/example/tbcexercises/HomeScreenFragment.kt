package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentHomeScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val entryFragment = EntryFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtEntry.text =
            firebaseAuth.currentUser?.displayName.toString() + " " + context?.resources?.getString(R.string.home_screen_entry_text)
        listeners()
        return binding.root
    }


    private fun listeners() {
        binding.btnsignOut.setOnClickListener {
            Firebase.auth.signOut()
            parentFragmentManager.beginTransaction().apply {

                replace(R.id.flFragment, entryFragment)
                commit()
            }
        }
    }

}