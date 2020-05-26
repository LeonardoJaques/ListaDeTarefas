package com.jaques.projetos.listadetarefas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.jaques.projetos.listadetarefas.R
import com.jaques.projetos.listadetarefas.helper.TaskDAO
import com.jaques.projetos.listadetarefas.model.Tarefa
import kotlinx.android.synthetic.main.activity_add_task.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var editTask: TextInputEditText
//    private lateinit var taskCurrent: Tarefa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTask = text_task
        // Recuperar tarefa, caso seja edição
//        taskCurrent = intent.getSerializableExtra("tarefaSelecionada") as Tarefa

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_task, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val salvar = R.id.save

        when (item.itemId) {
            salvar -> {
                val nomeTarefa = editTask.text.toString()
                if (!nomeTarefa.isEmpty()) {
                    val taskDAO = TaskDAO(applicationContext)
                    val task = Tarefa()
                    task.nomeTarefa = nomeTarefa
                    taskDAO.salvar(task)
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
