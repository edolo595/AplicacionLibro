package com.edolo.aplicacionlibro.activity

import android.app.Application
import com.edolo.aplicacionlibro.util.PrefernciasUsuario

class AplicacionLibro : Application() {
    companion object {
    lateinit var prefs:PrefernciasUsuario
    }
    override fun onCreate() {

        super.onCreate()
         prefs =PrefernciasUsuario(applicationContext)
    }
}