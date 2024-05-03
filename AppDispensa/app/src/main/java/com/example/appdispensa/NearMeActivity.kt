package com.example.appdispensa

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.appdispensa.databinding.ActivityNearMeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.Executors


class NearMeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityNearMeBinding
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var lat : Double = 0.0
    private  var lng : Double = 0.0

    private companion object {
        private const val REQUEST_CODE = 101
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNearMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       getCurrentLocation()
    }

    private fun getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
        }

        var locationRequest : LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,60000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(10000)
            .build()

        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if(p0 == null){
                    Toast.makeText(applicationContext,"Current result is null",Toast.LENGTH_SHORT).show()
                }

            }

        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
        var task : Task<Location> = fusedLocationProviderClient.getLastLocation()
        task.addOnSuccessListener(object:OnSuccessListener<Location>{
            override fun onSuccess(p0: Location?) {
                if(p0!=null){
                    lat = p0.latitude
                    lng = p0.longitude
                    var latLng : LatLng = LatLng(lat,lng)
                    mMap.addMarker(MarkerOptions().position(latLng).title("Sei quì"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13F))
                    var stringBuilder : StringBuilder = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
                    stringBuilder.append("location=" + lat + "," + lng)
                    stringBuilder.append("&radius=3000")
                    stringBuilder.append("&type=supermarket")
                    stringBuilder.append("&sensor=true")
                    stringBuilder.append("&key=MY_API_KEY")

                    var url:String = stringBuilder.toString()



                    var fetchdata : FetchData = FetchData()

                    fetchdata.AsyncCall(mMap,url)

                }
            }
        })
    }


    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(REQUEST_CODE){
            REQUEST_CODE -> if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }
        }
    }
}