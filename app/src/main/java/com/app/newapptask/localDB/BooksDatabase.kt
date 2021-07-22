package com.app.newapptask.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.newapptask.utils.subscribeOnBackground

@Database(entities = [BooksModel::class], version = 1)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun noteDao(): BooksDao

    companion object {
        private var instance: BooksDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): BooksDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, BooksDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: BooksDatabase) {
            val noteDao = db.noteDao()
            subscribeOnBackground {
                noteDao.insert(BooksModel("Dev", "8888999900", "Book A"))
            }
        }
    }



}