package com.example.appdispensa.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.appdispensa.R
import com.example.appdispensa.databinding.FragmentHomeBinding
import com.example.appdispensa.dbhelper.MyDbHelper



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userWelcome = view.findViewById<TextView>(R.id.txtBenvenuto)

        val sharedPreferences: SharedPreferences = binding.root.context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val user_id = sharedPreferences.getInt("user_id", -1) //where 0 is default value

        // Chiamo la query per ottenere il nome utente e la email dato l'id dell'utente loggato


        var db: MyDbHelper = MyDbHelper(this.requireContext(), "dbDispensa.db", 1)
        var cursor: Cursor = db.getUserInfo(user_id)


        var resultList: ArrayList<String> = extractFromCursorUserAndEmail(cursor)
        println(resultList)
        userWelcome.text = resultList.first()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun extractFromCursorUserAndEmail(cursor:Cursor): ArrayList<String>{

        var myList: ArrayList<String> = arrayListOf()


        if (cursor.moveToFirst()) {
            do {
                myList.add(cursor.getString(0))
            } while (cursor.moveToNext());
        }

        return myList
    }
}