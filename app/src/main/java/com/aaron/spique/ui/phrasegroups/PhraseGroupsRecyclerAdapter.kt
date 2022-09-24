package com.aaron.spique.ui.phrasegroups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.databinding.PhraseGroupItemBinding
import com.aaron.spique.ui.shared.recyclerview.baserecycleradapter.BaseRecyclerAdapter
import com.aaron.spique.ui.shared.recyclerview.phraserecyclerview.PhraseRecyclerAdapter

class PhraseGroupRecyclerAdapter(val listener: PhraseGroupListener) : BaseRecyclerAdapter<PhraseGroupRecyclerAdapter.PhraseGroupViewHolder>() {

    interface PhraseGroupListener: PhraseRecyclerAdapter.Listener {
        fun onMoreClicked(groupName: String)
    }

    class PhraseGroupViewHolder(val binding: PhraseGroupItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhraseGroupItemUiState, listener: PhraseGroupListener) {
            binding.uiState = item
            val adapter = PhraseRecyclerAdapter(listener, 450)
            binding.phraseGroupRecyclerview.adapter = adapter
            binding.phraseGroupRecyclerview.layoutManager = GridLayoutManager(binding.root.context, 2, RecyclerView.HORIZONTAL, false)
            binding.moreButton.setOnClickListener { listener.onMoreClicked(item.header) }
            adapter.submitList(item.phraseItemList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseGroupViewHolder {
        return PhraseGroupViewHolder(PhraseGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PhraseGroupViewHolder, position: Int) {
        holder.bind(currentList[position] as PhraseGroupItemUiState, listener)
    }

    override fun getItemCount(): Int = currentList.size
}