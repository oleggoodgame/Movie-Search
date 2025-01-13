package com.example.moviersearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviersearch.databinding.CardItemBinding

class RecyclerViewAdapter(private val onItemClick: (MovieData) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder>() {

    private val items: MutableList<MovieData> = mutableListOf()
    // 2
    class RecyclerHolder(item: View, val onItemClick: (MovieData) -> Unit) : RecyclerView.ViewHolder(item) {
        val binding = CardItemBinding.bind(item)

        fun bind(cardItem: MovieData) = with(binding) {
            itemTitle.text = cardItem.Title
            itemYear.text = cardItem.Year
            itemType.text = cardItem.Type
            if(cardItem.Poster == "N/A"){
                image.setImageResource(R.drawable.none)
            }else{
                Glide.with(itemView.context)
                    .load(cardItem.Poster)
                    .into(binding.image)
            }
            itemView.setOnClickListener {
                onItemClick(cardItem)
            }
        }
    }
    // 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return RecyclerHolder(view, onItemClick)
    }
    //3
    override fun getItemCount(): Int {
        return items.size
    }
    // 4
    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItem(item: MovieData) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(newItems: List<MovieData>) {
        val startPos = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPos, newItems.size)
    }
    fun clearItems(){
        items.clear()
        notifyDataSetChanged()
    }
}