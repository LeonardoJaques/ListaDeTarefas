package com.jaques.projetos.listadetarefas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jaques.projetos.listadetarefas.R
import com.jaques.projetos.listadetarefas.model.Tarefa

/** author Leonardo Jaques on 18/05/20 */
class TarefaAdapter(listaTarefas: ArrayList<Tarefa>) :
    RecyclerView.Adapter<TarefaAdapter.MyViewHolder>() {

    var lista = listaTarefas


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tarefa: TextView = itemView.findViewById(R.id.textView_tarefa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_tarefa_adapter, parent, false)

        return MyViewHolder(itemLista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tarefa: Tarefa = lista.get(position)
        holder.tarefa.setText(tarefa.nomeTarefa)
    }
}