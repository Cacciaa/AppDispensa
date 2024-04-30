package com.example.appdispensa.ui.macro

import android.content.Context
import android.content.SharedPreferences
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
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.appdispensa.R
import com.example.appdispensa.databinding.FragmentMacroBinding
import com.example.appdispensa.dbhelper.MyDbHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.callbackFlow

class MacroFragment : Fragment() {

    private var _binding: FragmentMacroBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMacroBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupChartView()
        return root
    }

    private fun setupChartView() {
        var pie : Pie = AnyChart.pie()
        var datalist: MutableList<DataEntry> =  ArrayList<DataEntry>()

        datalist.add(ValueDataEntry("Carboidrati",250))
        datalist.add(ValueDataEntry("Fibre",125))
        datalist.add(ValueDataEntry("Grassi",325))
        datalist.add(ValueDataEntry("Proteine",215))
        pie.data(datalist)
        pie.background().enabled()
        pie.background().fill("#262523")
        pie.palette(arrayOf("#3BA580", "#E1D926", "#A965B8", "#4e7a96"))
        var chart : AnyChartView = binding.root.findViewById(R.id.chartMacro)
        chart!!.setChart(pie)


        var fab_add:FloatingActionButton = binding.root.findViewById(R.id.fab_add_macro)

        fab_add.setOnClickListener{ view->
            val inflater: LayoutInflater = getLayoutInflater();
            val dialoglayout: View = inflater.inflate(R.layout.macro_dialog, null);
            val dialogMacro = AlertDialog.Builder(binding.root.context)
            dialogMacro.setView(dialoglayout);
            val dialogMacroCreate = dialogMacro.create()
            dialogMacroCreate.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogMacroCreate.show();

            var btnAdd = dialogMacroCreate.findViewById<Button>(R.id.btnConfermaMacro)
            var btnAnn = dialogMacroCreate.findViewById<Button>(R.id.btnAnnullaMacro)

            btnAdd!!.setOnClickListener (object:View.OnClickListener {
                override fun onClick(view: View?) {
                    var carbo:String = dialogMacroCreate.findViewById<EditText>(R.id.editTextCarbo)!!.text.toString()
                    var proteine:String = dialogMacroCreate.findViewById<EditText>(R.id.editTextProteine)!!.text.toString()
                    var fibre:String = dialogMacroCreate.findViewById<EditText>(R.id.editTextFibre)!!.text.toString()
                    var grassi:String = dialogMacroCreate.findViewById<EditText>(R.id.editTextGrassi)!!.text.toString()

                    if(carbo.isNotEmpty() || proteine.isNotEmpty() || fibre.isNotEmpty() || grassi.isNotEmpty()){
                        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
                        val user_id = sharedPreferences.getInt("user_id", -1) //where 0 is default value
                        var db: MyDbHelper = MyDbHelper(view!!.context, "dbDispensa.db", 1)
                        if(carbo.equals("")) carbo= "0"
                        if(proteine.equals("")) proteine= "0"
                        if(fibre.equals("")) fibre= "0"
                        if(grassi.equals("")) grassi= "0"
                        db.insertMacro(carbo.toInt(),proteine.toInt(),grassi.toInt(),fibre.toInt(),user_id)
                        dialogMacroCreate.dismiss()
                    }
                    else{
                        Toast.makeText(view!!.context,"Errore. Ricontrolla i valori",Toast.LENGTH_SHORT).show()
                    }

                }
            })

            btnAnn!!.setOnClickListener (object:View.OnClickListener {
                override fun onClick(view: View?) {
                    dialogMacroCreate.dismiss()
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}