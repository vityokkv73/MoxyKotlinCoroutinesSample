package com.example.deerhunter.coroutinessample.ui.movie.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.movie.view.IMoviesView
import com.example.deerhunter.coroutinessample.ui.movie.view.items.MovieItem
import com.example.deerhunter.coroutinessample.ui.utilities.Paginator
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

@InjectViewState
class MoviesPresenter(private val interactor: ApiInteractor) : MvpPresenter<IMoviesView>() {

    private val paginator: Paginator = Paginator { offset -> loadMovies(offset) }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovies()
    }

    fun loadMore() {
        paginator.next()
    }

    private fun loadMovies(page: Int = 1) = async(UI) {
        try {
            viewState.showProgress()
            val (pageNumber, _, totalPages, movies) = bg { interactor.loadMovies(page) }.await()
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