package com.example.dozer.machine.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.dozer.common.ui.NetworkErrorIndicator
import com.example.dozer.common.ui.IndexCard
import com.example.dozer.common.ui.LoadingIndicator
import com.example.dozer.common.ui.theme.DozerTheme
import com.example.dozer.machine.data.Machine
import org.koin.compose.koinInject

@Composable
fun MachineScreen(
    machineViewModel: MachineViewModel = koinInject()
) {
    val uiState by machineViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        when(val state = uiState) {
            is MachineUiState.Loading -> LoadingIndicator()
            is MachineUiState.Error -> NetworkErrorIndicator { machineViewModel.getIndex() }
            is MachineUiState.Success -> {
                val machines: LazyPagingItems<Machine> = state.machines.collectAsLazyPagingItems()

                if (machines.loadState.refresh is LoadState.Loading) {
                    LoadingIndicator()
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(400.dp),
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(machines.itemCount) { index ->
                            val machine: Machine? = machines[index]
                            IndexCard(
                                imageUrl = machine?.imageUrl,
                                title = "${machine?.serialNumber} ${machine?.name}",
                                description = machine?.description ?: "",
                                modifier = Modifier.padding(8.dp).width(400.dp)
                            ) {
                                Log.d("Machine clicked", machine?.name ?: "Machine was null")
                            }
                        }
                        item {
                            if (machines.loadState.append is LoadState.Loading)
                                CircularProgressIndicator()
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