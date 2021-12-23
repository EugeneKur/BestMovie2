package com.example.bestmovie2.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.bestmovie2.R
import com.example.bestmovie2.databinding.DetailFragmentBinding
import com.example.bestmovie2.viewmodel.MainViewModel
import com.example.bestmovie2.databinding.MainFragmentBinding
import com.example.bestmovie2.model.Movie
import com.example.bestmovie2.model.MovieDTO
import com.example.bestmovie2.model.MovieLoader
import com.example.bestmovie2.viewmodel.AppState
import com.example.bestmovie2.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?) : DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.getParcelable<Movie>("MOVIE_EXTRA")?.let { movie ->
            view.findViewById<ImageView>(R.id.image_movie).setImageResource(movie.title.image)

            binding.titleMovie.text = movie.title.name

            MovieLoader.load(movie, object : MovieLoader.OnMovieLoadListener{
                override fun onLoaded(movieDTO: MovieDTO) {

                    binding.yearMovie.text = movieDTO.release_date?.toString()
                    binding.ratingMovie.text = movieDTO.vote_average?.toString()
                    binding.aboutMovie.text = movieDTO.about?.toString()

                }

                override fun onFailed(throwable: Throwable) {
                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
                }

            })


            /*binding.titleMovie.text = movie.title.name
            binding.yearMovie.text = movie.title.year
            binding.ratingMovie.text = movie.title.rating.toString()
            binding.aboutMovie.text = movie.about*/
        }


        /*
        binding.imageMovie.imageAlpha= movie?.title?.image!! - не работает
         */
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}