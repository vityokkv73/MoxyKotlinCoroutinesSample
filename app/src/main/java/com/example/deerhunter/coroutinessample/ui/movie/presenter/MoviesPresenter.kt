package com.example.deerhunter.coroutinessample.ui.movie.presenter

import co.metalab.asyncawait.async
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.interactors.ApiInteractor
import com.example.deerhunter.coroutinessample.ui.movie.view.IMoviesView


@InjectViewState
class MoviesPresenter(private val interactor: ApiInteractor) : MvpPresenter<IMoviesView>() {

    fun loadMovies() = async {
        try {
            viewState.showProgress()
            viewState.addMovies(await { interactor.loadMovies().results })
        } catch (ex: Exception) {
            viewState.showError(ex.message ?: "exception")
        } finally {
            viewState.hideProgress()
        }
    }

    fun movieClicked(movie: Movie) {
        viewState.openMovieScreen(movie)
    }

//    fun loadNextRepositories(currentCount: Int) {
//        val page = currentCount / GithubApi.PAGE_SIZE + 1
//
//        loadData(page, true, false)
//    }
//
//    fun loadMovies(isRefreshing: Boolean) {
//        loadData(1, false, isRefreshing)
//    }
//
//    private fun loadData(page: Int, isPageLoading: Boolean, isRefreshing: Boolean) {
//        if (mIsInLoading) {
//            return
//        }
//        mIsInLoading = true
//
//        getViewState().onStartLoading()
//
//        showProgress(isPageLoading, isRefreshing)
//
//        val observable = mGithubService!!.getUserRepos("JakeWharton", page, GithubApi.PAGE_SIZE)
//
//        val subscription = observable
//                .compose(Utils.applySchedulers())
//                .subscribe({ repositories ->
//                    onLoadingFinish(isPageLoading, isRefreshing)
//                    onLoadingSuccess(isPageLoading, repositories)
//                }, { error ->
//                    onLoadingFinish(isPageLoading, isRefreshing)
//                    onLoadingFailed(error)
//                })
//        unsubscribeOnDestroy(subscription)
//    }
//
//    private fun onLoadingFinish(isPageLoading: Boolean, isRefreshing: Boolean) {
//        mIsInLoading = false
//
//        getViewState().onFinishLoading()
//
//        hideProgress(isPageLoading, isRefreshing)
//    }
//
//    private fun onLoadingSuccess(isPageLoading: Boolean, repositories: List<Repository>) {
//        val maybeMore = repositories.size >= GithubApi.PAGE_SIZE
//        if (isPageLoading) {
//            getViewState().addRepositories(repositories, maybeMore)
//        } else {
//            getViewState().setRepositories(repositories, maybeMore)
//        }
//    }
//
//    private fun onLoadingFailed(error: Throwable) {
//        getViewState().showError(error.toString())
//    }
//
//    private fun showProgress(isPageLoading: Boolean, isRefreshing: Boolean) {
//        if (isPageLoading) {
//            return
//        }
//
//        if (isRefreshing) {
//            getViewState().showRefreshing()
//        } else {
//            getViewState().showListProgress()
//        }
//    }
//
//    private fun hideProgress(isPageLoading: Boolean, isRefreshing: Boolean) {
//        if (isPageLoading) {
//            return
//        }
//
//        if (isRefreshing) {
//            getViewState().hideRefreshing()
//        } else {
//            getViewState().hideListProgress()
//        }
//    }
//
//    fun onErrorCancel() {
//        getViewState().hideError()
//    }
}