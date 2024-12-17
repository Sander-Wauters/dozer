package com.example.dozer.machine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dozer.machine.data.MachineDto
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.MockMachineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface MachineUiState {
    data class Success(val machines: List<MachineDto.Index>) : MachineUiState
    data object Error : MachineUiState
    data object Loading : MachineUiState
}

class MachineViewModel(
    private val machineRepo: MachineRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MachineUiState> = MutableStateFlow(MachineUiState.Success(emptyList()))
    val uiState: StateFlow<MachineUiState> = _uiState

    init {
        getIndex()
    }

    fun getIndex() {
        viewModelScope.launch {
            machineRepo.getIndex()
                .map {
                    MachineUiState.Success(it)
                }
                .catch {
                    MachineUiState.Error
                }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = MachineUiState.Loading
                )
                .collect {
                    _uiState.value = it
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