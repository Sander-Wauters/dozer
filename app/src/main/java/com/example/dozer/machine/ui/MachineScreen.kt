package com.example.dozer.machine.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dozer.common.ui.NetworkErrorIndicator
import com.example.dozer.common.ui.IndexCard
import com.example.dozer.common.ui.LoadingIndicator
import com.example.dozer.common.ui.theme.DozerTheme

@Composable
fun MachineScreen(
    machineViewModel: MachineViewModel
) {
    val uiState by machineViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(400.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            when(val state = uiState) {
                is MachineUiState.Loading -> item {
                    LoadingIndicator()
                }
                is MachineUiState.Error -> item {
                    NetworkErrorIndicator {
                        machineViewModel.getIndex()
                    }
                }
                is MachineUiState.Success -> {
                    items(state.machines) { machine ->
                        IndexCard(
                            imageUrl = machine.imageUrl,
                            title = "${machine.serialNumber} ${machine.name}",
                            description = machine.description ?: "",
                            modifier = Modifier.padding(8.dp).width(400.dp)
                        ) {
                            Log.d("Machine clicked", machine.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MachineScreenPreview() {
    DozerTheme {
        MachineScreen(viewModel(factory = MachineViewModel.Factory))
    }
}