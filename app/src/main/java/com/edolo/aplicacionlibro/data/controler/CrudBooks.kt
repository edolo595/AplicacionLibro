package com.edolo.aplicacionlibro.data.controler

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import com.edolo.aplicacionlibro.data.Books
import com.edolo.aplicacionlibro.modelo.LibroData

class CrudBooks (context: Context) {
    private val DB = Books.BooksDbHelper(context)
    val listaLibros = mutableListOf<LibroData>()

    fun gregarLibro(datos: LibroData): Long {
        val db = DB.writableDatabase
        val valores = ContentValues().apply {
            put(Books.Books.TITULO, datos.titulo)
            put(Books.Books.AUTOR, datos.autor)
            put(Books.Books.GENERO, datos.genero)
            put(Books.Books.PRECIO, datos.precio)
            put(Books.Books.IMAGEN, datos.imagen)
        }
        return db.insert(Books.Books.NOMBRE_TABLA, null, valores)
    }

    fun leerLibro(): List<LibroData> {
        val db = DB.readableDatabase
        val proyec = arrayOf(
            BaseColumns._ID,
            Books.Books.TITULO,
            Books.Books.AUTOR,
            Books.Books.GENERO,
            Books.Books.PRECIO,
            Books.Books.IMAGEN
        )
        val cursor = db.query(Books.Books.NOMBRE_TABLA, proyec, null, null, null, null, null, null)
        listaLibros.clear()
        with(cursor) {
            while (moveToNext()) {
                listaLibros.add(
                    LibroData(
                        getLong(getColumnIndexOrThrow(BaseColumns._ID)),
                        getString(getColumnIndexOrThrow(Books.Books.TITULO)),
                        getString(getColumnIndexOrThrow(Books.Books.AUTOR)),
                        getString(getColumnIndexOrThrow(Books.Books.GENERO)),
                        getString(getColumnIndexOrThrow(Books.Books.PRECIO)),
                        getString(getColumnIndexOrThrow(Books.Books.IMAGEN))

                    )
                )
            }
            for (books in listaLibros) {
                Log.i("libros", books.imagen)
            }
        }
        return listaLibros
    }

    fun borrarLibro(posision: Long): Int {
        val db = DB.writableDatabase
        val seleccion = "${BaseColumns._ID} = ?"

        val selectArgs = arrayOf(posision.toString())
        val filasBorradas = db.delete(Books.Books.NOMBRE_TABLA, seleccion, selectArgs)
        return filasBorradas
    }

    fun actualizarLibro(posision: Long, datos: LibroData): Int {
        val db = DB.writableDatabase
        val valores = ContentValues().apply {
            put(Books.Books.TITULO, datos.titulo)
            put(Books.Books.AUTOR, datos.autor)
            put(Books.Books.GENERO, datos.genero)
            put(Books.Books.PRECIO, datos.precio)
        }

        val seleccion = "${BaseColumns._ID} = ?"
        val selectArgs = arrayOf(posision.toString())
        val filaActualizada = db.update(Books.Books.NOMBRE_TABLA, valores,seleccion, selectArgs)
        return filaActualizada
    }
}