package com.turanapps.eventreminder.Backend.Database.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.turanapps.eventreminder.Backend.Database.RoomDB.Dao.DiaryDao
import com.turanapps.eventreminder.Backend.Database.RoomDB.Dao.EventDao
import com.turanapps.eventreminder.CONST.Const.ROOM_DATABASE_NAME
import com.turanapps.eventreminder.Model.Diary
import com.turanapps.eventreminder.Model.Event
import com.turanapps.eventreminder.Util.Converters

@Database(entities = [Event::class, Diary::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun diaryDao(): DiaryDao

    companion object {
        @Volatile
        private var instance: MyRoomDatabase? = null

        fun getInstance(context: Context): MyRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MyRoomDatabase {
            return Room.databaseBuilder(context.applicationContext, MyRoomDatabase::class.java, ROOM_DATABASE_NAME)
                .build()
        }
    }
}