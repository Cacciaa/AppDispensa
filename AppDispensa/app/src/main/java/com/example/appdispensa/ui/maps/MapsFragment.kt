package com.example.appdispensa.ui.maps

import android.Manifest
import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.appdispensa.R
import com.example.appdispensa.databinding.MapsFragmentBinding
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (ContextCompat.checkSelfPermission(root.context,permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Apri la mappa
            Toast.makeText(root.context,"Hai gia dato i permessi", Toast.LENGTH_SHORT).show()
            //requireActivity().finish()
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
                Toast.makeText(root.context,"Permission Granted", Toast.LENGTH_SHORT).show()
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