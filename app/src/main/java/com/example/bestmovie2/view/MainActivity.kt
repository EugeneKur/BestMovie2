package com.example.bestmovie2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bestmovie2.databinding.MainActivityBinding
import com.example.bestmovie2.databinding.MainFragmentBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}