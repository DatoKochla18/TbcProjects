package com.example.tbcexercises.profileScreen

import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.databinding.FragmentProfileBinding
import com.example.tbcexercises.util.BaseFragment


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val args: ProfileFragmentArgs by navArgs()

    override fun start() {
        binding.txtProfile.text = args.profile
    }

    override fun listeners() {}

}