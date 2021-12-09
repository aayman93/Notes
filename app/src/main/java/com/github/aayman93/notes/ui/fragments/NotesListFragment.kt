package com.github.aayman93.notes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.aayman93.notes.R
import com.github.aayman93.notes.data.models.Note
import com.github.aayman93.notes.databinding.FragmentNotesListBinding
import com.github.aayman93.notes.ui.adapters.NotesAdapter
import com.github.aayman93.notes.ui.viewmodels.NotesListViewModel
import com.github.aayman93.notes.util.Constants.KEY_SORTED_BY
import com.github.aayman93.notes.util.Constants.SORTED_BY_DATE_CREATED_INDEX
import com.github.aayman93.notes.util.Constants.SORTED_BY_DATE_MODIFIED_INDEX
import com.github.aayman93.notes.util.Constants.SORTED_BY_TITLE_INDEX
import com.github.aayman93.notes.util.PrefsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotesListFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesListViewModel by viewModels()

    @Inject lateinit var notesAdapter: NotesAdapter

    private lateinit var prefsManager: PrefsManager

    private val notesObserver = Observer<List<Note>> { notes ->
        notesAdapter.submitList(notes)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setListeners()
    }

    private fun init() {
        prefsManager = PrefsManager(requireContext())
        binding.notesRecycler.adapter = notesAdapter
        when (prefsManager.getInt(KEY_SORTED_BY, SORTED_BY_TITLE_INDEX)) {
            SORTED_BY_TITLE_INDEX -> { getNotesSortedByTitle() }
            SORTED_BY_DATE_CREATED_INDEX -> { getNotesSortedByDateCreated() }
            SORTED_BY_DATE_MODIFIED_INDEX -> { getNotesSortedByDateModified() }
        }
    }

    private fun setListeners() {
        with(binding) {
            fab.setOnClickListener {
                findNavController().navigate(
                    NotesListFragmentDirections.actionNotesListFragmentToAddNoteFragment()
                )
            }

            tvSortBy.setOnClickListener { v ->
                PopupMenu(requireContext(), v).apply {
                    setOnMenuItemClickListener(this@NotesListFragment)
                    inflate(R.menu.menu_popup_sort)
                    menu.getItem(
                        prefsManager.getInt(KEY_SORTED_BY, SORTED_BY_TITLE_INDEX)
                    ).isChecked = true
                    show()
                }
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.sort_by_title -> {
                getNotesSortedByTitle()
                true
            }
            R.id.sort_by_date_created -> {
                getNotesSortedByDateCreated()
                true
            }
            R.id.sort_by_date_modified -> {
                getNotesSortedByDateModified()
                true
            }
            else -> false
        }
    }

    private fun getNotesSortedByTitle() {
        prefsManager.putInt(KEY_SORTED_BY, SORTED_BY_TITLE_INDEX)
        binding.tvSortBy.text = getString(R.string.label_title)
        viewModel.sortedByTitle.observe(viewLifecycleOwner, notesObserver)

        viewModel.sortedByDateCreated.removeObserver(notesObserver)
        viewModel.sortedByDateModified.removeObserver(notesObserver)
    }

    private fun getNotesSortedByDateCreated() {
        prefsManager.putInt(KEY_SORTED_BY, SORTED_BY_DATE_CREATED_INDEX)
        binding.tvSortBy.text = getString(R.string.label_date_created)
        viewModel.sortedByDateCreated.observe(viewLifecycleOwner, notesObserver)

        viewModel.sortedByTitle.removeObserver(notesObserver)
        viewModel.sortedByDateModified.removeObserver(notesObserver)
    }

    private fun getNotesSortedByDateModified() {
        prefsManager.putInt(KEY_SORTED_BY, SORTED_BY_DATE_MODIFIED_INDEX)
        binding.tvSortBy.text = getString(R.string.label_date_modified)
        viewModel.sortedByDateModified.observe(viewLifecycleOwner, notesObserver)

        viewModel.sortedByTitle.removeObserver(notesObserver)
        viewModel.sortedByDateCreated.removeObserver(notesObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}