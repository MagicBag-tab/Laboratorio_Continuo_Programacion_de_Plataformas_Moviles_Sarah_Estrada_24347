package com.magicbag.laboratorio_continuo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LaboratorioDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao

    companion object {
        @Volatile
        private var INSTANCE: LaboratorioDatabase? = null

        fun getDatabase(context: Context): LaboratorioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LaboratorioDatabase::class.java,
                    "laboratorio_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}