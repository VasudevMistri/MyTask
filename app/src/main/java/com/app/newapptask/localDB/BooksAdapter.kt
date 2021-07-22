package com.app.newapptask.localDB

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.app.newapptask.R

class NoteAdapter(private val onItemClickListener: (BooksModel) -> Unit) :
    ListAdapter<BooksModel, NoteAdapter.NoteHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.books_item, parent,
            false
        )
        return NoteHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        with(getItem(position)) {
            holder.tvName.text = name
            holder.tvMobile.text = mobile
            holder.tvBooks.text = books
        }
    }

    fun getNoteAt(position: Int) = getItem(position)


    inner class NoteHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMobile: TextView = itemView.findViewById(R.id.tvMobile)
        val tvBooks: TextView = itemView.findViewById(R.id.tvBooks)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != NO_POSITION)
                    onItemClickListener(getItem(adapterPosition))
            }
        }

    }
}

private val diffCallback = object : DiffUtil.ItemCallback<BooksModel>() {
    override fun areItemsTheSame(oldItem: BooksModel, newItem: BooksModel) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BooksModel, newItem: BooksModel) =
        oldItem.name == newItem.name
                && oldItem.mobile == newItem.mobile
                && oldItem.books == newItem.books
}