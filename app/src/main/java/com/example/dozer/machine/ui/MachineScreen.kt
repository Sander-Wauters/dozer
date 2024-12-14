package com.example.dozer.machine.ui

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dozer.common.ui.IndexCard
import com.example.dozer.machine.data.MachineDatasource
import com.example.dozer.machine.data.MachineDto
import com.example.dozer.ui.theme.DozerTheme

@Composable
fun MachineScreen(
    machines: List<MachineDto.Index>
) {
    Scaffold { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(400.dp),
            modifier = Modifier.padding(innerPadding)
        ) {
            items(machines) { machine ->
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

@Preview(showBackground = true)
@Composable
fun MachineScreenPreview() {
    DozerTheme {
        MachineScreen(
            machines = MachineDatasource().loadMachines()
        )
    }
}