package com.jaques.projetos.listadetarefas.activity

import android.content.Intent
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
    private var taskCurrent: Tarefa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        editTask = text_task
        // Recuperar tarefa, caso seja edição
        taskCurrent = intent.getSerializableExtra("tarefaSelecionada") as? Tarefa

        // Configurar caixa de texto
        editTask.setText(taskCurrent?.nomeTarefa)
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
                val taskDAO = TaskDAO(applicationContext)
                val task = Tarefa()

                if (taskCurrent != null){//edição
                    if (!nomeTarefa.isEmpty()) {
                        task.nomeTarefa = nomeTarefa
                        task.id = taskCurrent!!.id
                        // atualizar no banco de dados
                        if (taskDAO.atualizar(task)){
                            finish()
                            Toast.makeText(applicationContext, "Sucesso ao atualizar", Toast.LENGTH_LONG)
                                .show()
                        }else{
                            Toast.makeText(applicationContext, "Erro ao atualizar", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }else{
                    if (!nomeTarefa.isEmpty()) { //salvar
                        task.nomeTarefa = nomeTarefa
                        if (taskDAO.salvar(task)) {
                            finish()
                            Toast.makeText(applicationContext, "Sucesso ao salvar", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(applicationContext, "Falha ao salvar", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }

            }
        }
        return super.onOptionsItemSelected(item)

    }
}
