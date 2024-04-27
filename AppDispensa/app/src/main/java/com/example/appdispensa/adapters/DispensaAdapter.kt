package com.example.appdispensa.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.DetailedDispensaItemActivity
import com.example.appdispensa.R
import com.example.appdispensa.interfaces.OnValueChangeInt
import com.example.appdispensa.models.DispensaModel
import com.google.gson.Gson

class DispensaAdapter(context: Context?, list:MutableList<DispensaModel>) : RecyclerView.Adapter<DispensaAdapter.ViewHolder>(){

    var context: Context? = context
    var list: MutableList<DispensaModel>? = list

    class ViewHolder : RecyclerView.ViewHolder {

        var imageview: ImageView? = null
        var nome: TextView? = null

        constructor(itemView: View) : super(itemView) {
            imageview = itemView.findViewById(R.id.imageView5)
            nome = itemView.findViewById(R.id.txtNomeDisp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.dispensa_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.imageview!!.setBackgroundColor(Color.parseColor("#56181e"))
        holder.nome!!.setText(list!!.get(position).name)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                var intent = Intent(context,DetailedDispensaItemActivity::class.java)
                intent.putExtra("id_dispensa",list!!.get(position).id)
                intent.putExtra("name",list!!.get(position).name)
                context!!.startActivity(intent)

            }
        })


    }
}