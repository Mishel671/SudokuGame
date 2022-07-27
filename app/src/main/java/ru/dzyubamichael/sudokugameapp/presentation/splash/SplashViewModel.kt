package ru.dzyubamichael.sudokugameapp.presentation.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor():ViewModel() {

    private val _launchNextScreen = MutableLiveData<Unit>()
    val launchNextScreen: LiveData<Unit>
    get() = _launchNextScreen

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(4000)
            }
            _launchNextScreen.value = Unit
        }
    }

}