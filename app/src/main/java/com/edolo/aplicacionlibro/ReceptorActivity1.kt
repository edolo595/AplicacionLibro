package com.edolo.aplicacionlibro

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edolo.aplicacionlibro.databinding.ActivityReceptor1Binding

class ReceptorActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityReceptor1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceptor1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val recep = intent.getStringExtra(resources.getString(R.string.identificador)) ?: ""
        binding.txtReceptor1.setText(recep)
        binding.btnCerrar1.setOnClickListener{
            val intento= Intent()
            intento.putExtra(resources.getString(R.string.identificador_envio),"RESPUESTA DEL RECEPTOR 1")
            setResult(Activity.RESULT_OK,intento)
            finish()
        }
    }
}