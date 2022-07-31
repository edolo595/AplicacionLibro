package com.edolo.aplicacionlibro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edolo.aplicacionlibro.databinding.ActivityReceptorBinding

class ReceptorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReceptorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceptorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recep = intent.getStringExtra(resources.getString(R.string.identificador)) ?: ""
        binding.txtRepetor0.setText(recep)

        binding.btnCerrar0.setOnClickListener{
            val intento= Intent()
            intento.putExtra(resources.getString(R.string.identificador_envio),"RESPUESTA DEL RECEPTOR 0")
            setResult(Activity.RESULT_OK,intento)

            finish()
        }
    }

}