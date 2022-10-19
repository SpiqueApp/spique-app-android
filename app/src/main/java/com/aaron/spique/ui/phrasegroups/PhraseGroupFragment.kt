package com.aaron.spique.ui.phrasegroups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaron.spique.R
import com.aaron.spique.databinding.FragmentPhraseGroupBinding
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseItemUiState
import com.aaron.spique.ui.shared.SpeakingFragment
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhraseGroupFragment : SpeakingFragment() {

    override val viewModel: PhraseGroupViewModel by viewModels()
    private lateinit var binding: FragmentPhraseGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.phrase_group_fragment_title)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phrase_group, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        val recyclerViewAdapter = PhraseGroupRecyclerAdapter(object : PhraseGroupRecyclerAdapter.PhraseGroupListener {
            override fun onItemClicked(item: PhraseItemUiState) {
                viewModel.addUtterance(item.phrase, item.id)
            }

            override fun onMoreClicked(groupName: String) {
                val direction = PhraseGroupFragmentDirections.actionPhraseGroupFragmentToSinglePhraseGroupFragment(groupName)
                findNavController().navigate(direction)
            }
        })
        binding.phraseGroupsRecyclerview.adapter = recyclerViewAdapter
        binding.phraseGroupsRecyclerview.layoutManager = LinearLayoutManager(context)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    recyclerViewAdapter.submitList(it.groupList)
                    it.groupList.forEachIndexed { index, item ->
                        val vh = binding.phraseGroupsRecyclerview.findViewHolderForAdapterPosition(index) as? PhraseGroupRecyclerAdapter.PhraseGroupViewHolder
                        (vh?.binding?.phraseGroupRecyclerview?.adapter as? PhraseRecyclerAdapter)?.submitList(item.phraseItemList)
                    }
                }
            }
        }
    }
}