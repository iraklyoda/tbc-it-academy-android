package com.example.baseproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.baseproject.databinding.FragmentGameBinding
import com.example.baseproject.databinding.FragmentMainBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding get() = _binding!!
    private var gridSize: Int = 3
    var gameIsActive: Boolean = true


    private val gameAdapter: GameAdapter by lazy {
        GameAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gridSize = arguments?.getInt("configId") ?: 3
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGame()
        gameLogic()
        playAgain()
    }

    private fun setGame() {

        for (i in 1..gridSize * gridSize) {
            buttonList.add(GameButton())
        }
        binding.rvTictactoe.layoutManager = GridLayoutManager(requireContext(), gridSize)
        binding.rvTictactoe.adapter = gameAdapter
        gameAdapter.submitList(buttonList.toList())
    }

    private fun playAgain() {
        binding.btnPlayAgain.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun gameLogic() {
        var activePlayer: Int = 1

        gameAdapter.itemClickListener = { position: Int ->
            if (gameIsActive) {

                if (buttonList[position].state == CellState.EMPTY) {
                    if (activePlayer == 1) {
                        buttonList[position] = buttonList[position].copy(state = CellState.X)
                        activePlayer = 2
                        binding.textHeader.text = getString(R.string.player_2)
                    } else {
                        buttonList[position] = buttonList[position].copy(state = CellState.O)
                        activePlayer = 1
                        binding.textHeader.text = getString(R.string.player_1)
                    }
                }
                
                gameAdapter.submitList(buttonList.toList())

                val winner: CellState? = checkWin(buttonList, gridSize)

                if (checkDraw(buttonList)) {
                    binding.textHeader.text = getString(R.string.draw)
                    finishGame()
                }

                if (winner != null) {
                    binding.textHeader.text = getString(R.string.wins, winner.status)
                    finishGame()
                }
            }
        }
    }

    private fun checkWin(buttonList: List<GameButton>, gridSize: Int): CellState? {
        // Check rows for filled markers
        for (row in 0 until gridSize) {
            val firstCellState = buttonList[row * gridSize].state
            if (firstCellState != CellState.EMPTY &&
                (0 until gridSize).all { buttonList[row * gridSize + it].state == firstCellState }
            ) {
                return firstCellState
            }
        }

        // Check columns for filled markers
        for (col in 0 until gridSize) {
            val firstCellState = buttonList[col].state
            if (firstCellState != CellState.EMPTY &&
                (0 until gridSize).all { buttonList[it * gridSize + col].state == firstCellState }
            ) {
                return firstCellState
            }
        }

        // Check diagonal (top-left to bottom-right)
        val firstDiagonalCellState = buttonList[0].state
        if (firstDiagonalCellState != CellState.EMPTY &&
            (0 until gridSize).all { buttonList[it * gridSize + it].state == firstDiagonalCellState }
        ) {
            return firstDiagonalCellState
        }

        // Check diagonal (top-right to bottom-left)
        val firstAntiDiagonalCellState = buttonList[gridSize - 1].state
        if (firstAntiDiagonalCellState != CellState.EMPTY &&
            (0 until gridSize).all { buttonList[it * gridSize + (gridSize - 1 - it)].state == firstAntiDiagonalCellState }
        ) {
            return firstAntiDiagonalCellState
        }

        return null // No winner yet
    }

    private fun checkDraw(list: List<GameButton>): Boolean {
        return list.all { it.state != CellState.EMPTY }
    }

    private fun finishGame() {
        gameIsActive = false
        buttonList = mutableListOf()
        binding.btnPlayAgain.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        var buttonList: MutableList<GameButton> = mutableListOf()
    }
}