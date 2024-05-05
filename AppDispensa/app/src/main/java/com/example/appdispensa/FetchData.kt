package com.example.appdispensa

import android.os.Handler
import android.os.Looper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FetchData {
    lateinit var googleNearbyPlaces: String
    lateinit var googleMap: GoogleMap
    lateinit var url: String

    var executor1: ExecutorService = Executors.newSingleThreadExecutor()

    var handler: Handler = Handler(Looper.getMainLooper())

    fun AsyncCall(googleMap: GoogleMap, UrlServer: String,allMarkers : MutableList<Marker>) {
        val CONNECTION_TIMEOUT = 10000
        val READ_TIMEOUT = 15000
        var getResult: String? = null
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {

            //Background work here
            this.googleMap = googleMap
            this.url = UrlServer
            var downloadUrl : DownloadUrl = DownloadUrl()
            googleNearbyPlaces = downloadUrl.retrieveUrl(url)

            handler.post {
                makeApiCall(googleNearbyPlaces, googleMap,allMarkers)
            }
        }

    }


    fun makeApiCall(value: String, mMap: GoogleMap, allMarkers: MutableList<Marker>) {

        val jsonObject = JSONObject(value) // This will make the json below as an object for you

        // You can access all the attributes , nested ones using JSONArray and JSONObject here

        var result = jsonObject.getJSONArray("results")

        for (i in 0..result.length()-1) {
            var jsonObject1: JSONObject = result.getJSONObject(i)
            var getLocation: JSONObject =
                jsonObject1.getJSONObject("geometry").getJSONObject("location")

            var latitude: String = getLocation.getString("lat")
            var longitude: String = getLocation.getString("lng")
            var getname: JSONObject = result.getJSONObject(i)

            var name: String = getname.getString("name")

            var newlatlng: LatLng = LatLng(latitude.toDouble(), longitude.toDouble())
            var markeroptions: MarkerOptions = MarkerOptions()
            markeroptions.title(name)
            markeroptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            markeroptions.position(newlatlng)
            allMarkers.add(mMap.addMarker(markeroptions)!!)
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newlatlng, 13F))


        }


    }

}