package com.example.dozer.machine.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "machines"
)
data class Machine (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "serialNumber")
    val serialNumber: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?
)
