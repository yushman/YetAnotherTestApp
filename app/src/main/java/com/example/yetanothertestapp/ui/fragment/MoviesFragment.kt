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
import com.example.yetanothertestapp.databinding.FragmentMoviesBinding
import com.example.yetanothertestapp.extensions.gone
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
//        moviesViewModel =
//            ViewModelProvider(this, viewModelFactory).get(MoviesFragmentViewModel::class.java)
//            provideViewModel(this, MoviesFragmentViewModel::class.java)
        moviesViewModel.state.observe(this.viewLifecycleOwner, Observer { updateUiByState(it) })
    }

    private fun initViews() {

        moviesAdapter = MoviesAdapter{ item, isLongTap -> itemClick(item, isLongTap) }
        val lm = FooterGridLayoutManager(this.context!!, 2, moviesAdapter)
        binding.rvMoviesList.apply {
            adapter = moviesAdapter
            layoutManager = lm
//            isNestedScrollingEnabled = false
            setHasFixedSize(true)
//            lm.isAutoMeasureEnabled = true
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val lastVisible = lm.findLastVisibleItemPosition()
                    Timber.i(lastVisible.toString())
//                    if (lm.findLastVisibleItemPosition() >= moviesAdapter.itemCount - 1)
//                        moviesViewModel.loadNext()
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
        binding.pbMoviesList.gone()
        binding.includeEmptyQuery.ltEmptyQuery.visible()
    }

    private fun showLoadedState(data: List<MovieViewItem>) {
        moviesAdapter.update(data)
        binding.pbMoviesList.gone()
        binding.includeEmptyQuery.ltEmptyQuery.gone()
    }

    private fun prceedQuery(text: String){
        if (timer != null) timer!!.cancel()
        timer = object : CountDownTimer(TIMER_WAITING_TIME, TIMER_WAITING_TIME) {
            override fun onFinish() {
                moviesViewModel.proceedQuery(text)
            }

            override fun onTick(p0: Long) {
                //DO NOTHING
            }
        }
    }

    private fun needUpdateList() {
        moviesViewModel.updateList()
        Timber.i("Need Update")
    }

    private fun itemClick(item: MovieViewItem, longTap: Boolean) {
        when (item) {
            is MovieViewItem.FooterLoadingError -> moviesViewModel.reloadPage()
            is MovieViewItem.MovieItem -> toggleFavorite(item)
        }
    }

    private fun toggleFavorite(item: MovieViewItem.MovieItem) {
        if (item.isFavorite) moviesViewModel.removeFavorite(item)
        else moviesViewModel.addFavorite(item)
    }

}
