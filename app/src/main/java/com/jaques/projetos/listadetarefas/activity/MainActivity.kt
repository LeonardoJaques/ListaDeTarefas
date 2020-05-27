package com.jaques.projetos.listadetarefas.activity

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaques.projetos.listadetarefas.R
import com.jaques.projetos.listadetarefas.adapter.TarefaAdapter
import com.jaques.projetos.listadetarefas.helper.DbHelper
import com.jaques.projetos.listadetarefas.helper.RecyclerItemClickListenr
import com.jaques.projetos.listadetarefas.helper.TaskDAO
import com.jaques.projetos.listadetarefas.model.Tarefa
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tarefaAdapter: TarefaAdapter
    private var listaTarefas = ArrayList<Tarefa>()
    private lateinit var taskSelection: Tarefa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Configuarar recycler
        recyclerView = recyclerView_listaDeTarefas

        // adicionar evento de click
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                recyclerView,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        //Recuperar tarefa para edição
                        val tarefaSelecionada: Tarefa = listaTarefas[position]
                        // Enviar tarefa para a tela adicionar tarefas
                        val intent = Intent(applicationContext, AddTaskActivity::class.java)
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        //Recuperar tarefa para deletar
                        taskSelection = listaTarefas[position]
                        val dialog: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                        // Configurar título e mensagem
                        dialog.setTitle("Confirmar exclusão")
                        dialog.setMessage("Deseja excluir a tarefa ${taskSelection.nomeTarefa} ?")
                        dialog.setPositiveButton("Sim") { action, which ->
                            val taskDao = TaskDAO(applicationContext)
                            if (taskDao.deletar(taskSelection)) {
                                carregarListaTarefas()
                                Toast.makeText(
                                    applicationContext,
                                    "Sucesso ao excluir tarefa ${taskSelection.nomeTarefa}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Erro ao excluir ${taskSelection.nomeTarefa}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        dialog.setNegativeButton("Não", null)
                        //Exibir a dialog
                        dialog.create().show()


                    }
                })
        )


        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    fun carregarListaTarefas(): Unit {
        //Listar tarefas
        val taskDAO = TaskDAO(applicationContext)
        listaTarefas = taskDAO.listar()


        /*
        * Exibir listas de tarefas no RecyclerView
        * */

        //Configurar adapter
        tarefaAdapter = TarefaAdapter(listaTarefas)

        //configurar RecyclerView
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayout.VERTICAL
            )
        )
        recyclerView.adapter = tarefaAdapter
        //Adicionar evento de clique
    }

    override fun onStart() {
        carregarListaTarefas()
        super.onStart()
    }

}
