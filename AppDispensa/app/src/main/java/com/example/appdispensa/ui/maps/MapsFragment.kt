package com.example.appdispensa.ui.maps

import android.Manifest
import android.Manifest.permission
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.appdispensa.NearMeActivity
import com.example.appdispensa.R
import com.example.appdispensa.databinding.MapsFragmentBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MapsFragment : Fragment() {

    private var _binding: MapsFragmentBinding? = null
    private var btnPermessoLoc: Button? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var root : RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapsFragmentBinding.inflate(inflater, container, false)
        root = binding.root
        var locationManager : LocationManager = (root.context.getSystemService(LOCATION_SERVICE)) as LocationManager

        if (ContextCompat.checkSelfPermission(root.context,permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Apri la mappa
            //Toast.makeText(root.context,"Hai gia dato i permessi", Toast.LENGTH_SHORT).show()
            var intent = (Intent(context, NearMeActivity::class.java))
            root.context!!.startActivity(intent)
            parentFragmentManager.popBackStack()
        }else {

            btnPermessoLoc = root.findViewById(R.id.btnPermessoLoc)

            btnPermessoLoc!!.setOnClickListener { view ->
                checkMyPermission(root)
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkMyPermission(root:View){

        Dexter.withContext(root.context).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(object : PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                //oast.makeText(root.context,"Permission Granted", Toast.LENGTH_SHORT).show()
                var intent = (Intent(context, NearMeActivity::class.java))
                context!!.startActivity(intent)
                parentFragmentManager.popBackStack()

            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                var intent: Intent = Intent()
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                var uri: Uri = Uri.fromParts("package", activity!!.packageName, "")
                intent.setData(uri)
                startActivity(intent)

            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
                p1!!.continuePermissionRequest()
            }


        }).check()
    }


}