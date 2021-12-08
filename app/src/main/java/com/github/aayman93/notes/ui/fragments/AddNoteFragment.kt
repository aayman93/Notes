package com.github.aayman93.notes.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.aayman93.notes.R
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.databinding.FragmentAddNoteBinding
import com.github.aayman93.notes.ui.viewmodels.AddEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        if (savedInstanceState == null) {
            binding.inputTitle.requestFocus()
        }
    }

    private fun addNote() {
        val dateInMillis = System.currentTimeMillis()
        val noteTitle = binding.inputTitle.text.toString()
        val noteText = binding.inputText.text.toString()

        if (viewModel.validateInputs(noteTitle, noteText)) {
            val note = Note(
                title = noteTitle,
                text = noteText,
                dateCreated = dateInMillis,
                dateModified = dateInMillis
            )
            viewModel.addNote(note)
            Toast.makeText(requireContext(), R.string.note_added, Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), R.string.error_empty_fields, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                addNote()
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