package com.example.android_recyclerviewsyncedwithtabs_example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_recyclerviewsyncedwithtabs_example.databinding.ItemPersonBinding

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
        return PersonViewHolder(
                binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PersonViewHolder).bind(getItem(position))
    }

    class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PersonItem) {
            binding.run {
                name.text = item.name
            }
        }

    }

}