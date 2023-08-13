package com.danmc.voixsac.ui.service

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danmc.voixsac.R


class ServiciosAdapter : RecyclerView.Adapter<ServiciosAdapter.ServiciosViewHolder>(){
    private var serviciosList = mutableListOf<Servicios>()
    private var onClickView:((Servicios)->Unit)? = null
    private var onClickDelete:((Servicios)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiciosViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.servicios_item_holder,parent,false)
        return  ServiciosViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return serviciosList.size
    }

    override fun onBindViewHolder(holder: ServiciosViewHolder, position: Int) {
        val servicios = serviciosList[position]
        holder.setItem(servicios)
        holder.setOnClickView {
            onClickView?.invoke(it)
        }
        holder.setOnClickDelete {
            onClickDelete?.invoke(it)
        }
    }

    fun setItems(list: MutableList<Servicios>){
        this.serviciosList = list
        notifyDataSetChanged()
    }

    fun setOnClickView(callback: (Servicios) -> Unit){
        this.onClickView = callback
    }

    fun setOnClickDelete(callback: (Servicios) -> Unit){
        this.onClickDelete = callback
    }


    class ServiciosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var tvCodigo: TextView? = null
        private var tvCliente: TextView? = null
        private var tvUbicacion: TextView? = null
        private var actionView: ImageView? = null
        private var actionDelete: ImageView? = null

        private var onClickView: ((Servicios) -> Unit)?= null
        private var onClickDelete: ((Servicios) -> Unit)?= null

        fun setItem(data : Servicios){
            tvCodigo =itemView.findViewById(R.id.tv_codigo)
            tvCliente =itemView.findViewById(R.id.tv_cliente)
            tvUbicacion =itemView.findViewById(R.id.tv_ubicacion)
            actionView =itemView.findViewById(R.id.ic_view)
            actionDelete = itemView.findViewById(R.id.ic_delete)

            tvCodigo?.text = data.codigo
            tvCliente?.text = data.cliente
            tvUbicacion?.text = data.ubicacion

            actionView?.setOnClickListener{
                onClickView?.invoke(data)
            }

            actionDelete?.setOnClickListener{
                onClickDelete?.invoke(data)
            }
        }
        fun setOnClickView(callback: (Servicios)->Unit){
            this.onClickView = callback
        }

        fun setOnClickDelete(callback: (Servicios) -> Unit){
            this.onClickDelete = callback
        }
    }
}