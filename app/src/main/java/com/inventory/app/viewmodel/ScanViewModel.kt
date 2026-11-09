package com.inventory.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.inventory.app.data.db.AppDatabase
import com.inventory.app.data.db.ScanRecord
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val dao = db.scanDao()

    val allScans: StateFlow<List<ScanRecord>> = dao.getAllScans()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addScan(barcode: String) {
        viewModelScope.launch {
            dao.insert(ScanRecord(barcode = barcode))
        }
    }

    fun deleteScan(id: Long) {
        viewModelScope.launch {
            dao.delete(id)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            dao.deleteAll()
        }
    }
}
