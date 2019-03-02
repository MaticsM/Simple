package hu.mark.simple.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import hu.mark.simple.MainApplication
import hu.mark.simple.interfaces.DaoSimple
import hu.mark.simple.util.Constants

@Database(entities = [SimpleCache::class], version = 1, exportSchema = false)
abstract class SimpleDatabase : RoomDatabase() {
    abstract fun daoSimple(): DaoSimple

    companion object {
        val database = Room
            .databaseBuilder(
                MainApplication.applicationContext(),
                SimpleDatabase::class.java,
                Constants.DB
            )
            .build()
    }
}