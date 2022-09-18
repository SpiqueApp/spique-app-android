package com.aaron.spique.ui.phraselist.ui.view.phraserecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aaron.spique.ui.shared.recyclerview.rearrangeablerecyclerview.ReArrangeableRecyclerAdapter
import com.aaron.spique.databinding.PhraseItemBinding

class PhraseRecyclerAdapter(private val listener: Listener, private val fixedItemWidth: Int? = null): ReArrangeableRecyclerAdapter<PhraseItemUiState, PhraseRecyclerAdapter.PhraseItemViewHolder>() {

    interface Listener {
        fun onItemClicked(item: PhraseItemUiState)
    }

    override val items: MutableList<PhraseItemUiState> = mutableListOf()

    class PhraseItemViewHolder(private val binding: PhraseItemBinding, private val fixedItemWidth: Int?): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhraseItemUiState, listener: Listener) {
            binding.model = item
            binding.listener = listener
            fixedItemWidth?.let {
                val lp = binding.root.layoutParams
                lp.width = fixedItemWidth
                binding.root.layoutParams = lp
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseItemViewHolder {
        return PhraseItemViewHolder(PhraseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), fixedItemWidth)
    }

    override fun onBindViewHolder(holder: PhraseItemViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}