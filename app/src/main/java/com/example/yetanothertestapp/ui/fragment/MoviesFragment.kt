package com.example.yetanothertestapp.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

import com.example.yetanothertestapp.databinding.FragmentMoviesBinding
import com.example.yetanothertestapp.extensions.provideViewModel
import com.example.yetanothertestapp.extensions.setVisible
import com.example.yetanothertestapp.model.MovieViewItem
import com.example.yetanothertestapp.ui.adapter.MoviesAdapter
import timber.log.Timber

class MoviesFragment : Fragment() {

    private val TIMER_WAITING_TIME = 1000L

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesViewModel: MoviesFragmentViewModel
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

    override fun onResume() {
        super.onResume()
        moviesViewModel.loadMovies()
    }

    private fun initViewModel() {
        moviesViewModel = provideViewModel(this, MoviesFragmentViewModel::class.java)
        moviesViewModel.movies.observe(this, Observer { moviesAdapter.update(it) })
    }

    private fun initViews() {

        moviesAdapter = MoviesAdapter{ item, isLongTap -> itemClick(item, isLongTap) }
        binding.rvMoviesList.apply {
            adapter = moviesAdapter
            layoutManager = GridLayoutManager(this.context, 2)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL))
        }

        binding.srlMoviesList.setOnRefreshListener { needUpdateList() }

//        binding.includeSearchView.ivSearchviewClose.setVisible(moviesViewModel.isSearchViewActive)
//        binding.includeEmptyQuery.ltEmptyQuery.setVisible(moviesViewModel.isEmptyQuery)
//        binding.pbMoviesList.setVisible(moviesViewModel.isInLoading)

        binding.includeSearchView.etSearview.addTextChangedListener { text: Editable? -> prceedQuery(text.toString()) }

    }

    private fun prceedQuery(text: String){
        if (timer != null) timer!!.cancel()
        timer = object : CountDownTimer(TIMER_WAITING_TIME, TIMER_WAITING_TIME / 2){
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

    private fun itemClick(item: MovieViewItem.MovieItem, longTap: Boolean) {
        if (longTap) moviesViewModel
        else Toast.makeText(this.context, item.title, Toast.LENGTH_SHORT).show()
    }

}
