package com.Evelyndamayantiambarita.challengechapter5.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.Evelyndamayantiambarita.challengechapter5.databinding.ListItemMoviePopulerBinding
import com.Evelyndamayantiambarita.challengechapter5.model.movie.MoviePopulerModel
import java.util.*

class MoviePopulerAdapter(private val listener: EventListener, private var list: List<MoviePopulerModel>) :
    RecyclerView.Adapter<MoviePopulerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ListItemMoviePopulerBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun updateList(list: List<MoviePopulerModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemMoviePopulerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        Glide.with(holder.binding.root.context)
            .load(item.image)
            .into(holder.binding.ivPoster)
        holder.binding.tvTitleMovie.text = item.title

        holder.binding.rating.rating = (item.vote_average ?: 0.0).toFloat()
        holder.binding.rating.stepSize = (0.5).toFloat()
        holder.binding.rating.max = 5
        holder.binding.rating.numStars = 5

        holder.itemView.setOnClickListener {
            listener.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onClick(item: MoviePopulerModel)
    }

}