package com.rashmi.birthdayreminder.di

import android.content.Context
import androidx.room.Room
import com.rashmi.birthdayreminder.db.BirthdayDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBirthdayDB(
        @ApplicationContext context: Context
    ): BirthdayDB {
        return Room.databaseBuilder(
            context,
            BirthdayDB::class.java,
            "birthday_db"
        ).build()
    }

    @Provides
    fun providesBirthdayDao(birthdayDB: BirthdayDB) = birthdayDB.birthdayDao()
}