package com.Evelyndamayantiambarita.challengechapter5.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.Evelyndamayantiambarita.challengechapter5.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val value = intent.extras!!.getString("my_key")

        viewModel.getMovieDetail(value.toString())
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.shouldShowMovieDetail.observe(this) {
            binding.tvTitle.text = it.title
            binding.tvOverview.text = it.overview
            Glide.with(applicationContext)
                .load(it.image)
                .into(binding.ivCover)
        }
    }
}