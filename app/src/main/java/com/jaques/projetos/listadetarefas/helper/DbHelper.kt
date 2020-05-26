package com.jaques.projetos.listadetarefas.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/** author Leonardo Jaques on 25/05/20 */

class DbHelper(context: Context) : SQLiteOpenHelper(context, "DB_TAREFAS", null, 1) {

    companion object {
        const val TABELA_TAREFAS: String = "tarefas"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql =
            "CREATE TABLE IF NOT EXISTS $TABELA_TAREFAS (id INTEGER PRIMARY KEY AUTOINCREMENT,  nome TEXT NOT NULL ); "

        try {
            db.execSQL(sql)
            Log.i("INFO DB", "Sucesso ao criar a tabela")
        } catch (e: Exception) {
            Log.i("INFO DB", "Erro ao criar a tabela" + e.message)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS $TABELA_TAREFAS ;"

        try {
            db?.execSQL(sql)
            onCreate(db!!)
            Log.i("INFO DB", "Sucesso ao atualizar App")
        } catch (e: Exception) {
            Log.i("INFO DB", "Erro  ao atualizar App" + e.message)
        }
    }
}


