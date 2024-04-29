package com.example.appdispensa.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appdispensa.R
import com.example.appdispensa.dbhelper.MyDbHelper
import com.example.appdispensa.interfaces.OnValueChangeInt
import com.example.appdispensa.models.DetailedDispensaModel
import com.google.android.material.slider.Slider


class DetailedDispensaAdapter(list:MutableList<DetailedDispensaModel>,onvaluechangeint:OnValueChangeInt,var id_Dispensa:Int) : RecyclerView.Adapter<DetailedDispensaAdapter.ViewHolder>() {
    private var onvaluechangeint : OnValueChangeInt = onvaluechangeint;
    class ViewHolder : RecyclerView.ViewHolder {
        var imageview : ImageView? = null
        var nome: TextView? = null
        constructor(itemView: View) : super(itemView){
            imageview = itemView.findViewById(R.id.detailed_img)
            nome = itemView.findViewById(R.id.detailed_name)

        }

    }


    var list: MutableList<DetailedDispensaModel>? = list

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailedDispensaAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.detailed_dispensa_item,parent,false))

    }

    override fun onBindViewHolder(holder: DetailedDispensaAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.imageview!!.setImageResource(list!!.get(position).image)
        holder.nome!!.setText(list!!.get(position).name)
        var main_slider = holder.itemView.findViewById<Slider>(R.id.slider_quantity)
        main_slider.value = list!!.get(position).quantita.toFloat()
        var text_quantity = holder.itemView.findViewById<TextView>(R.id.text_quantity)
        text_quantity.text = main_slider.value.toInt().toString()

        main_slider.addOnChangeListener(object : Slider.OnChangeListener{
            override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                text_quantity.text = value.toInt().toString()
                if(value.toInt()==0){
                    if(onvaluechangeint != null){
                        var pos = holder.adapterPosition
                        if(pos != RecyclerView.NO_POSITION){
                            var db: MyDbHelper = MyDbHelper(holder.itemView.context, "dbDispensa.db", 1)
                            db.deleteItem(list!!.get(position).id)
                            onvaluechangeint.deleteOnChange(holder.adapterPosition,id_Dispensa)
                            // Cancella sul db l'item selezionato


                        }
                    }

                }
                else{
                    var db: MyDbHelper = MyDbHelper(holder.itemView.context, "dbDispensa.db", 1)
                    db.updateQuantityItem(list!!.get(position).id,value.toInt())
                }
            }

        })

        var minus_img = holder.itemView.findViewById<ImageView>(R.id.minus_img_slider)
        var add_img = holder.itemView.findViewById<ImageView>(R.id.add_img_slider)

        minus_img.setOnClickListener(View.OnClickListener { // Intent class will help to go to next activity using
            if(main_slider.value > 0){
                main_slider.value-=1

            }
        })

        add_img.setOnClickListener(View.OnClickListener { // Intent class will help to go to next activity using
            if(main_slider.value <10){
                main_slider.value+=1

            }
        })

    }

    override fun getItemCount(): Int {
        return list!!.size
    }


}