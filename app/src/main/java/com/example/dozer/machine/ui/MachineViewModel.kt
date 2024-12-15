package com.example.dozer.machine.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dozer.machine.data.MachineDto
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.MockMachineRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed interface MachineUiState {
    data class Success(val machines: List<MachineDto.Index>) : MachineUiState
    data object Error : MachineUiState
    data object Loading : MachineUiState
}

class MachineViewModel(
    private val machineRepo: MachineRepository
) : ViewModel() {
    var uiState: MachineUiState by mutableStateOf(MachineUiState.Loading)
        private set

    init {
        getIndex()
    }

    fun getIndex() {
        viewModelScope.launch {
            uiState = MachineUiState.Loading
            uiState = try {
                MachineUiState.Success(machineRepo.getIndex().machines ?: emptyList())
            } catch (e: IOException) {
                MachineUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MachineViewModel(MockMachineRepository())
            }
        }
    }
}