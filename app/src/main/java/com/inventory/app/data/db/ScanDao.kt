package com.inventory.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScanDao {
    @Query("SELECT * FROM scan_records ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<ScanRecord>>

    @Insert
    suspend fun insert(record: ScanRecord)

    @Query("DELETE FROM scan_records WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM scan_records")
    suspend fun deleteAll()
}
