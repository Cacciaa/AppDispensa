package com.example.appdispensa

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.appdispensa.databinding.ActivityNearMeBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class NearMeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityNearMeBinding
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private var lat : Double = 0.0
    private  var lng : Double = 0.0

    private var allMarker : MutableList<Marker> = ArrayList()

    private lateinit  var  locationRequest : LocationRequest

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
        mMap.uiSettings.isMyLocationButtonEnabled = true
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return;
        }
        mMap.isMyLocationEnabled = true

        CheckGps()
        getCurrentLocation()
        mMap.setOnMyLocationButtonClickListener(object : OnMyLocationButtonClickListener{
            override fun onMyLocationButtonClick(): Boolean {
                CheckGps()
                getCurrentLocation()
                return true
            }

        })


    }



    private fun CheckGps() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000).build()

        var builder : LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).setAlwaysShow(true)
        var locationSettingsResponseTask : Task<LocationSettingsResponse> = LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())


        locationSettingsResponseTask.addOnCompleteListener(object : OnCompleteListener<LocationSettingsResponse>{
            override fun onComplete(@NonNull p0: Task<LocationSettingsResponse>) {
                try{
                    var response : LocationSettingsResponse? =  p0.getResult(ApiException::class.java)
                    getCurrentLocation()


                }
                catch(e:ApiException){
                    if(e.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                        var res: ResolvableApiException = (e) as ResolvableApiException
                        try{
                            res.startResolutionForResult(this@NearMeActivity, 101)
                        }catch (send : IntentSender.SendIntentException){
                            send.printStackTrace()
                        }
                    }

                    if(e.statusCode == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE){
                        Toast.makeText(applicationContext,"Setting not available",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 ){
           if(resultCode == RESULT_OK){
               Log.d("nearme","GPS Enable")
           }
            if(resultCode == RESULT_CANCELED){
                Log.d("nearme","GPS denied to enable")
            }
        }
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
                    removeAllMarker()
                    lat = p0.latitude
                    lng = p0.longitude
                    var latLng : LatLng = LatLng(lat,lng)
                    var stringBuilder : StringBuilder = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
                    stringBuilder.append("location=" + lat + "," + lng)
                    stringBuilder.append("&radius=3000")
                    stringBuilder.append("&type=supermarket")
                    stringBuilder.append("&sensor=true")
                    stringBuilder.append("&key="+resources.getString(R.string.api_key))
                    var url:String = stringBuilder.toString()

                    var fetchdata : FetchData = FetchData()

                    fetchdata.AsyncCall(mMap,url,allMarker)

                    var m : Marker ?= mMap.addMarker(MarkerOptions().position(latLng).title("Sei quì"))
                    allMarker.add(m!!)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13F))

                }
            }
        })
    }

    private fun removeAllMarker() {
        for(m in allMarker){
            m.remove()
        }
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