package ru.dzyubamichael.sudokugameapp.presentation.choosegame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.dzyubamichael.sudokugameapp.R
import ru.dzyubamichael.sudokugameapp.databinding.FragmentChooseGameBinding
import ru.dzyubamichael.sudokugameapp.domain.GameItem
import ru.dzyubamichael.sudokugameapp.presentation.choosegame.adapter.GamesListAdapter
import ru.dzyubamichael.sudokugameapp.presentation.game.GameFragment
import ru.dzyubamichael.sudokugameapp.presentation.viewBinding

@AndroidEntryPoint
class ChooseGameFragment : Fragment(R.layout.fragment_choose_game) {

    private val binding by viewBinding(FragmentChooseGameBinding::bind)

    private val viewModel: ChooseGameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNewGame()
        setupRecyclerView()
    }

    private fun setNewGame() {
        binding.btnNewGame.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_up_enter_anim)
                .setPopExitAnim(R.anim.slide_down_exit_anim)
                .build()
            findNavController().navigate(
                ChooseGameFragmentDirections.actionChooseGameFragmentToGameFragment(
                    GameFragment.NEW_GAME_MODE,
                    null
                ),
                navOptions
            )
        }
    }

    private fun setupRecyclerView() {
        val adapter = GamesListAdapter(requireActivity())
        binding.rvGamesList.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvGamesList.adapter = adapter

        postponeEnterTransition()
        binding.rvGamesList.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        adapter.onGameClickListener = { gameItem: GameItem, imageContainer: ConstraintLayout ->
            val extras = FragmentNavigatorExtras(
                imageContainer to gameItem.id.toString()
            )
            val action = ChooseGameFragmentDirections
                .actionChooseGameFragmentToGameFragment(
                    GameFragment.SAVED_GAME_MODE,
                    gameItem
                )
            findNavController().navigate(action, extras)
        }

        adapter.onPressedListener = { id ->
            viewModel.deleteGameItem(id)
        }
        viewModel.getGamesList.observe(viewLifecycleOwner) { list ->
            if (list.isNotEmpty()) {
                adapter.submitList(list)
                binding.tvNotify.visibility = View.INVISIBLE
            } else {
                binding.tvNotify.visibility = View.VISIBLE
            }
        }
    }
}