package com.laughcraft.android.books.ui

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.appcompat.app.AlertDialog

import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.laughcraft.android.books.R
import com.laughcraft.android.books.moxy.MainPresenter
import com.laughcraft.android.books.moxy.MainView

import com.laughcraft.android.books.repository.entity.BookEntity
import com.laughcraft.android.books.ui.adapter.ChaptersAdapter

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView<BookEntity> {
    
    @InjectPresenter
    lateinit var presenter: MainPresenter
    
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //detect first launch
        if (savedInstanceState == null){
            presenter.init()
        }
        
        recycler_view_chapters.layoutManager = LinearLayoutManager(this)
        recycler_view_chapters.adapter = ChaptersAdapter(arrayListOf())
    }
    
    override fun updateUI(books: List<BookEntity>){
        recycler_view_chapters.adapter = ChaptersAdapter(books)
    }
    
    override fun showError(throwable: Throwable){
        runOnUiThread {
            AlertDialog
                .Builder(this)
                .setTitle(R.string.error_dialog_title)
                .setMessage(throwable.message)
                .create()
                .show()
        }
    }
}
