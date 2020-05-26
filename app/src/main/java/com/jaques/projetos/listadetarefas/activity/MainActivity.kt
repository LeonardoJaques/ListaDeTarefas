package com.jaques.projetos.listadetarefas.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
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
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        Log.i("clique", "onItemLongClick")
                    }
                })
        )


        fab.setOnClickListener { view ->
            val intent = Intent(applicationContext, AddTaskActivity::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
