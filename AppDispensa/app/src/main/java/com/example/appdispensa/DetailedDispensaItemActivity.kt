package com.example.appdispensa

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.adapters.DetailedDispensaAdapter
import com.example.appdispensa.dbhelper.MyDbHelper
import com.example.appdispensa.interfaces.OnValueChangeInt
import com.example.appdispensa.models.DetailedDispensaModel
import com.example.appdispensa.models.DispensaModel
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
        var collapsbar : CollapsingToolbarLayout = findViewById(R.id.collapsbar)
        collapsbar.setExpandedTitleTextAppearance(R.style.FontCollapsed)
        collapsbar.setCollapsedTitleTextAppearance(R.style.FontExpanded)
        var nome: String? = intent.getStringExtra("nome_dispensa")
        var idDispensa: Int = intent.getIntExtra("id_dispensa", -1)
        collapsbar.title = nome

        imageview = findViewById(R.id.detailed_img)


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
                        if(quant>0 && quant <=10){
                            Toast.makeText(view!!.context,"Prodotto aggiunto",Toast.LENGTH_SHORT).show()
                            dialogDispensaCreate.dismiss()
                            //add product to db
                            addItemToDb(nomeInserito.text.toString(),quant,idDispensa)
                            //refresh
                            updateActivityView(idDispensa)
                        }
                        else{
                            Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                        }

                    }
                    else{
                        Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                    }


            }
            })

            btnAnn!!.setOnClickListener (object:View.OnClickListener{
                override fun onClick(view: View?) {
                    dialogDispensaCreate.dismiss()
                }
            })

       }


        fabremove!!.setOnClickListener {view ->
            var idDispensa: Int = intent.getIntExtra("id_dispensa", -1)
            var db: MyDbHelper = MyDbHelper(this, "dbDispensa.db", 1)
            db.deleteDispensa(idDispensa)
            finish()
        }


    }

    override fun deleteOnChange(pos: Int,id_dispensa: Int) {
        detailedDispensaModelsList.removeAt(pos)
        detailedDispensaAdapter!!.notifyItemRemoved(pos)

        var db: MyDbHelper = MyDbHelper(this, "dbDispensa.db", 1)
        var cursor: Cursor = db.getItemOfDispensa(id_dispensa)
        detailedDispensaModelsList.clear()
        if (cursor.moveToFirst()) {
            do {
                detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,cursor.getString(0),cursor.getInt(1),cursor.getInt(2)))
            } while (cursor.moveToNext());
        }

        detailedDispensaAdapter = DetailedDispensaAdapter(detailedDispensaModelsList,this,id_dispensa)
        recyclerView!!.adapter=detailedDispensaAdapter
        detailedDispensaAdapter!!.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        var idDispensa: Int = intent.getIntExtra("id_dispensa", -1)
        updateActivityView(idDispensa)
    }

    private fun updateActivityView(id_dispensa:Int){
        detailedDispensaModelsList.clear()
        recyclerView = findViewById(R.id.detailed_dispensa_rec);
        recyclerView!!.layoutManager =(LinearLayoutManager(this))
        detailedDispensaModelsList = ArrayList()
        var db: MyDbHelper = MyDbHelper(this, "dbDispensa.db", 1)
        var cursor: Cursor = db.getItemOfDispensa(id_dispensa)

        if (cursor.moveToFirst()) {
            do {
                detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,cursor.getString(0),cursor.getInt(1),cursor.getInt(2)))
            } while (cursor.moveToNext());
        }
        detailedDispensaAdapter = DetailedDispensaAdapter(detailedDispensaModelsList,this,id_dispensa)
        recyclerView!!.adapter=detailedDispensaAdapter
        detailedDispensaAdapter!!.notifyDataSetChanged()
    }

    private fun addItemToDb(nome:String,quantita:Int,id_dispensa: Int){
        var db: MyDbHelper = MyDbHelper(this, "dbDispensa.db", 1)
        db.insertItem(nome,quantita,id_dispensa)
    }



}