package com.app.newapptask.localDB

import android.app.Application
import androidx.lifecycle.LiveData
import com.app.newapptask.utils.subscribeOnBackground

class BooksRepository(application: Application) {

    private var booksDao: BooksDao
    private var allNotes: LiveData<List<BooksModel>>

    private val database = BooksDatabase.getInstance(application)

    init {
        booksDao = database.noteDao()
        allNotes = booksDao.getAllBooks()
    }

    fun insert(booksModel: BooksModel) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            booksDao.insert(booksModel)
        }
    }

    fun update(booksModel: BooksModel) {
        subscribeOnBackground {
            booksDao.update(booksModel)
        }
    }

    fun delete(booksModel: BooksModel) {
        subscribeOnBackground {
            booksDao.delete(booksModel)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            booksDao.deleteAllBooks()
        }
    }

    fun getAllNotes(): LiveData<List<BooksModel>> {
        return allNotes
    }



}