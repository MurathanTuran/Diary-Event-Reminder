package com.turanapps.eventreminder.DI

import android.content.Context
import androidx.room.Room
import com.turanapps.eventreminder.Backend.Controller.Concrete.DiaryControllerImpl
import com.turanapps.eventreminder.Backend.Controller.Concrete.EventControllerImpl
import com.turanapps.eventreminder.Backend.Database.Repository
import com.turanapps.eventreminder.Backend.Database.RoomDB.MyRoomDatabase
import com.turanapps.eventreminder.Backend.Service.Concrete.DiaryServiceImpl
import com.turanapps.eventreminder.Backend.Service.Concrete.EventServiceImpl
import com.turanapps.eventreminder.CONST.Const.ROOM_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectDiaryDao(
        database: MyRoomDatabase
    ) = database.diaryDao()

    @Singleton
    @Provides
    fun injectEventDao(
        database: MyRoomDatabase
    ) = database.eventDao()

    @Singleton
    @Provides
    fun injectMyRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MyRoomDatabase::class.java, ROOM_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun injectRepository(
        database: MyRoomDatabase
    ) = Repository(database)

    @Singleton
    @Provides
    fun injectDiaryControllerImpl(
        service: DiaryServiceImpl
    ) = DiaryControllerImpl(service)

    @Singleton
    @Provides
    fun injectEventControllerImpl(
        service: EventServiceImpl
    ) = EventControllerImpl(service)

    @Singleton
    @Provides
    fun injectEventServiceImpl(
        repository: Repository
    ) = EventServiceImpl(repository)

    @Singleton
    @Provides
    fun injectDiaryServiceImpl(
        repository: Repository
    ) = DiaryServiceImpl(repository)

}