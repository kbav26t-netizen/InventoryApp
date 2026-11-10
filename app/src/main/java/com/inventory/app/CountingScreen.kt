package com.inventory.app

import android.Manifest
import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CountingScreen(onBack: () -> Unit) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var objectCount by remember { mutableStateOf(0) }
    var lastDetectedObjects by remember { mutableStateOf<List<String>>(emptyList()) }

    if (cameraPermissionState.status.isGranted) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Подсчёт объектов") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
            
            CountingCameraPreview(
                onObjectsDetected = { objects ->
                    objectCount = objects.size
                    lastDetectedObjects = objects
                },
                modifier = Modifier.weight(1f)
            )
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Обнаружено объектов:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "$objectCount",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (lastDetectedObjects.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            lastDetectedObjects.joinToString(", "),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Разрешить доступ к камере")
            }
        }
    }
}

@Composable
fun CountingCameraPreview(
    onObjectsDetected: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val analyzerExecutor = remember { Executors.newSingleThreadExecutor() }
    val detector = remember { ObjectDetector(context) }

    DisposableEffect(lifecycleOwner) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(analyzerExecutor) { imageProxy ->
                    val bitmap = imageProxy.toBitmap()
                    val timestampMs = System.currentTimeMillis()
                    
                    // Запускаем детекцию
                    detector.detect(bitmap, timestampMs)
                    
                    // Пока просто считаем кадры (реальная детекция будет позже)
                    onObjectsDetected(emptyList())
                    
                    imageProxy.close()
                }
            }
        
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        onDispose {
            cameraProvider.unbindAll()
            analyzerExecutor.shutdown()
            detector.close()
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier.fillMaxWidth()
    )
}
