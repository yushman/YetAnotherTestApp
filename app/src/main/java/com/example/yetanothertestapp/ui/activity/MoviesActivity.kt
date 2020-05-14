package com.example.yetanothertestapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.ActivityMoviesBinding
import com.example.yetanothertestapp.extensions.provideViewModel
import com.example.yetanothertestapp.ui.fragment.MoviesFragment
import com.example.yetanothertestapp.ui.fragment.MoviesFragmentViewModel

class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding
    private lateinit var fragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesBinding.inflate(layoutInflater)

        setContentView(binding.root)

        fragment = MoviesFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, fragment)
            .commit()
    }
}
