package com.mta.hwapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

//@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class HWDatabase: RoomDatabase() {

    private val NUMBER_OF_THREAD = 4
    val databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREAD)

    companion object {

        @Volatile
        private var instance: HWDatabase? = null

        fun getDatabase(context: Context): HWDatabase{

            return instance ?: synchronized(this){
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    HWDatabase::class.java,
                    "hw-app"
                ).build()
                instance = database
                return database
            }
        }
    }
}