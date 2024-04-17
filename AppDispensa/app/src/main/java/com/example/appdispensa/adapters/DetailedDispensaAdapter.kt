package com.example.appdispensa.adapters

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.R
import com.example.appdispensa.models.DetailedDispensaModel
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView


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

    }

    override fun getItemCount(): Int {
        return list!!.size
    }
}