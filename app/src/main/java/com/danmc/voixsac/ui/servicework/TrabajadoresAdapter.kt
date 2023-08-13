package com.danmc.voixsac.ui.servicework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danmc.voixsac.R

class TrabajadoresAdapter : RecyclerView.Adapter<TrabajadoresAdapter.TrabajadoresViewHolder>(){
    private var trabajadoresList = mutableListOf<Trabajadores>()
    private var onClickView:((Trabajadores)->Unit)? = null
    private var onClickDelete:((Trabajadores)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadoresViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.trabajadores_item_holder,parent,false)
        return  TrabajadoresViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return trabajadoresList.size
    }

    override fun onBindViewHolder(holder: TrabajadoresViewHolder, position: Int) {
        val trabajadores = trabajadoresList[position]
        holder.setItem(trabajadores)
        holder.setOnClickView {
            onClickView?.invoke(it)
        }
        holder.setOnClickDelete {
            onClickDelete?.invoke(it)
        }
    }

    fun setItems(list: MutableList<Trabajadores>){
        this.trabajadoresList = list
        notifyDataSetChanged()
    }

    fun setOnClickView(callback: (Trabajadores) -> Unit){
        this.onClickView = callback
    }

    fun setOnClickDelete(callback: (Trabajadores) -> Unit){
        this.onClickDelete = callback
    }


    class TrabajadoresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var tvNombre: TextView? = null
        private var tvApellido: TextView? = null
        private var tvCorreo: TextView? = null
        private var tvContrasena: TextView? = null
        private var tvDni: TextView? = null
        private var tvRol: TextView? = null
        private var actionView: ImageView? = null
        private var actionDelete: ImageView? = null

        private var onClickView: ((Trabajadores) -> Unit)?= null
        private var onClickDelete: ((Trabajadores) -> Unit)?= null

        fun setItem(data : Trabajadores){
            tvNombre =itemView.findViewById(R.id.tv_nombre)
            tvApellido =itemView.findViewById(R.id.tv_apellido)
            tvCorreo =itemView.findViewById(R.id.tv_correo)
            tvContrasena =itemView.findViewById(R.id.tv_contrasena)
            tvDni =itemView.findViewById(R.id.tv_dni)
            tvRol =itemView.findViewById(R.id.tv_rol)
            actionView =itemView.findViewById(R.id.ic_view)
            actionDelete = itemView.findViewById(R.id.ic_delete)

            tvNombre?.text = data.nombre
            tvApellido?.text = data.apellido
            tvCorreo?.text = data.correo
            tvContrasena?.text = data.contrasena
            tvDni?.text = data.dni
            tvRol?.text = data.rol

            actionView?.setOnClickListener{
                onClickView?.invoke(data)
            }

            actionDelete?.setOnClickListener{
                onClickDelete?.invoke(data)
            }
        }
        fun setOnClickView(callback: (Trabajadores)->Unit){
            this.onClickView = callback
        }

        fun setOnClickDelete(callback: (Trabajadores) -> Unit){
            this.onClickDelete = callback
        }
    }
}