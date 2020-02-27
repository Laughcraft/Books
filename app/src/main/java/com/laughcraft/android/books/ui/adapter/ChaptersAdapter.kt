package com.laughcraft.android.books.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laughcraft.android.books.R
import com.laughcraft.android.books.repository.entity.BookEntity

class ChaptersAdapter(var books: List<BookEntity>) : RecyclerView.Adapter<ChaptersAdapter.ChapterHolder>() {
    
    private val chapters: MutableList<List<BookEntity>> = arrayListOf()
    
    init {
        books.groupBy { it.book_1_chapter }.forEach { chapters.add(it.value) }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ChapterHolder(view)
    }
    
    override fun getItemCount(): Int = chapters.size
    
    override fun onBindViewHolder(holder: ChapterHolder, position: Int) {
        holder.bind(chapters[position][0], position)
    }
    
    inner class ChapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text_view_book_chapter)
        private val imageView: ImageView = itemView.findViewById(R.id.image_view_book_chapter)
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view_books)
        
        fun bind(book: BookEntity, position: Int) {
    
            textView.text = book.book_1_chapter
    
            recyclerView.layoutManager = LinearLayoutManager(itemView.context)
            recyclerView.adapter = BooksAdapter(chapters[position])
            
            itemView.setOnClickListener {
                if (recyclerView.visibility == View.VISIBLE) {
                    recyclerView.visibility = View.GONE
                    imageView.setImageResource(R.drawable.arrow_right_24)
                } else {
                    
                    recyclerView.visibility = View.VISIBLE
                    imageView.setImageResource(R.drawable.arrow_down_24)
                }
            }
        }
    }
}