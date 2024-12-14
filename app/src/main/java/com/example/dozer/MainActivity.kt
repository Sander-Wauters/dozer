package com.example.dozer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dozer.machine.data.MachineDatasource
import com.example.dozer.machine.ui.MachineScreen
import com.example.dozer.ui.theme.DozerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DozerTheme {
                MachineScreen(MachineDatasource().loadMachines())
            }
        }
    }
}
