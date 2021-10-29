package com.endava.budgetplanner.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.endava.budgetplanner.data.models.user.User
import com.endava.budgetplanner.data.repo.contract.SplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashState {
    object Loading : SplashState()
    data class LoadComplete(val data: User) : SplashState()
    data class Error(val error: String) : SplashState()
}

class SplashViewModel @Inject constructor(
    private val repo: SplashRepository
) : ViewModel() {
    //stub to delete
    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState : StateFlow<SplashState> = _splashState.asStateFlow()

    init {
        configInit()
    }
    //stub to delete
    private fun configInit(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.initConfig().distinctUntilChanged().collectLatest {
                delay(3000L)
                _splashState.value = SplashState.LoadComplete(it)
            }
        }
    }
}