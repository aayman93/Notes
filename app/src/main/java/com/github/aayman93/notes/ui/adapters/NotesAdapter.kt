package com.github.aayman93.notes.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.databinding.ItemNoteBinding
import javax.inject.Inject

class NotesAdapter @Inject constructor() : ListAdapter<Note, NotesAdapter.NotesViewHolder>(Differ) {

    private var onNoteClickListener: ((Note) -> Unit)? = null

    fun setOnNoteClickListener(listener: (Note) -> Unit) {
        onNoteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class NotesViewHolder(
        private val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            with(binding) {
                tvNoteTitle.text = note.title
                tvNoteText.text = note.text

                root.setOnClickListener {
                    onNoteClickListener?.invoke(note)
                }
            }
        }
    }

    companion object Differ : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}