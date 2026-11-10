package com.inventory.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inventory.app.presentation.HistoryScreen
import com.inventory.app.presentation.ModeSelectionScreen
import com.inventory.app.viewmodel.ScanViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val viewModel: ScanViewModel = viewModel()
                    val scans by viewModel.allScans.collectAsState()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") {
                            ModeSelectionScreen(
                                onBarcodeModeClick = { navController.navigate("camera") },
                                onCountingModeClick = { navController.navigate("counting") }
                            )
                        }
                        composable("camera") {
                            CameraScreen(
                                viewModel = viewModel,
                                onOpenHistory = { navController.navigate("history") }
                            )
                        }
                        composable("history") {
                            HistoryScreen(
                                scans = scans,
                                onDelete = { viewModel.deleteScan(it) },
                                onClearAll = { viewModel.clearAll() },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("counting") {
                            CountingScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
