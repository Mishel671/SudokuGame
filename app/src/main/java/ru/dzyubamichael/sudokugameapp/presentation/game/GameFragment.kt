package ru.dzyubamichael.sudokugameapp.presentation.game

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.dzyubamichael.sudokugameapp.R
import ru.dzyubamichael.sudokugameapp.databinding.FragmentGameBinding
import ru.dzyubamichael.sudokugameapp.presentation.viewBinding

@AndroidEntryPoint
class GameFragment : Fragment(R.layout.fragment_game) {

    private val args: GameFragmentArgs by navArgs()

    private val binding by viewBinding(FragmentGameBinding::bind)

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        setNumbersClickListeners()
        setSolveAndCleanClickListener()
    }

    private fun launchRightMode() {
        when (args.launchMode) {
            NEW_GAME_MODE -> {
                onBackPressedListener(true)

            }
            SAVED_GAME_MODE -> {
                startSavedGame()
                onBackPressedListener(false)
            }
        }
    }


    private fun startSavedGame() {
        val gameItem = args.gameItem!!
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext())
                .inflateTransition(android.R.transition.move)
        binding.root.transitionName = gameItem.id.toString()
        binding.sudokuBoard.setGameData(gameItem.gameData)
    }



    private fun setSolveAndCleanClickListener() {
        with(binding) {
            btnClean.setOnClickListener {
                sudokuBoard.solver.resetBoard()
                sudokuBoard.invalidate()
            }
            btnSolve.setOnClickListener {
                sudokuBoard.solver.getEmptyBoxIndexes()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        sudokuBoard.solver.solve(sudokuBoard)
                    }
                }
                sudokuBoard.invalidate()
            }
        }
    }

    private fun setNumbersClickListeners() {
        with(binding) {
            btnOne.setOnClickListener {
                setNumber(1)
            }
            btnTwo.setOnClickListener {
                setNumber(2)
            }
            btnThree.setOnClickListener {
                setNumber(3)
            }
            btnFour.setOnClickListener {
                setNumber(4)
            }
            btnFive.setOnClickListener {
                setNumber(5)
            }
            btnSix.setOnClickListener {
                setNumber(6)
            }
            btnSeven.setOnClickListener {
                setNumber(7)
            }
            btnEight.setOnClickListener {
                setNumber(8)
            }
            btnNine.setOnClickListener {
                setNumber(9)
            }
        }
    }

    private fun setNumber(number: Int) {
        val sudokuBoard = binding.sudokuBoard
        sudokuBoard.solver.setNumberPos(number)
        sudokuBoard.invalidate()
    }

    private fun onBackPressedListener(isNewGame: Boolean){
        val callback = object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    val gameData = binding.sudokuBoard.getGameData()
                    val logo = binding.sudokuBoard.drawToBitmap(config = Bitmap.Config.ARGB_8888)
                    if (isNewGame){
                        viewModel.addGame(gameData, logo)
                    } else {
                        viewModel.updateGame(gameData, logo, args.gameItem!!.id)
                    }
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    companion object {
        const val NEW_GAME_MODE = "new_game"
        const val SAVED_GAME_MODE = "saved_game"
    }
}
