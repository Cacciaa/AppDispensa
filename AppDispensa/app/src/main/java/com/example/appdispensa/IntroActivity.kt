package com.example.appdispensa

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.hololo.tutorial.library.Step
import com.hololo.tutorial.library.TutorialActivity


class IntroActivity : TutorialActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPrevText("Indietro")
        setNextText("Avanti")
        setFinishText("Fine")
        setCancelText("Annulla")
        addFragment(
            Step.Builder()
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro1) // int top drawable
                .build()
        )

        addFragment(
            Step.Builder()

                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro2) // int top drawable
                .build()
        )


        addFragment(
            Step.Builder()
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro3) // int top drawable
                .build()
        )

        addFragment(
            Step.Builder()
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro4) // int top drawable
                .build()
        )

        addFragment(
            Step.Builder()

                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro5) // int top drawable
                .build()
        )


        addFragment(
            Step.Builder()
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.drawable.intro6) // int top drawable
                .build()
        )




    }

    override fun currentFragmentPosition(position: Int) {

    }

    override fun finishTutorial() {
        val intent = Intent(this,LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun setCancelText(text: String?) {
        super.setCancelText(text)
    }

    override fun setNextText(text: String?) {
        super.setNextText(text)
    }

    override fun setFinishText(text: String?) {
        super.setFinishText(text)
    }

    override fun setPrevText(text: String?) {
        super.setPrevText(text)
    }
}