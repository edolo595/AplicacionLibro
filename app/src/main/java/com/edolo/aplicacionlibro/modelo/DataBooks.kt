package com.edolo.aplicacionlibro.modelo

import java.io.Serializable

data class DataBooks(var titulo:String, var autor:String,var genero:String, var precio:String,var imagen:Int?): Serializable
