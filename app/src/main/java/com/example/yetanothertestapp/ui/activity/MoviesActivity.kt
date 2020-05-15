package com.example.yetanothertestapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.ActivityMoviesBinding
import com.example.yetanothertestapp.ui.fragment.MoviesFragment

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
