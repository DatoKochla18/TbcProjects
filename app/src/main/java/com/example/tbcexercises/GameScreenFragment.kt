package com.example.tbcexercises

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tbcexercises.databinding.FragmentGameScreenBinding


class GameScreenFragment : Fragment() {


    private var width: Int? = null
    private val widthNotNull get() = width!!
    private var height: Int? = null
    private val heightNotNull get() = width!!

    private var remainingMoves: Int? = null

    private var _binding: FragmentGameScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var data: MutableList<ButtonData>

    private var player: Boolean = true

    private val gameAdapter by lazy {
        GameAdapter(onClick = {
            onButtonClicked(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            width = it.getInt(WIDTH)
            height = it.getInt(HEIGHT)


        }
        remainingMoves = widthNotNull * heightNotNull
        data = generateData(widthNotNull, heightNotNull)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvContainer.adapter = gameAdapter
        binding.rvContainer.layoutManager = GridLayoutManager(requireContext(), heightNotNull)
        gameAdapter.submitList(data.toList())
    }

    companion object {
        private const val WIDTH = "WIDTH"
        private const val HEIGHT = "HEIGHT"
        private const val PLAYER_X = "PLAYER X"
        private const val PLAYER_O = "PLAYER O"

        fun newInstance(width: Int, height: Int) =
            GameScreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(WIDTH, width)
                    putInt(HEIGHT, height)
                }
            }
    }

    private fun onButtonClicked(buttonData: ButtonData) {
        val idx = data.indexOfFirst { it.id == buttonData.id }
        if (data[idx].isClicked == null) {
            val img = if (player) R.drawable.card_o else R.drawable.card_x
            data[idx] = buttonData.copy(isClicked = true, img = img)
            player = player.not()
            gameAdapter.submitList(data.toList())

            remainingMoves = remainingMoves?.minus(1)
        }


        if (remainingMoves == 0) {
            Toast.makeText(requireContext(), getString(R.string.game_over_draw), Toast.LENGTH_SHORT)
                .show()
            parentFragmentManager.popBackStack()
        }

        gameOver(data)
    }

    private fun generateData(width: Int, height: Int): MutableList<ButtonData> {
        val data = mutableListOf<ButtonData>()
        for (i in 0..<(width * height)) {
            data.add(ButtonData(i))
        }
        return data
    }

    private fun gameOver(data: MutableList<ButtonData>) {
        val grid = Array(widthNotNull) { row ->
            data.subList(row * widthNotNull, (row + 1) * widthNotNull)
        }
        val diagonalRange = 0 until widthNotNull


        for (row in grid) {
            if (row.all { it.img == R.drawable.card_o }) winner(PLAYER_O)
            if (row.all { it.img == R.drawable.card_x }) winner(PLAYER_X)
        }

        for (col in 0 until widthNotNull) {
            if (grid.all { it[col].img == R.drawable.card_o }) winner(PLAYER_O)
            if (grid.all { it[col].img == R.drawable.card_x }) winner(PLAYER_X)
        }
        //this is the diagonal (0,0) (1,1) so on
        if (diagonalRange.all { grid[it][it].img == R.drawable.card_o }) {
            winner(PLAYER_O)
        }

        if (diagonalRange.all { grid[it][it].img == R.drawable.card_x }) {
            winner(PLAYER_X)
        }
        //this is diagonal (0,2) (1,1) so on
        if (diagonalRange.all { grid[it][widthNotNull - 1 - it].img == R.drawable.card_o }) {
            winner(PLAYER_O)
        }
        if (diagonalRange.all { grid[it][widthNotNull- 1 - it].img == R.drawable.card_x }) {
            winner(PLAYER_X)
        }

    }

    private fun winner(player: String) {
        Toast.makeText(requireContext(), "$player Wins!", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}