package com.example.appdispensa

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.adapters.DetailedDispensaAdapter
import com.example.appdispensa.adapters.DispensaAdapter
import com.example.appdispensa.models.DetailedDispensaModel
import com.example.appdispensa.models.DispensaModel

class DetailedDispensaItemActivity : AppCompatActivity() {

    var recyclerView : RecyclerView?=null
    var detailedDispensaModelsList :MutableList<DetailedDispensaModel> = ArrayList()
    var detailedDispensaAdapter : DetailedDispensaAdapter?=null
    var imageview: ImageView?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_dispensa_item)

        var nome: String? = intent.getStringExtra("nome")
        recyclerView = findViewById(R.id.detailed_dispensa_rec);
        imageview = findViewById(R.id.detailed_img)
        recyclerView!!.layoutManager =(LinearLayoutManager(this))
        detailedDispensaModelsList = ArrayList()
        detailedDispensaModelsList.add(DetailedDispensaModel(R.drawable.outline_info_24,"Dispensa"))

        detailedDispensaAdapter = DetailedDispensaAdapter(detailedDispensaModelsList)
        recyclerView!!.adapter=detailedDispensaAdapter
        detailedDispensaAdapter!!.notifyDataSetChanged()

    }
}