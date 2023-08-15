package com.danmc.voixsac.ui.attendance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danmc.voixsac.R

class AsistenciasAdapter : RecyclerView.Adapter<AsistenciasAdapter.AsistenciasViewHolder>(){
    private var asistenciasList = mutableListOf<Asistencias>()
    private var onClickView:((Asistencias)->Unit)? = null
    private var onClickDelete:((Asistencias)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciasViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.asistenciass_item_holder,parent,false)
        return  AsistenciasViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return asistenciasList.size
    }

    override fun onBindViewHolder(holder: AsistenciasViewHolder, position: Int) {
        val asistencias = asistenciasList[position]
        holder.setItem(asistencias)
        holder.setOnClickView {
            onClickView?.invoke(it)
        }
        holder.setOnClickDelete {
            onClickDelete?.invoke(it)
        }
    }

    fun setItems(list: MutableList<Asistencias>){
        this.asistenciasList = list
        notifyDataSetChanged()
    }

    fun setOnClickView(callback: (Asistencias) -> Unit){
        this.onClickView = callback
    }

    fun setOnClickDelete(callback: (Asistencias) -> Unit){
        this.onClickDelete = callback
    }


    class AsistenciasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var tvFecha: TextView? = null
        private var tvHora: TextView? = null
        private var tvUbicacion: TextView? = null
        private var tvEstado: TextView? = null
        private var tvDni: TextView? = null

        private var actionView: ImageView? = null
        private var actionDelete: ImageView? = null

        private var onClickView: ((Asistencias) -> Unit)?= null
        private var onClickDelete: ((Asistencias) -> Unit)?= null

        fun setItem(data : Asistencias){
            tvFecha =itemView.findViewById(R.id.tv_fecha)
            tvHora =itemView.findViewById(R.id.tv_hora)
            tvUbicacion =itemView.findViewById(R.id.tv_ubicacion)
            tvEstado =itemView.findViewById(R.id.tv_estado)
            tvDni =itemView.findViewById(R.id.tv_dni)

            actionView =itemView.findViewById(R.id.ic_view)
            actionDelete = itemView.findViewById(R.id.ic_delete)

            tvFecha?.text = data.fecha
            tvHora?.text = data.hora
            tvUbicacion?.text = data.ubicacion
            tvEstado?.text = data.estado
            tvDni?.text = data.dni


            actionView?.setOnClickListener{
                onClickView?.invoke(data)
            }

            actionDelete?.setOnClickListener{
                onClickDelete?.invoke(data)
            }
        }
        fun setOnClickView(callback: (Asistencias)->Unit){
            this.onClickView = callback
        }

        fun setOnClickDelete(callback: (Asistencias) -> Unit){
            this.onClickDelete = callback
        }
    }
}