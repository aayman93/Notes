package com.github.aayman93.notes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.aayman93.notes.R
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.databinding.FragmentEditNoteBinding
import com.github.aayman93.notes.ui.viewmodels.AddEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val args: EditNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private val viewModel: AddEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        currentNote = args.note
        updateUi(currentNote)
    }

    private fun updateUi(note: Note) {
        with(binding) {
            inputTitle.setText(note.title)
            inputText.setText(note.text)
        }
    }

    private fun updateNote() {
        val noteTitle = binding.inputTitle.text.toString()
        val noteText = binding.inputText.text.toString()

        if (viewModel.validateInputs(noteTitle, noteText)) {
            val updatedNote = Note(
                id = currentNote.id,
                title = noteTitle,
                text = noteText,
                dateCreated = currentNote.dateCreated,
                dateModified = System.currentTimeMillis()
            )
            viewModel.updateNote(updatedNote)
            Toast.makeText(requireContext(), R.string.note_updated, Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_message)
            .setPositiveButton(getString(R.string.delete_dialog_positive_button_text)) { _, _ ->
                deleteNote()
            }
            .setNegativeButton(getString(R.string.delete_dialog_negative_button_text)) { _, _ -> }
            .create().show()
    }

    private fun deleteNote() {
        viewModel.deleteNote(currentNote)
        Toast.makeText(requireContext(), R.string.note_deleted, Toast.LENGTH_SHORT).show()

        findNavController().navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_save -> {
                updateNote()
                true
            }
            R.id.action_delete -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}