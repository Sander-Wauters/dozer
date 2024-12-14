package com.example.dozer.machine.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dozer.machine.data.MachineDatasource
import com.example.dozer.machine.data.MachineDto
import kotlinx.coroutines.launch
import okio.IOException

sealed interface UiState {
    sealed interface Success : UiState
    sealed interface Error : UiState
    sealed interface Loading : UiState
}

sealed interface MachineUiState : UiState {
    data class Success(val machines: List<MachineDto.Index>) : MachineUiState, UiState.Success
    data object Error : MachineUiState, UiState.Error
    data object Loading : MachineUiState, UiState.Loading
}

class MachineViewModel : ViewModel() {
    var uiState: MachineUiState by mutableStateOf(MachineUiState.Loading)
        private set

    init {
        getIndex()
    }

    fun getIndex() {
        viewModelScope.launch {
            uiState = MachineUiState.Loading
            uiState = try {
                MachineUiState.Success(MachineDatasource().loadMachines())
            } catch (e: IOException) {
                MachineUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MachineViewModel()
            }
        }
    }
}