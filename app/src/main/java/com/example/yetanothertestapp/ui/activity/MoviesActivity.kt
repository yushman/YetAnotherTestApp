package com.example.yetanothertestapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yetanothertestapp.R
import com.example.yetanothertestapp.databinding.ActivityMoviesBinding
import com.example.yetanothertestapp.ui.fragment.MoviesFragment

class MoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MoviesFragment())
            .commit()
    }


}
