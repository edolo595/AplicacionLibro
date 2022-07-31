package com.edolo.aplicacionlibro.util

import android.content.Context

class PrefernciasUsuario(val contexto:Context) {

    val SHARE_NAME="COLOR_PREFERENCES"
    val SHARE_NAME_COLOR="COLOR"

    val prefer = contexto.getSharedPreferences(SHARE_NAME,0)



    fun guardarColor(color:Int){
        prefer.edit().putInt(SHARE_NAME_COLOR,color).apply()
    }
    fun getColor():Int{
        return prefer.getInt(SHARE_NAME_COLOR,0)
    }

}