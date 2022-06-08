package com.example.android_recyclerviewsyncedwithtabs_example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_recyclerviewsyncedwithtabs_example.databinding.ItemPersonHorizontalBinding
import com.example.android_recyclerviewsyncedwithtabs_example.databinding.ItemPersonVerticalBinding

class PersonAdapter : ListAdapter<PersonItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonItem>() {
            override fun areItemsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PersonItem, newItem: PersonItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (PersonItem.ViewType.values()[viewType]) {
            PersonItem.ViewType.Horizontal -> {
                HorizontalViewHolder(
                        binding = ItemPersonHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            PersonItem.ViewType.Vertical -> {
                VerticalViewHolder(
                        binding = ItemPersonVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).let { curItem ->
            when (curItem.viewType) {
                PersonItem.ViewType.Horizontal -> {
                    (holder as HorizontalViewHolder).bind(getItem(position))
                }
                PersonItem.ViewType.Vertical -> {
                    (holder as VerticalViewHolder).bind(getItem(position))
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    class HorizontalViewHolder(private val binding: ItemPersonHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PersonItem) {
            binding.run {
                name.text = item.name
            }
        }

    }

    class VerticalViewHolder(private val binding: ItemPersonVerticalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PersonItem) {
            binding.run {
                name.text = item.name
            }
        }

    }

}