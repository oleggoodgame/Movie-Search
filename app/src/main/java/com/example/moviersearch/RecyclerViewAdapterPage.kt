package com.example.moviersearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.moviersearch.databinding.PageItemBinding

class RecyclerViewAdapterPage(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapterPage.RecyclerHolder>() {

    private val items: MutableList<String> = mutableListOf()
    private var selectedPage: Int = -1
    class RecyclerHolder(item: View, val onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(item) {
        private val binding = PageItemBinding.bind(item)

        fun bind(page: String, isSelected: Boolean) = with(binding) {
            textViewPageNumber.text = page
            itemView.background = if (isSelected) {
                ContextCompat.getDrawable(itemView.context, R.drawable.drawable_page_selected)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.drawable_page)
            }

            itemView.setOnClickListener {
                onItemClick(page)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.page_item, parent, false)
        return RecyclerHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        val page = items[position]
        holder.bind(page, position == selectedPage)
    }

    fun addItems(newItems: List<String>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    fun setSelectedPage(page: Int) {
        selectedPage = page-1
        notifyDataSetChanged()
    }
    fun moveToNextPage() {
        if (selectedPage >= 0 && selectedPage < items.size - 1) {
            selectedPage++  // Переміщуємо на наступну сторінку
            notifyDataSetChanged()
        }
    }
    fun clearItems(){
        items.clear()
        notifyDataSetChanged()
    }
}
