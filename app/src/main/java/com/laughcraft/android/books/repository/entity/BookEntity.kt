package com.laughcraft.android.books.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "books")
data class BookEntity(@PrimaryKey(autoGenerate = true)
                      @Expose var id: Int,
                      var book_1_chapter: String?,
                      var book_1_type: String?,
                      var link: String?,
                      var book_1_title: String?,
                      var book_1_url: String?,
                      var book_1_snippet: String?)