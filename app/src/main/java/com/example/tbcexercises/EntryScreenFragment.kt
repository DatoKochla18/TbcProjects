package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.tbcexercises.databinding.FragmentEntryScreenBinding


class EntryScreenFragment : Fragment() {

    private var _binding: FragmentEntryScreenBinding? = null
    private val binding get() = _binding!!

    private val validDimensions = listOf(3, 4, 5)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartGame.setOnClickListener { startGame() }
    }

    private fun startGame() {
        val width = binding.etWidth.text.toString()
        val height = binding.etHeight.text.toString()

        val widthChecked = width.toIntOrNull()
        val heightChecked = height.toIntOrNull()

        var isValid = true

        if (widthChecked == null || heightChecked == null) {
            showToast(getString(R.string.only_number_are_allowed))
            isValid = false
        } else if (widthChecked !in validDimensions || heightChecked !in validDimensions) {
            showToast(getString(R.string.only_dimensions_3_4_or_5_are_allowed))
            isValid = false
        } else if (widthChecked != heightChecked) {
            showToast(getString(R.string.width_and_height_must_be_equal))
            isValid = false
        }

        if (isValid) {
            navigateToGameScreen(widthChecked!!, heightChecked!!)
        }
    }

    private fun navigateToGameScreen(width: Int, height: Int) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main, GameScreenFragment.newInstance(width, height))
            addToBackStack(null)
            commit()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
