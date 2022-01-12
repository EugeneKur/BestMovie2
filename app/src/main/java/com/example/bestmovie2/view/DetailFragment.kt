package com.example.bestmovie2.view

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import coil.load
import coil.transform.GrayscaleTransformation
import com.example.bestmovie2.R
import com.example.bestmovie2.databinding.DetailFragmentBinding
import com.example.bestmovie2.viewmodel.MainViewModel
import com.example.bestmovie2.databinding.MainFragmentBinding
import com.example.bestmovie2.model.*
import com.example.bestmovie2.viewmodel.AppState
import com.example.bestmovie2.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle?) : DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    private val listener = Repository.OnLoadListener {

        RepositoryImpl.getMovieFromServer()?.let { movie ->
            binding.titleMovie.text = movie.title.name
            binding.yearMovie.text = movie.title.year
            binding.ratingMovie.text = movie.title.rating.toString()
            binding.aboutMovie.text = movie.about
            movie.note = binding.noteMovie.text.toString()

            binding.imageMovie.load("https://image.tmdb.org/t/p/w500${movie.logo}") {
                crossfade(true)
                transformations(GrayscaleTransformation())
            }

            viewModel.saveHistory(movie)

        } ?: Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
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

        RepositoryImpl.addLoadedListener(listener)


        arguments?.getParcelable<Movie>("MOVIE_EXTRA")?.let { movie ->
            view.findViewById<ImageView>(R.id.image_movie).setImageResource(movie.title.image)

            binding.titleMovie.text = movie.title.name

            requireActivity().startService(Intent(requireContext(), MainIntentService::class.java).apply {
                putExtra("MOVIE_EXTRA", movie)
            })

            /*
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
            */


            /*
            binding.titleMovie.text = movie.title.name
            binding.yearMovie.text = movie.title.year
            binding.ratingMovie.text = movie.title.rating.toString()
            binding.aboutMovie.text = movie.about
            */
        }




        /*
        binding.imageMovie.imageAlpha= movie?.title?.image!! - не работает
         */
    }


    override fun onDestroyView() {
        super.onDestroyView()
        RepositoryImpl.removeLoadedListener(listener)
        _binding = null
    }

}