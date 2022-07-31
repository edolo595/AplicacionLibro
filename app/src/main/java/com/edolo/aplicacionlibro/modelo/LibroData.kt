package com.edolo.aplicacionlibro.modelo

import java.io.Serializable

data class LibroData(
    var idLibro: Long,
    var titulo: String,
    var autor: String,
    var genero: String,
    var precio: String,
    var imagen: String
):Serializable
