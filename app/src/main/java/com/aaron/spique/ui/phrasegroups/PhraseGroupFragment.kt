package com.aaron.spique.ui.phrasegroups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaron.spique.R
import com.aaron.spique.databinding.FragmentPhraseGroupBinding
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseItemUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhraseGroupFragment : Fragment() {

    private val viewModel: PhraseGroupViewModel by viewModels()
    private lateinit var binding: FragmentPhraseGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phrase_group, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        val recyclerViewAdapter = PhraseGroupRecyclerAdapter(object : PhraseGroupRecyclerAdapter.PhraseGroupListener {
            override fun onItemClicked(item: PhraseItemUiState) {
                //todo
            }

            override fun onMoreClicked(groupName: String) {
                val direction = PhraseGroupFragmentDirections.actionPhraseGroupFragmentToSinglePhraseGroupFragment()
                findNavController().navigate(direction)
            }
        })
        binding.phraseGroupsRecyclerview.adapter = recyclerViewAdapter
        binding.phraseGroupsRecyclerview.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    recyclerViewAdapter.update(it.groupList)
                }
            }
        }
    }
}