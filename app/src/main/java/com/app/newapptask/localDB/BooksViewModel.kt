package com.app.newapptask.localDB

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class BooksViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = BooksRepository(app)
    private val allNotes = repository.getAllNotes()

    fun insert(booksModel: BooksModel) {
        repository.insert(booksModel)
    }

    fun update(booksModel: BooksModel) {
        repository.update(booksModel)
    }

    fun delete(booksModel: BooksModel) {
        repository.delete(booksModel)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<List<BooksModel>> {
        return allNotes
    }


}