package com.example.yetanothertestapp.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.FragmentMoviesBinding
import com.example.yetanothertestapp.extensions.gone
import com.example.yetanothertestapp.extensions.hideKeyboard
import com.example.yetanothertestapp.extensions.visible
import com.example.yetanothertestapp.model.MovieViewItem
import com.example.yetanothertestapp.ui.adapter.MoviesAdapter
import com.example.yetanothertestapp.ui.custom.FooterGridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MoviesFragment : Fragment() {

    private val TIMER_WAITING_TIME = 1000L

    private val moviesViewModel: MoviesFragmentViewModel by viewModel()
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        initViewModel()
        initViews()

        return binding.root
    }

    private fun initViewModel() {
        moviesViewModel.state.observe(this.viewLifecycleOwner, Observer { updateUiByState(it) })
    }

    private fun initViews() {

        moviesAdapter = MoviesAdapter{ item, isLongTap -> itemClick(item, isLongTap) }
        val lm = FooterGridLayoutManager(this.context!!, 2, moviesAdapter)
        binding.rvMoviesList.apply {
            adapter = moviesAdapter
            layoutManager = lm
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (lm.findLastVisibleItemPosition() >= moviesAdapter.itemCount - 1)
                        moviesViewModel.loadNext()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

        binding.srlMoviesList.setOnRefreshListener { needUpdateList() }

        binding.includeSearchView.etSearchview.addTextChangedListener { text: Editable? ->
            prceedQuery(
                text.toString()
            )
        }

        binding.includeSearchView.ivSearchviewClose.setOnClickListener { ivSearchviewCloseClick() }
    }

    private fun ivSearchviewCloseClick() {
        binding.includeSearchView.etSearchview.setText("")
        binding.includeSearchView.etSearchview.hideKeyboard(this.context!!)
        binding.includeSearchView.ivSearchviewClose.gone()
        binding.rvMoviesList.visible()
        binding.includeEmptyQuery.ltEmptyQuery.gone()
        moviesViewModel.updateList()
    }

    private fun updateUiByState(state: MoviesFragmentViewModel.State) {
        binding.srlMoviesList.isRefreshing = false
        Timber.i(state.toString())
        when (state) {
            is MoviesFragmentViewModel.State.LoadingState -> showLoadingState()
            is MoviesFragmentViewModel.State.ErrorState -> showErrorState(state.message)
            is MoviesFragmentViewModel.State.NoItemState -> showNoItemState()
            is MoviesFragmentViewModel.State.LoadedState -> showLoadedState(state.data)
        }
    }

    private fun showLoadingState() {
        binding.pbMoviesList.visible()
    }

    private fun showErrorState(message: String) {
        binding.pbMoviesList.gone()
        binding.includeEmptyQuery.ltEmptyQuery.gone()
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNoItemState() {
        binding.rvMoviesList.gone()
        binding.pbMoviesList.gone()
        binding.includeEmptyQuery.ltEmptyQuery.visible()
        val q = binding.includeSearchView.etSearchview.text.toString()
        binding.includeEmptyQuery.tvEmptyQuery.text =
            String.format(resources.getString(R.string.empty_query_top_text), q)
    }

    private fun showLoadedState(data: List<MovieViewItem>) {
        moviesAdapter.update(data)
        binding.pbMoviesList.gone()
        binding.includeEmptyQuery.ltEmptyQuery.gone()
    }

    private fun prceedQuery(text: String){
        if (text.isNotEmpty()) {
            binding.includeSearchView.ivSearchviewClose.visible()
            if (timer != null) timer!!.cancel()
            timer = object : CountDownTimer(TIMER_WAITING_TIME, TIMER_WAITING_TIME) {
                override fun onFinish() {
                    moviesViewModel.proceedQuery(text)
                }

                override fun onTick(p0: Long) {
//                moviesViewModel.proceedQuery(text)
                }
            }.start()
        }
    }

    private fun needUpdateList() {
        moviesViewModel.updateList()
        Timber.i("Need Update")
    }

    private fun itemClick(item: MovieViewItem, longTap: Boolean) {
        when (item) {
            is MovieViewItem.FooterLoadingError -> moviesViewModel.reloadPage()
            is MovieViewItem.MovieItem ->
                if (longTap) toggleFavorite(item)
                else showMovieInfoBuble(item)
        }
    }

    private fun showMovieInfoBuble(item: MovieViewItem.MovieItem) {
        Toast.makeText(
            this.context,
            String.format(resources.getString(R.string.movie_short_tap_toast), item.title),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun toggleFavorite(item: MovieViewItem.MovieItem) {
        if (item.isFavorite) moviesViewModel.removeFavorite(item)
        else moviesViewModel.addFavorite(item)
        moviesAdapter.togleItemIsFavorite(item)
        showMovieChangeStateBuble(item)
    }

    private fun showMovieChangeStateBuble(item: MovieViewItem.MovieItem) {
        if (item.isFavorite) Toast.makeText(
            this.context,
            String.format(resources.getString(R.string.movie_favorite_remove_toast), item.title),
            Toast.LENGTH_SHORT
        ).show()
        else Toast.makeText(
            this.context,
            String.format(resources.getString(R.string.movie_favorite_add_toast), item.title),
            Toast.LENGTH_SHORT
        ).show()
    }

}
