package com.example.deerhunter.coroutinessample.ui.movie.presenter

import com.arellomobile.mvp.InjectViewState
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.common.ScopedMvpPresenter
import com.example.deerhunter.coroutinessample.ui.movie.view.IMoviesView
import com.example.deerhunter.coroutinessample.ui.movie.view.items.MovieItem
import com.example.deerhunter.coroutinessample.ui.utilities.Paginator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@InjectViewState
class MoviesPresenter(private val interactor: ApiInteractor) : ScopedMvpPresenter<IMoviesView>() {

    private val paginator: Paginator = Paginator { offset -> launch { loadMovies(offset) } }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch { loadMovies() }
    }

    fun loadMore() {
        paginator.next()
    }

    private suspend fun loadMovies(page: Int = 1) = coroutineScope {
        try {
            viewState.showProgress()
            val (pageNumber, _, totalPages, movies) = interactor.loadMovies(page).await()
            paginator.totalPagesNumber = totalPages
            paginator.received(pageNumber)
            viewState.addMovies(movies.map { MovieItem(it) })
        } catch (ex: Throwable) {
            viewState.showError(ex.message ?: "exception")
        }
    }

    fun movieClicked(movie: Movie) {
        viewState.openMovieScreen(movie)
    }
}