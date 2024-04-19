package com.example.appdispensa.ui.home


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.appdispensa.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("user_id", 0) //where 0 is default value
        //Toast.makeText(binding.root.context,user_id.toString(),Toast.LENGTH_SHORT).show()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}