package ru.dzyubamichael.sudokugameapp.presentation.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.dzyubamichael.sudokugameapp.R
import ru.dzyubamichael.sudokugameapp.databinding.FragmentSplashBinding
import ru.dzyubamichael.sudokugameapp.presentation.viewBinding


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    private val viewModel: SplashViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.launchNextScreen.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_splashFragment_to_chooseGameFragment)
        }
    }

}