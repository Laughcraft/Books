package com.laughcraft.android.books.moxy

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView<Entity>: MvpView {
    fun showError(throwable: Throwable)
    
    fun updateUI(books: List<Entity>)
}