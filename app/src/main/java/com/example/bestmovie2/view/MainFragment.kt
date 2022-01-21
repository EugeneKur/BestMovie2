package com.example.bestmovie2.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestmovie2.R
import com.example.bestmovie2.viewmodel.MainViewModel
import com.example.bestmovie2.databinding.MainFragmentBinding
import com.example.bestmovie2.model.Movie
import com.example.bestmovie2.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import java.io.IOException
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }




    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()
    private var isRussian = true

    private val viewModel: MainViewModel by  lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val fineLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_FINE_LOCATION, false
            )
            val coarseLocationGranted = result.getOrDefault(
                Manifest.permission.ACCESS_COARSE_LOCATION, false
            )

            when {
                fineLocationGranted or coarseLocationGranted -> showLocation()
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) -> AlertDialog.Builder(requireActivity()).setTitle("Дай доступ")
                    .setMessage("Ну очень надо")
                    .setPositiveButton("Дать доступ") { _, _ -> requestPermission() }
                    .setNegativeButton("Не давать доступ") { dialog, _ -> dialog.dismiss() }
                    .create()
                    .show()
                else -> requestPermission()
            }
        }

    @SuppressLint("MissingPermission")
    private fun showLocation() {

        requireActivity().startActivity(Intent(requireContext(), MapsActivity::class.java))

//        val locationManager =
//            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            val providerGps = locationManager.getProvider(LocationManager.GPS_PROVIDER)
//
//            providerGps?.let {
//                locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    60_000L,
//                    100F,
//                    object: LocationListener {
//                        override fun onLocationChanged(location: Location) {
//                            getAddressByLocation(location)
//                        }
//                        override fun onStatusChanged(
//                            provider: String?,
//                            status: Int,
//                            extras: Bundle?
//                        ) {
//                            // do noting
//                        }
//
//                        override fun onProviderEnabled(provider: String) {
//
//                        }
//
//                        override fun onProviderDisabled(provider: String) {
//
//                        }
//                    }
//                )
//            } ?: locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
//                getAddressByLocation(location)
//            }
//        } else {
//            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
//                getAddressByLocation(location)
//            }
//        }
    }


    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(requireActivity())
        Thread {
            try {
                val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                requireActivity().runOnUiThread {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Я тут был ${Date().time  - location.time} назад")
                        .setMessage(address[0].getAddressLine(0))
                        .show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun requestPermission() {
        permissionResult.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            isRussian = sharedPreferences.getBoolean("key2", false)
        }

        binding.mainRecyclerView.adapter = adapter

        adapter.listener = MainAdapter.OnItemClick { movie ->

            val bundle = Bundle()
            bundle.putParcelable("MOVIE_EXTRA", movie)

            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }

        }

        // Подписались на изменения liveData
        viewModel.getData().observe(viewLifecycleOwner, { state ->
            render(state)
        })

        // Запросили новые данные
        if (isRussian) {
            viewModel.getMovieFromLocalStorageRus()
        } else viewModel.getMovieFromLocalStorageWorld()

        binding.mainFAB.setOnClickListener {

            if (isRussian) {
                viewModel.getMovieFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_flag_24)
                val editor = sharedPreferences?.edit()
                editor?.putBoolean("key2", !isRussian)
                editor?.apply()
                isRussian = !isRussian
            } else {
                viewModel.getMovieFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
                val editor = sharedPreferences?.edit()
                editor?.putBoolean("key2", !isRussian)
                editor?.apply()
                isRussian = !isRussian
            }
        }

        binding.locationFAB.setOnClickListener {
            requestPermission()
        }
    }

    private fun render(state: AppState) {

        when (state) {
            is AppState.Success<*> -> {

                val movie: List<Movie> = state.data as List<Movie>
                adapter.setMovie(movie)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(),"Попробовать снова",
                    {
                        // Запросили новые данные
                        viewModel.getMovieFromLocalStorageRus()
                    })
            }
            is AppState.Loading ->
                binding.loadingContainer.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}