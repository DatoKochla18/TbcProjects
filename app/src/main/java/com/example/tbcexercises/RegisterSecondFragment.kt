package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tbcexercises.databinding.FragmentRegisterSecondBinding

class RegisterSecondFragment : Fragment() {
    private lateinit var binding: FragmentRegisterSecondBinding

    private  val termsOfServiceFragment = TermsOfServiceFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterSecondBinding.inflate(layoutInflater, container, false)
        listeners()
        return binding.root
    }


    private fun listeners(){
        binding.txtTermsOfService.setOnClickListener{
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment,termsOfServiceFragment)
                addToBackStack(null)
                commit()
            }
        }

        binding.btnArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()

        }
    }
}