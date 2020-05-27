package com.jaques.projetos.listadetarefas.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.jaques.projetos.listadetarefas.model.Tarefa

/** author Leonardo Jaques on 25/05/20 */
class TaskDAO(context: Context) : ITaskDAO {
    private var escrever: SQLiteDatabase
    private var ler: SQLiteDatabase

    init {
        val db = DbHelper(context)
        escrever = db.writableDatabase
        ler = db.readableDatabase
    }


    override fun salvar(tarefa: Tarefa): Boolean {
        val cv = ContentValues()
        cv.put("nome", tarefa.nomeTarefa)

        try {
            escrever.insert(DbHelper.TABELA_TAREFAS, null, cv)
            Log.i("INFO", "Tarefa salva com Sucesso")
        } catch (e: Exception) {
            Log.e("INFO", "Erro ao salvar tarefa" + e.message)
            return false
        }
        return true
    }

    override fun atualizar(tarefa: Tarefa): Boolean {
        val cv = ContentValues()
        cv.put("nome", tarefa.nomeTarefa)
        try {
            val args = arrayOf(tarefa.id.toString())
            escrever.update(DbHelper.TABELA_TAREFAS, cv, "id=?", args)
            Log.i("INFO", "Tarefa atualizada com Sucesso")
        } catch (e: Exception) {
            Log.e("INFO", "Erro ao atualizar tarefa" + e.message)
            return false
        }

        return true
    }

    override fun deletar(tarefa: Tarefa): Boolean {
        TODO("Not yet implemented")
    }

    override fun listar(): ArrayList<Tarefa> {
        val tarefas = ArrayList<Tarefa>()
        val sql = "SELECT * FROM ${DbHelper.TABELA_TAREFAS};"
        val c: Cursor = ler.rawQuery(sql, null)

        while (c.moveToNext()) {
            val tarefa = Tarefa()
            val id: Long = c.getLong(c.getColumnIndex("id"))

            val nomeTarefa: String = c.getString(c.getColumnIndex("nome"))
            tarefa.id = id
            tarefa.nomeTarefa = nomeTarefa

            tarefas.add(tarefa)
        }

        return tarefas
    }

}

