package com.example.deerhunter.coroutinessample.ui.movie.view

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.deerhunter.coroutinessample.R
import com.example.deerhunter.coroutinessample.data.Movie
import com.example.deerhunter.coroutinessample.di.movie.MovieModule
import com.example.deerhunter.coroutinessample.ui.movie.presenter.MoviesPresenter
import com.example.deerhunter.coroutinessample.ui.movie.view.adapter.MoviesAdapter
import com.example.deerhunter.coroutinessample.ui.movie.view.items.MovieItem
import com.example.deerhunter.coroutinessample.ui.utilities.GridSpaceItemDecoration
import com.example.deerhunter.coroutinessample.ui.utilities.OnPageScrollListener
import com.example.deerhunter.coroutinessample.ui.utilities.UiCalculator
import com.example.deerhunter.coroutinessample.utilities.getActivityComponent
import kotlinx.android.synthetic.main.movies_fragment.*
import javax.inject.Inject

class MoviesFragment : MvpAppCompatFragment(), IMoviesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MoviesPresenter

    @ProvidePresenter
    fun providePresenter(): MoviesPresenter {
        return presenter
    }

    @Inject
    lateinit var rowLayoutData: UiCalculator.RowLayoutData

    private lateinit var moviesAdapter: MoviesAdapter
    private val scrollListener by lazy { OnPageScrollListener({ presenter.loadMore() }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent().plus(MovieModule()).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val resources = context.resources
        val verticalSpacing = resources.getDimensionPixelSize(R.dimen.vertical_space_between_cards)
        val horizontalSpacing = resources.getDimensionPixelSize(R.dimen.horizontal_space_between_cards)
        val spanCount = rowLayoutData.columnsCount

        moviesAdapter = MoviesAdapter(rowLayoutData, { presenter.movieClicked(it) }, { _, _ -> presenter.loadMore() })
        with(moviesList) {
            setPadding(rowLayoutData.horizontalPadding, paddingTop, rowLayoutData.horizontalPadding, paddingBottom)
            adapter = moviesAdapter
            addOnScrollListener(scrollListener)
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(activity, spanCount, GridLayoutManager.VERTICAL, false).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    // TODO: we should use adapter delegates to find correct itemViewType
                    override fun getSpanSize(position: Int) = when (adapter.getItemViewType(position)) {
                        0 -> 1
                        else -> getSpanCount()
                    }
                }
            }
            addItemDecoration(GridSpaceItemDecoration(spanCount, horizontalSpacing, verticalSpacing, false, GridLayoutManager.VERTICAL))
        }
    }

    override fun addMovies(movies: List<MovieItem>) {
        moviesAdapter.removeErrorAndProgress()
        moviesAdapter.add(movies)
    }

    override fun showProgress() {
        moviesAdapter.progress()
    }

    override fun showError(errorMessage: CharSequence?) {
        moviesAdapter.error(errorAdditionalMessage = errorMessage)
    }

    override fun openMovieScreen(movie: Movie) {
        activity.supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, MovieFragment.newInstance(movie)).addToBackStack(null).commit()
    }
}