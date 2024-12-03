package com.example.tbcexercises

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var Translator: Translator = GeoTranslate(context = this)


        val etNum: EditText = findViewById(R.id.etNum)
        val txtResult: AppCompatTextView = findViewById(R.id.txtResult)

        val btTranslate: AppCompatButton = findViewById(R.id.btnTranslate)

        val tgbtLanguage: ToggleButton = findViewById(R.id.tgbtLanguage)

        Log.d("Daviti1234", etNum.text.toString())
        Log.d("Daviti1234", Translator.translateNum(etNum.text.toString()))

        btTranslate.setOnClickListener {
            txtResult.text = Translator.translateNum(etNum.text.toString())
        }
        tgbtLanguage.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Translator = EnTranslate(this)
                if (etNum.text.toString() != "") {
                    txtResult.text = Translator.translateNum(etNum.text.toString())
                }
                btTranslate.text = getString(R.string.translate)
                etNum.hint = getString(R.string.hint)
            } else {
                Translator = GeoTranslate(this)
                btTranslate.text = getString(R.string.translate_ge)
                etNum.hint = getString(R.string.hint_ge)
                if (etNum.text.toString() != "") {
                    txtResult.text = Translator.translateNum(etNum.text.toString())
                }
            }
        }

    }
}