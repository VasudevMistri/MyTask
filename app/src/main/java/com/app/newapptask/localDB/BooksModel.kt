package com.app.newapptask.localDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BooksModel(val name: String,
                      val mobile: String,
                      val books: String,
                      @PrimaryKey(autoGenerate = false) val id: Int? = null)