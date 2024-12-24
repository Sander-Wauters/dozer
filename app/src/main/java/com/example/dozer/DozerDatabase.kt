package com.example.dozer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dozer.machine.data.local.MachineEntity
import com.example.dozer.machine.data.local.MachineDao

@Database(
    entities = [
        MachineEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DozerDatabase: RoomDatabase() {
    abstract fun getMachineDao(): MachineDao

    companion object {
        @Volatile
        private var Instance: DozerDatabase? = null

        fun getDatabase(context: Context): DozerDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DozerDatabase::class.java, "dozerDatabase")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}