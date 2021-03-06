package com.example.bestmovie2.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bestmovie2.viewmodel.MainViewModel
import com.example.bestmovie2.databinding.MainFragmentBinding
import com.example.bestmovie2.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })

        // Запросили новые данные
        viewModel.getWeather()


    }

    private fun render(state: AppState) {

        when (state) {
            is AppState.Success -> {
                binding.loadingContainer.visibility = View.GONE
                binding.titleMovie.text = state.movie.title
                binding.ratingMovie.text = state.movie.rating.toString()
                binding.aboutMovie.text = state.movie.about
                binding.yearMovie.text = state.movie.year
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Попробовать снова") {
                        // Запросили новые данные
                        viewModel.getWeather()
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