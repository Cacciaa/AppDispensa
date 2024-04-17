package com.example.appdispensa.ui.dispensa

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.R
import com.example.appdispensa.adapters.DispensaAdapter
import com.example.appdispensa.databinding.DispensaFragmentBinding
import com.example.appdispensa.models.DispensaModel


class DispensaFragment : Fragment() {

    private var _binding: DispensaFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var recyclerView : RecyclerView?=null
    var dispensaModels:MutableList<DispensaModel> = ArrayList()
    var dispensaAdapter : DispensaAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DispensaFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = root.findViewById(R.id.dispensa_rec);

        recyclerView!!.layoutManager = LinearLayoutManager(context)
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa1"))
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa2"))
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa3"))
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa4"))
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa5"))
        dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa"))

        dispensaAdapter = DispensaAdapter(context,dispensaModels)
        recyclerView!!.adapter = dispensaAdapter
        dispensaAdapter!!.notifyDataSetChanged()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dispensa_fragment, container, false)
    }*/

}