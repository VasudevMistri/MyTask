package com.app.newapptask.localDB

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.newapptask.R
import com.app.newapptask.databinding.ActivityAddEditBooksBinding


const val EXTRA_ID = " com.app.newapptask.LocalDB.EXTRA_ID"
const val EXTRA_TITLE = " com.app.newapptask.LocalDB.EXTRA_TITLE"
const val EXTRA_DESCRIPTION = " com.app.newapptask.LocalDB.EXTRA_DESCRIPTION"
const val EXTRA_PRIORITY = " com.app.newapptask.LocalDB.EXTRA_PRIORITY"

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var mode: Mode
    private var noteId: Int = -1
    lateinit var mBinding: ActivityAddEditBooksBinding

    var booksSelected = ""
    private val status = arrayOf(
        "Book A",
        "Book B",
        "Book C"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_books)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mBinding.sprBooks.adapter = adapter

        noteId = intent.getIntExtra(EXTRA_ID, -1)
        if(noteId==-1)
        {
            mBinding.tvToolbar.setText(resources.getString(R.string.add_books))
        }else{
            mBinding.tvToolbar.setText(resources.getString(R.string.edit_books))
            mBinding.etName.setText(intent.getStringExtra(EXTRA_TITLE))
            mBinding.etMobile.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            booksSelected = intent.getStringExtra(EXTRA_PRIORITY)
            for (i in status.indices) {
                if (status.get(i).equals(booksSelected)) {
                    mBinding.sprBooks.setSelection(i)
                }
            }
        }

        mBinding.sprBooks.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                booksSelected = status.get(p2)
                Log.e("onres select", booksSelected)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        mBinding.btnSave.setOnClickListener {
            saveNote()
        }

    }

    private fun saveNote() {
        val title = mBinding.etName.text.toString()
        val desc = mBinding.etMobile.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "please insert name", Toast.LENGTH_SHORT).show()
            return
        }
        if (desc.isEmpty()) {
            Toast.makeText(this, "please insert mobile number", Toast.LENGTH_SHORT).show()
            return
        }
        if (desc.length<10) {
            Toast.makeText(this, "invalid mobile number", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent()
        // only if note ID was provided i.e. we are editing
        if (noteId != -1)
            data.putExtra(EXTRA_ID, noteId)
        data.putExtra(EXTRA_TITLE, title)
        data.putExtra(EXTRA_DESCRIPTION, desc)
        data.putExtra(EXTRA_PRIORITY, booksSelected)

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private sealed class Mode {
        object AddNote : Mode()
        object EditNote : Mode()
    }
}
