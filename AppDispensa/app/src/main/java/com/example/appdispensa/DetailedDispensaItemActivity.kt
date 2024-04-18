package com.example.appdispensa

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.adapters.DetailedDispensaAdapter
import com.example.appdispensa.interfaces.OnValueChangeInt
import com.example.appdispensa.models.DetailedDispensaModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailedDispensaItemActivity : AppCompatActivity(),OnValueChangeInt{

    var recyclerView : RecyclerView?=null
    var detailedDispensaModelsList :MutableList<DetailedDispensaModel> = ArrayList()
    var detailedDispensaAdapter : DetailedDispensaAdapter?=null
    var imageview: ImageView?=null
    var fabadd:FloatingActionButton?=null
    var fabremove:FloatingActionButton?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_dispensa_item)
        var collapsebar : CollapsingToolbarLayout = findViewById(R.id.collapsbar)

        var nome: String? = intent.getStringExtra("nome")

        collapsebar.title = nome
        recyclerView = findViewById(R.id.detailed_dispensa_rec);
        imageview = findViewById(R.id.detailed_img)
        recyclerView!!.layoutManager =(LinearLayoutManager(this))
        detailedDispensaModelsList = ArrayList()
        detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,"Item1"))
        detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,"Item2"))
        detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,"Item3"))
        detailedDispensaAdapter = DetailedDispensaAdapter(detailedDispensaModelsList,this)
        recyclerView!!.adapter=detailedDispensaAdapter
        detailedDispensaAdapter!!.notifyDataSetChanged()

        fabadd = findViewById(R.id.fab_add)
        fabremove = findViewById(R.id.fab_remove)
        fabadd!!.setOnClickListener {view ->
           //call query to db to delete current dispensa
            val inflater: LayoutInflater = getLayoutInflater();
            val dialoglayout: View = inflater.inflate(R.layout.dispensa_add_item_dialog, null);
            val dialogDispensa = AlertDialog.Builder(this)
            dialogDispensa.setView(dialoglayout);
            val dialogDispensaCreate = dialogDispensa.create()
            dialogDispensaCreate.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogDispensaCreate.show();

            var btnAdd = dialogDispensaCreate.findViewById<Button>(R.id.btnConfermaD)
            var btnAnn = dialogDispensaCreate.findViewById<Button>(R.id.btnAnnullaD)

            btnAdd!!.setOnClickListener (object:View.OnClickListener{
                override fun onClick(view: View?) {

                    val nomeInserito = dialogDispensaCreate.findViewById<EditText>(R.id.editTextInserisci)
                    val quantInserito = dialogDispensaCreate.findViewById<EditText>(R.id.editTextQuantity)

                    if(nomeInserito!!.text.toString().isNotEmpty() && quantInserito!!.text.toString().isNotEmpty()){

                        var quant: Int = Integer.parseInt(quantInserito!!.text.toString())
                        if(quant>=0 && quant <=10){
                            Toast.makeText(view!!.context,"Prodotto aggiunto",Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                        }

                    }
                    else{
                        Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                    }

                    dialogDispensaCreate.dismiss()
            }
            })

            btnAnn!!.setOnClickListener (object:View.OnClickListener{
                override fun onClick(view: View?) {
                    dialogDispensaCreate.dismiss()
                }
            })

       }


        fabremove!!.setOnClickListener {view ->
            //call remove item from db
            // go to dispensa fragment and refresh the list after querying to db
            finish()
        }


    }

    override fun deleteOnChange(pos: Int) {
        detailedDispensaModelsList.removeAt(pos)
        detailedDispensaAdapter!!.notifyItemRemoved(pos)

    }



}