package com.danmc.voixsac.ui.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.danmc.voixsac.databinding.ActivityServiceBinding
import com.danmc.voixsac.ui.servicework.getRandomString
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var list = mutableListOf<Servicios>()

    private var adapter: ServiciosAdapter? = null
    private var selectedId:String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("servicios")
        getData()

        binding.btnSave.setOnClickListener{saveData()}
        binding.btnUpdate.setOnClickListener{updateData()}
    }

    private fun updateData(){
        val codigo = binding.edCodigo.text.toString()
        val cliente = binding.edCliente.text.toString()
        val ubicacion = binding.edUbicacion.text.toString()
        val servicios = Servicios(codigo=codigo, cliente=cliente,ubicacion=ubicacion)

        databaseReference?.child(selectedId.toString())?.setValue(servicios)
    }

    private fun initRecyclerView(){
        adapter = ServiciosAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ServiceActivity)
            recyclerView.adapter = adapter

        }
        adapter?.setOnClickView {
            binding.edCodigo.setText(it.codigo.toString())
            binding.edCliente.setText(it.cliente.toString())
            binding.edUbicacion.setText(it.ubicacion.toString())
            selectedId = it.id
        }

        adapter?.setOnClickDelete {
            selectedId = it.id
            databaseReference?.child(selectedId.orEmpty())?.removeValue()
        }

    }

    private fun saveData(){
        val codigo = binding.edCodigo.text.toString()
        val cliente = binding.edCliente.text.toString()
        val ubicacion = binding.edUbicacion.text.toString()
        val servicios = Servicios(codigo=codigo, cliente=cliente,ubicacion=ubicacion)

        databaseReference?.child(getRandomString(2))?.setValue(servicios)
    }

    private fun getData(){
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                //Log.e("oooo","onDataChange: $snapshot")
                list.clear()
                for (ds in snapshot.children){
                    val id = ds.key
                    val codigo = ds.child("codigo").value.toString()
                    val cliente = ds.child("cliente").value.toString()
                    val ubicacion = ds.child("ubicacion").value.toString()

                    val servicios = Servicios(codigo=codigo, cliente=cliente,ubicacion=ubicacion)
                    list.add(servicios)
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
