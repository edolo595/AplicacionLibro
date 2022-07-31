package com.edolo.aplicacionlibro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edolo.aplicacionlibro.databinding.ActivityReceptor2Binding

class ReceptorActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityReceptor2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceptor2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val recep = intent.getStringExtra(resources.getString(R.string.identificador)) ?: ""
        binding.txtReceptor2.setText(recep)
        binding.btnCerrar2.setOnClickListener{

            val intento= Intent()
            intento.putExtra(resources.getString(R.string.identificador_envio),"RESPUESTA DEL RECEPTOR 2")
            intento.putExtra("NUMERO_ENVIO",256)
            //setResult(Activity.RESULT_OK,intento)
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}