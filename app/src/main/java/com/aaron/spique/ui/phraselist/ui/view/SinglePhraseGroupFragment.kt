package com.aaron.spique.ui.phraselist.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.aaron.spique.R
import com.aaron.spique.databinding.FragmentSinglePhraseBinding
import com.aaron.spique.ui.shared.recyclerview.VariableColumnGridLayoutManager
import com.aaron.spique.ui.shared.recyclerview.rearrangeablerecyclerview.DraggableItemTouchHelperCallback
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseItemUiState
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseRecyclerAdapter
import com.aaron.spique.ui.phraselist.ui.viewmodel.PhraseGridViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SinglePhraseGroupFragment : Fragment() {

    private val viewModel: PhraseGridViewModel by viewModels()
    private lateinit var binding: FragmentSinglePhraseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_single_phrase,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO: seems like this num columns should be calculated different to account for large phones / tablets
        binding.phraseRecyclerView.layoutManager = VariableColumnGridLayoutManager(requireContext(), 70)
        val recyclerViewAdapter = PhraseRecyclerAdapter(object : PhraseRecyclerAdapter.Listener {
            override fun onItemClicked(item: PhraseItemUiState) {
                viewModel.phraseItemClicked(item)
            }
        })
        binding.phraseRecyclerView.adapter = recyclerViewAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    recyclerViewAdapter.update(state.phrases)
                    state.utterances.firstOrNull()?.let {
                        Toast.makeText(context, it.phrase, Toast.LENGTH_LONG).show()
//                        speak(it.phrase)
                        viewModel.phraseSpoken(it)
                    }
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(object :
            DraggableItemTouchHelperCallback<PhraseItemUiState, PhraseRecyclerAdapter.PhraseItemViewHolder>(
                recyclerViewAdapter
            ) {
            override fun syncModelClass(from: Int, to: Int) {
                viewModel.itemsMoved(from, to)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.phraseRecyclerView)

    }
}