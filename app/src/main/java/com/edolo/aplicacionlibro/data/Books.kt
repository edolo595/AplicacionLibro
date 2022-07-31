package com.edolo.aplicacionlibro.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object Books {
    object Books : BaseColumns {
        const val NOMBRE_TABLA = "BOOK"
        const val TITULO = "TITULO"
        const val AUTOR = "AUTOR"
        const val GENERO = "GENERO"
        const val PRECIO = "PRECIO"
        const val IMAGEN = "IMAGEN"
    }

    private const val QUERY_CREAR_TABLA_BOOK =
        "create table ${Books.NOMBRE_TABLA} " +
                "(${BaseColumns._ID} integer primary key," +
                " ${Books.TITULO} text," +
                " ${Books.AUTOR} text," +
                " ${Books.GENERO} text," +
                " ${Books.PRECIO} text," +
                " ${Books.IMAGEN} text)"
    private const val QUERY_DROP_TABLE_BOOK = "drop table if exists ${Books.NOMBRE_TABLA}"

    class BooksDbHelper(contex: Context) :
        SQLiteOpenHelper(contex, NOMBRE_BASE_DATOS, null, VERSIO_BASE_DATOS) {
        companion object {
            const val NOMBRE_BASE_DATOS = "BOOKS"
            const val VERSIO_BASE_DATOS = 1
        }


        override fun onCreate(p0: SQLiteDatabase?) {
            p0?.execSQL(QUERY_CREAR_TABLA_BOOK)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0?.execSQL(QUERY_DROP_TABLE_BOOK)
        }

    }

}