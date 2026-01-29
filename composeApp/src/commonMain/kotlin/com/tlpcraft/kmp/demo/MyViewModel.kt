package com.tlpcraft.kmp.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.MyUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyViewModel(val myUseCase: MyUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<ScreenUiState> = MutableStateFlow(ScreenUiState(""))
    val uiState = _uiState.asStateFlow()

    fun doWhatever() {
        viewModelScope.launch {
            delay(3000)
            val myValue = myUseCase(Unit)

            _uiState.update { ScreenUiState(myValue.myProperty) }
        }
    }
}
