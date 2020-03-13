package com.deepak.openweather.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deepak.openweather.BuildConfig
import com.deepak.openweather.R
import com.deepak.openweather.data.WeatherRepository
import com.deepak.openweather.data.local.dataBase
import com.deepak.openweather.databinding.ActivityMainBinding
import com.deepak.openweather.isConnected
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private val PERMISSION_ID = 42
    private val TAG = "MainActivity"
    lateinit var weatherRepository: WeatherRepository
    lateinit var binding: ActivityMainBinding


    private val viewModel: WeatherViewModel by lazy {
        val activity = requireNotNull(this)
        ViewModelProvider(this, Factory(weatherRepository, activity.application))
            .get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherRepository = WeatherRepository(dataBase(this), this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (isConnected) {
            if (checkPermissions() && isLocationEnabled()) {
                binding.weatherViewModel = viewModel
                binding.lifecycleOwner = this
            } else {
                requestPermission()
            }
        } else {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {

        val shouldProvideRationale = (ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) &&
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ))

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(binding.root, R.string.permission_rationale, Snackbar.LENGTH_SHORT)
                .setAction(android.R.string.ok) {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ), PERMISSION_ID
                    )
                }
        } else {
            Log.i(TAG, "Requesting permission")
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), PERMISSION_ID
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == PERMISSION_ID) {

            when {
                grantResults.isEmpty() -> Log.i(TAG, "User interaction was cancelled.")

                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    Log.i(TAG, "Permission granted")

                    //Starting viewmodel as first time after getting permission
                    binding.weatherViewModel = viewModel
                    binding.lifecycleOwner = this
                }

                else -> {
                    Snackbar.make(
                        binding.root,
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    Intent().apply {
                        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        data = (Uri.fromParts("package", BuildConfig.APPLICATION_ID, null))
                        flags = (Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(this)
                    }
                }
            }
        }
    }
}

