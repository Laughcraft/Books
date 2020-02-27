package com.laughcraft.android.books.repository.entity

data class ChaptersRequest(val status: String?,
                           val boolean: String?,
                           val data: List<BookEntity>?,
                           val errors: ChaptersRequest?,
                           val detail: String?)