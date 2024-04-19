package com.example.appdispensa.ui.dispensa


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.R
import com.example.appdispensa.adapters.DispensaAdapter
import com.example.appdispensa.databinding.DispensaFragmentBinding
import com.example.appdispensa.interfaces.OnValueChangeInt
import com.example.appdispensa.models.DispensaModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


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
        //CALL updateFragmentView for showing all the dispensa of the current user
        updateFragmentView()
        val btnAddDispensa = binding.root.findViewById<FloatingActionButton>(R.id.fab_add_dispensa)

        btnAddDispensa.setOnClickListener{view->
            val inflater: LayoutInflater = getLayoutInflater();
            val dialoglayout: View = inflater.inflate(R.layout.add_dispensa_dialog, null);
            val dialogDispensa = AlertDialog.Builder(binding.root.context)
            dialogDispensa.setView(dialoglayout);
            val dialogDispensaCreate = dialogDispensa.create()
            dialogDispensaCreate.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogDispensaCreate.show();

            val btnConfermaDispensa= dialogDispensaCreate.findViewById<Button>(R.id.btnConfermaDispensa)
            val btnAnnullaDispensa = dialogDispensaCreate.findViewById<Button>(R.id.btnAnnullaDispensa)

            btnConfermaDispensa!!.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    val nomeDispensa : String = dialogDispensaCreate.findViewById<EditText>(R.id.editTextDispensa)!!.text.toString()

                    if(nomeDispensa.isNotEmpty()){
                        //Add element to database
                        Toast.makeText(view!!.context,"Dispensa creata", Toast.LENGTH_SHORT).show()
                        dialogDispensaCreate.dismiss()
                    }
                    else{
                        Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                    }
                    //close dialog

                }

            })

            btnAnnullaDispensa!!.setOnClickListener{view->
                dialogDispensaCreate.dismiss()
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        //here refresh by querying to db
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


    fun addDispensaToList(){

    }

    fun updateFragmentView(){

    }
    /*override fun onStart() {
        super.onStart()

    }*/




}