package com.example.dozer.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dozer.R
import com.example.dozer.common.ui.theme.DozerTheme
import com.example.dozer.machine.data.MachineDatasource
import com.example.dozer.machine.ui.MachineUiState
import com.example.dozer.machine.ui.UiState

@Composable
fun AsyncData(
    data: UiState,
    retry: () -> Unit,
    content: @Composable () -> Unit
) {
    when (data) {
        is UiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }
        is UiState.Error -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_connection_error),
                    contentDescription = ""
                )
                Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
                Button(onClick = retry) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
        is UiState.Success -> content()
    }
}

@Preview(showBackground = true)
@Composable
fun AsyncDataLoadingPreview() {
    DozerTheme {
        AsyncData(
            data = MachineUiState.Loading,
            retry = {}
        ) {
            Text("should not be visible")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsyncDataErrorPreview() {
    DozerTheme {
        AsyncData(
            data = MachineUiState.Error,
            retry = {}
        ) {
            Text("should not be visible")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsyncDataSuccessPreview() {
    DozerTheme {
        AsyncData(
            data = MachineUiState.Success(MachineDatasource().loadMachines()),
            retry = {}
        ) {
            Text("should be visible")
        }
    }
}
