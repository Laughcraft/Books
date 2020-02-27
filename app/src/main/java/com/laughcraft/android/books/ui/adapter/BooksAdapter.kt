package com.laughcraft.android.books.ui.adapter

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laughcraft.android.books.R
import com.laughcraft.android.books.repository.entity.BookEntity

class BooksAdapter(val books: List<BookEntity>): RecyclerView.Adapter<BooksAdapter.BookHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_list_item, parent, false))
    }
    
    override fun getItemCount(): Int {
        return books.size
    }
    
    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(books[position])
    }
    
    class BookHolder(view: View): RecyclerView.ViewHolder(view){
        private val bookNameTextView: TextView = itemView.findViewById(R.id.text_view_book_title)
        private val bookSnippetTextView: TextView = itemView.findViewById(R.id.text_view_book_snippet)
        
        fun bind(book: BookEntity){
            bookNameTextView.text = book.book_1_title
            bookSnippetTextView.text = book.book_1_snippet
            
            itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.book_1_url))
                
                val activities: List<ResolveInfo> =
                    itemView
                        .context
                        .packageManager
                        .queryIntentActivities(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
                
                if (activities.isNotEmpty()){
                    itemView.context.startActivity(browserIntent)
                }
            }
        }
    }
}