package com.danmc.voixsac.ui.attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.danmc.voixsac.databinding.ActivityAttendanceWorkListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AttendanceWorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceWorkListBinding
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var list = mutableListOf<Asistencias>()

    private var adapter: AsistenciasAdapter? = null
    private var selectedDni:String?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceWorkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("asistencias")
        getData()

        binding.btnSave.setOnClickListener{saveData()}
        binding.btnUpdate.setOnClickListener{updateData()}
    }



    private fun updateData(){
        val fecha = binding.edFecha.text.toString()
        val hora = binding.edHora.text.toString()
        val ubicacion = binding.edUbicacion.text.toString()
        val estado = binding.edEstado.text.toString()
        val dni = binding.edDni.text.toString()
        val asistencias = Asistencias(fecha = fecha, hora = hora, ubicacion = ubicacion, estado = estado, dni = dni)

        databaseReference?.child(selectedDni.toString())?.setValue(asistencias)
    }

    private fun initRecyclerView(){
        adapter = AsistenciasAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@AttendanceWorkerActivity)
            recyclerView.adapter = adapter

        }
        adapter?.setOnClickView {
            binding.edFecha.setText(it.fecha.toString())
            binding.edHora.setText(it.hora.toString())
            binding.edUbicacion.setText(it.ubicacion.toString())
            binding.edEstado.setText(it.estado.toString())
            binding.edDni.setText(it.dni.toString())
            selectedDni = it.dni
        }

        adapter?.setOnClickDelete {
            selectedDni = it.dni
            databaseReference?.child(selectedDni.orEmpty())?.removeValue()
        }

    }

    private fun saveData(){
        val fecha = binding.edFecha.text.toString()
        val hora = binding.edHora.text.toString()
        val ubicacion = binding.edUbicacion.text.toString()
        val estado = binding.edEstado.text.toString()
        val dni = binding.edDni.text.toString()
        val asistencias = Asistencias(fecha = fecha, hora = hora, ubicacion = ubicacion, estado = estado, dni = dni)

        databaseReference?.child(getRandomString(3))?.setValue(asistencias)
    }

    private fun getData(){
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                //Log.e("oooo","onDataChange: $snapshot")
                list.clear()
                for (ds in snapshot.children){
                    val dni = ds.key
                    val fecha = ds.child("fecha").value.toString()
                    val hora = ds.child("hora").value.toString()
                    val ubicacion = ds.child("ubicación").value.toString()
                    val estado = ds.child("estado").value.toString()

                    val asistencias = Asistencias(fecha = fecha, hora = hora, ubicacion = ubicacion, estado = estado, dni = dni)
                    list.add(asistencias)
                }
                Log.e("oooo","size: ${list.size}")

                adapter?.setItems(list)

            }
            override fun onCancelled(error: DatabaseError){
                Log.e("ooooo","onCancelled: ${error.toException()}")
            }
        })
    }
}

    fun getRandomString(length:Int) : String{
        val allowedChars = ('A'..'Z')+('a'..'z')+('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
}
