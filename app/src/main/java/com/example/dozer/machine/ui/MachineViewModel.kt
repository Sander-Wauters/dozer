package com.example.dozer.machine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dozer.machine.data.Machine
import com.example.dozer.machine.data.MachineRepository
import com.example.dozer.machine.data.MockMachineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed interface MachineUiState {
    data class Success(val machines: Flow<PagingData<Machine>>) : MachineUiState
    data object Error : MachineUiState
    data object Loading : MachineUiState
}

class MachineViewModel(
    private val machineRepo: MachineRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<MachineUiState> = MutableStateFlow(MachineUiState.Loading)
    val uiState: StateFlow<MachineUiState> = _uiState

    init {
        getIndex()
    }

    fun getIndex() {
        viewModelScope.launch {
            _uiState.value = MachineUiState.Success(machineRepo.getMachines()
                .catch { MachineUiState.Error }
                .cachedIn(viewModelScope)
            )
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