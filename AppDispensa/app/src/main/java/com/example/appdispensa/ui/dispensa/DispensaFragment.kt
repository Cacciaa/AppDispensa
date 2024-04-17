package com.example.appdispensa.ui.dispensa


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    var check:Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DispensaFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()

        if(check == false){
            recyclerView = binding.root.findViewById(R.id.dispensa_rec);

            recyclerView!!.layoutManager = LinearLayoutManager(context)
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa1"))
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa2"))
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa3"))
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa4"))
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa5"))
            dispensaModels.add(DispensaModel(Color.parseColor("#56181e"),"Dispensa6"))

            dispensaAdapter = DispensaAdapter(context,dispensaModels)
            recyclerView!!.adapter = dispensaAdapter
            dispensaAdapter!!.notifyDataSetChanged()
            check = true
        }
        else{
            recyclerView = binding.root.findViewById(R.id.dispensa_rec);

            recyclerView!!.layoutManager = LinearLayoutManager(context)
            dispensaModels.removeAt(0)
            dispensaAdapter = DispensaAdapter(context,dispensaModels)
            recyclerView!!.adapter = dispensaAdapter
            dispensaAdapter!!.notifyItemRemoved(0)
        }

    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(context,"In start",Toast.LENGTH_LONG).show()
    }






}