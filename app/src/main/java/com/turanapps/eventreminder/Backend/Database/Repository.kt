package com.turanapps.eventreminder.Backend.Database

import com.turanapps.eventreminder.Backend.Database.RoomDB.Dao.DiaryDao
import com.turanapps.eventreminder.Backend.Database.RoomDB.Dao.EventDao
import com.turanapps.eventreminder.Backend.Database.RoomDB.MyRoomDatabase
import javax.inject.Inject

class Repository @Inject constructor (
    private val myDatabase: MyRoomDatabase
    ) {

    fun getEventDao(): EventDao {
        return myDatabase.eventDao()
    }

    fun getDiaryDao(): DiaryDao {
        return myDatabase.diaryDao()
    }

}