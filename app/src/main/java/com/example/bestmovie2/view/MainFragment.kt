package com.example.bestmovie2.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestmovie2.R
import com.example.bestmovie2.viewmodel.MainViewModel
import com.example.bestmovie2.databinding.MainFragmentBinding
import com.example.bestmovie2.model.Movie
import com.example.bestmovie2.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var isRussian = true

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecyclerView.adapter = adapter



        adapter.listener = MainAdapter.OnItemClick { movie ->

            val bundle = Bundle()
            bundle.putParcelable("MOVIE_EXTRA", movie)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })

        // Запросили новые данные
        viewModel.getMovieFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian

            if (isRussian) {
                viewModel.getMovieFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
            } else {
                viewModel.getMovieFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_flag_24)
            }

        }


    }

    private fun render(state: AppState) {

        when (state) {
            is AppState.Success<*> -> {

                val movie: List<Movie> = state.data as List<Movie>
                adapter.setMovie(movie)
                binding.loadingContainer.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробовать снова") {
                        // Запросили новые данные
                        viewModel.getMovieFromLocalStorageRus()
                    }
                    .show()
            }
            is AppState.Loading ->
                binding.loadingContainer.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}