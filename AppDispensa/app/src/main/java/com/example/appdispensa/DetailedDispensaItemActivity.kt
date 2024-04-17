package com.example.appdispensa

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.adapters.DetailedDispensaAdapter
import com.example.appdispensa.models.DetailedDispensaModel
import com.example.appdispensa.ui.dispensa.DispensaFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailedDispensaItemActivity : AppCompatActivity() {

    var recyclerView : RecyclerView?=null
    var detailedDispensaModelsList :MutableList<DetailedDispensaModel> = ArrayList()
    var detailedDispensaAdapter : DetailedDispensaAdapter?=null
    var imageview: ImageView?=null
    var fab:FloatingActionButton?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_dispensa_item)
        var collapsebar : CollapsingToolbarLayout = findViewById(R.id.collapsbar)
        collapsebar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        collapsebar.setExpandedTitleTextAppearance(R.style.CollapsedAppBar)

        var nome: String? = intent.getStringExtra("nome")
        collapsebar.title = nome
        recyclerView = findViewById(R.id.detailed_dispensa_rec);
        imageview = findViewById(R.id.detailed_img)
        recyclerView!!.layoutManager =(LinearLayoutManager(this))
        detailedDispensaModelsList = ArrayList()
        detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,"Item1"))

        detailedDispensaAdapter = DetailedDispensaAdapter(detailedDispensaModelsList)
        recyclerView!!.adapter=detailedDispensaAdapter
        detailedDispensaAdapter!!.notifyDataSetChanged()

        fab = findViewById(R.id.fab_remove)
       fab!!.setOnClickListener {view ->
           val bundle = Bundle()
           bundle.putBoolean("deleted", true)
           bundle.putString("name",nome)
           val fragobj = DispensaFragment()
           fragobj.setArguments(bundle)
           finish()

       }


    }




}