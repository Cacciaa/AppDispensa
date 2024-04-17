package com.example.appdispensa.adapters

import android.content.Intent
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.R
import com.example.appdispensa.models.DetailedDispensaModel
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.appdispensa.DetailedDispensaItemActivity
import com.google.android.material.slider.Slider


class DetailedDispensaAdapter(list:List<DetailedDispensaModel>) : RecyclerView.Adapter<DetailedDispensaAdapter.ViewHolder>() {
    class ViewHolder : RecyclerView.ViewHolder {
        var imageview : ImageView? = null
        var nome: TextView? = null
        constructor(itemView: View) : super(itemView){
            imageview = itemView.findViewById(R.id.detailed_img)
            nome = itemView.findViewById(R.id.detailed_name)
        }

    }


    var list: List<DetailedDispensaModel>? = list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailedDispensaAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.detailed_dispensa_item,parent,false))

    }

    override fun onBindViewHolder(holder: DetailedDispensaAdapter.ViewHolder, position: Int) {
        holder.imageview!!.setImageResource(list!!.get(position).image)
        holder.nome!!.setText(list!!.get(position).name)

        var main_slider = holder.itemView.findViewById<Slider>(R.id.slider_quantity)
        var text_quantity = holder.itemView.findViewById<TextView>(R.id.text_quantity)

        main_slider.addOnChangeListener(object : Slider.OnChangeListener{
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                text_quantity.text = value.toInt().toString()
            }

        })

    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}