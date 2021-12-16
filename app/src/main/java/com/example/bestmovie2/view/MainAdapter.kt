package com.example.bestmovie2.view

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bestmovie2.R
import com.example.bestmovie2.model.Movie

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var movie: List<Movie> = listOf()
    var listener: OnItemClick? = null

    fun setMovie (data: List<Movie>) {
        movie = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movie[position])
    }

    override fun getItemCount(): Int = movie.size

    inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            itemView.findViewById<TextView>(R.id.main_title_name_view).text = movie.title.name
            itemView.findViewById<ImageView>(R.id.main_item_image).setImageResource(movie.title.image)
            itemView.setOnClickListener {
                listener?.onClick(movie)
            }
        }

    }

    fun interface OnItemClick {
        fun onClick (movie: Movie)
    }

}
