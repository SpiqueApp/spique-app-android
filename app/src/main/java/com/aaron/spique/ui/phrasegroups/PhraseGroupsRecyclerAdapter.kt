package com.aaron.spique.ui.phrasegroups

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.databinding.PhraseGroupItemBinding
import com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview.PhraseRecyclerAdapter

class PhraseGroupRecyclerAdapter(val listener: PhraseGroupListener) : RecyclerView.Adapter<PhraseGroupRecyclerAdapter.PhraseGroupViewHolder>() {

    interface PhraseGroupListener: PhraseRecyclerAdapter.Listener {
        fun onMoreClicked(groupName: String)
    }

    private val phraseGroups = mutableListOf<PhraseGroupItemUiState>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<PhraseGroupItemUiState>) {
        phraseGroups.clear()
        phraseGroups.addAll(newList)
        notifyDataSetChanged()
    }

    class PhraseGroupViewHolder(private val binding: PhraseGroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhraseGroupItemUiState, listener: PhraseGroupListener) {
            binding.uiState = item
            val adapter = PhraseRecyclerAdapter(listener, 450)
            binding.phraseGroupRecyclerview.adapter = adapter
            binding.phraseGroupRecyclerview.layoutManager = GridLayoutManager(binding.root.context, 2, RecyclerView.HORIZONTAL, false)
            binding.moreButton.setOnClickListener { listener.onMoreClicked(item.header) }
            adapter.update(item.phraseItemList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseGroupViewHolder {
        return PhraseGroupViewHolder(PhraseGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhraseGroupViewHolder, position: Int) {
        holder.bind(phraseGroups[position], listener)
    }

    override fun getItemCount(): Int = phraseGroups.size
}