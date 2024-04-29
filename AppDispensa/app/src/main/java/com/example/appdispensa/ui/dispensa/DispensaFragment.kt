package com.example.appdispensa.ui.dispensa


import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
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
import com.example.appdispensa.dbhelper.MyDbHelper
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
        val btnAddDispensa = binding.root.findViewById<FloatingActionButton>(R.id.fab_add_dispensa)
        //Toast.makeText(binding.root.context,"OnCreateView",Toast.LENGTH_SHORT).show()
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

                        dialogDispensaCreate.dismiss()
                        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
                        val user_id = sharedPreferences.getInt("user_id", -1) //where 0 is default value
                        //add dispensa to db
                        addDispensaToDb(nomeDispensa,user_id)
                        //Refresh
                        updateFragmentView()


                    }
                    else{
                        Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                    }


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
        super.onResume()
        //make filter for catching if onResume lock screen or onResume by delete dispensa
        //see login activity and homefragment
        //Toast.makeText(context,"onResume",Toast.LENGTH_SHORT).show()
        updateFragmentView()
    }

    fun addDispensaToDb(nome:String,id_utente:Int){
        var db: MyDbHelper = MyDbHelper(this.requireContext(), "dbDispensa.db", 1)
        db.insertDispensa(nome,id_utente)
    }

    private fun updateFragmentView(){

        dispensaModels.clear()

        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("user_id", -1) //where 0 is default value

        var db: MyDbHelper = MyDbHelper(this.requireContext(), "dbDispensa.db", 1)
        var cursor: Cursor = db.getDispense(user_id)

        recyclerView = binding.root.findViewById(R.id.dispensa_rec);

        recyclerView!!.layoutManager = LinearLayoutManager(context)

        if (cursor.moveToFirst()) {
            do {
                dispensaModels.add(DispensaModel(cursor.getInt(0),cursor.getString(1)))
            } while (cursor.moveToNext());
        }

        dispensaAdapter = DispensaAdapter(context,dispensaModels)
        recyclerView!!.adapter = dispensaAdapter
        dispensaAdapter!!.notifyDataSetChanged()
    }





}