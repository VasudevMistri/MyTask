package com.app.newapptask.localDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BooksDao {

    @Insert
    fun insert(booksModel: BooksModel)

    @Update
    fun update(booksModel: BooksModel)

    @Delete
    fun delete(booksModel: BooksModel)

    @Query("delete from book_table")
    fun deleteAllBooks()

    @Query("select * from book_table order by id desc")
    fun getAllBooks(): LiveData<List<BooksModel>>
}