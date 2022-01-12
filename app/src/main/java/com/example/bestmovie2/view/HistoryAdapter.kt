package com.example.bestmovie2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.bestmovie2.R
import com.example.bestmovie2.model.Movie
import org.w3c.dom.Text

class HistoryAdapter(private var items: List<Movie>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {

        val movie = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.history_title_name).text = movie.title.name
            findViewById<TextView>(R.id.history_about).text = movie.about
            findViewById<TextView>(R.id.history_rating).text = movie.title.rating.toString()
            findViewById<TextView>(R.id.history_year).text = movie.title.year
        }
    }

    override fun getItemCount(): Int =items.size

    class HistoryItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

}