package com.laughcraft.android.books.moxy

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.laughcraft.android.books.repository.Repository
import com.laughcraft.android.books.repository.entity.BookEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@InjectViewState
class MainPresenter : MvpPresenter<MainView<BookEntity>>() {
    private var disposable: Disposable? = null
    
    fun init() {
        val repo = Repository.of<BookEntity>()
        repo.onError = { showError(it) }
        
        disposable =
            repo.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ updateUI(it) }, { it.printStackTrace() })
    }
    
    fun showError(throwable: Throwable) {
        viewState.showError(throwable)
    }
    
    fun updateUI(books: List<BookEntity>) {
        viewState.updateUI(books)
    }
}