package com.endrollmodel.notesample.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.endrollmodel.notesample.room.dao.MemoDao
import com.endrollmodel.notesample.room.dao.TodoDao
import com.endrollmodel.notesample.room.entity.MemoEntity
import com.endrollmodel.notesample.room.entity.TodoEntity

@Database(entities = [TodoEntity::class, MemoEntity::class], version = 1, exportSchema = false)
abstract class DataBaseHelper : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun memoDao(): MemoDao
    companion object {
        private var INSTANCE: DataBaseHelper? = null
        fun getInstance(context: Context): DataBaseHelper? {
            if (INSTANCE == null) {
                synchronized(DataBaseHelper::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        DataBaseHelper::class.java,
                        DataBaseHelper::class.java.simpleName
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}