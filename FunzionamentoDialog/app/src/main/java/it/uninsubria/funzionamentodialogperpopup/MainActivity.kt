package it.uninsubria.funzionamentodialogperpopup


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPopup : Button = findViewById<Button>(R.id.btnDialog)

        btnPopup.setOnClickListener{
            showCustomDialog()

        }
    }

    private fun showCustomDialog() {
        val inflater:LayoutInflater = getLayoutInflater();
        val dialoglayout: View = inflater.inflate(R.layout.dispensa_add_item_dialog, null);
        val dialogDispensa = AlertDialog.Builder(this)
        dialogDispensa.setView(dialoglayout);
        val dialogDispensaCreate = dialogDispensa.create()
        dialogDispensaCreate.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogDispensaCreate.show();
    }
}