package com.jaques.projetos.listadetarefas.helper

import com.jaques.projetos.listadetarefas.model.Tarefa

/** author Leonardo Jaques on 25/05/20 */
interface ITaskDAO {

    fun salvar(tarefa: Tarefa): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun deletar(tarefa: Tarefa): Boolean
    fun listar(): List<Tarefa>

}