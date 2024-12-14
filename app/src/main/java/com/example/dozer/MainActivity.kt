package com.example.dozer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dozer.machine.ui.MachineScreen
import com.example.dozer.common.ui.theme.DozerTheme
import com.example.dozer.machine.ui.MachineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DozerTheme {
                MachineScreen(viewModel(factory = MachineViewModel.Factory))
            }
        }
    }
}
