package com.app.newapptask.localDB

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.newapptask.R
import com.app.newapptask.databinding.ActivityBooksMainBinding
import kotlinx.android.synthetic.main.activity_books_main.*


const val ADD_NOTE_REQUEST = 1
const val EDIT_NOTE_REQUEST = 2

class BooksMainActivity : AppCompatActivity() {

    private lateinit var vm: BooksViewModel
    private lateinit var adapter: NoteAdapter
    lateinit var mBinding: ActivityBooksMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_books_main)

        setUpRecyclerView()

        setUpListeners()

        vm = ViewModelProviders.of(this)[BooksViewModel::class.java]

        vm.getAllNotes().observe(this, Observer {
            adapter.submitList(it)
        })

        mBinding.imgDelete.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Delete Alert")
                .setMessage("Do you really want delete all books?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(
                    android.R.string.yes
                ) { dialog, whichButton ->
                    vm.deleteAllNotes()
                    Toast.makeText(this, "All books deleted!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(android.R.string.no, null).show()
        }


    }

    private fun setUpListeners() {
        button_add_note.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        // swipe listener
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.getNoteAt(viewHolder.adapterPosition)
                vm.delete(note)
                Toast.makeText(this@BooksMainActivity, "Book deleted!", Toast.LENGTH_SHORT).show()
            }

        }).attachToRecyclerView(recycler_view)
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = NoteAdapter { clickedNote ->
            val intent = Intent(this, AddEditNoteActivity::class.java)
            intent.putExtra(EXTRA_ID, clickedNote.id)
            intent.putExtra(EXTRA_TITLE, clickedNote.name)
            intent.putExtra(EXTRA_DESCRIPTION, clickedNote.mobile)
            intent.putExtra(EXTRA_PRIORITY, clickedNote.books)
            startActivityForResult(intent, EDIT_NOTE_REQUEST)
        }
        recycler_view.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val title: String = data.getStringExtra(EXTRA_TITLE)
            val description: String = data.getStringExtra(EXTRA_DESCRIPTION)
            val priority: String = data.getStringExtra(EXTRA_PRIORITY)
            vm.insert(BooksModel(title, description, priority))
            Toast.makeText(this, "Book inserted!", Toast.LENGTH_SHORT).show()

        } else if (data != null && requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data.getIntExtra(EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Book couldn't be updated!", Toast.LENGTH_SHORT).show()
                return
            }
            val title: String = data.getStringExtra(EXTRA_TITLE)
            val description: String =
                data.getStringExtra(EXTRA_DESCRIPTION)
            val priority: String = data.getStringExtra(EXTRA_PRIORITY)
            vm.update(BooksModel(title, description, priority, id))
            Toast.makeText(this, "Book updated!", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Book not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}
