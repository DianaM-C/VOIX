package com.danmc.voixsac.ui.servicework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.danmc.voixsac.databinding.ActivityServiceWorkListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ServiceWorkerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceWorkListBinding
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var list = mutableListOf<Trabajadores>()

    private var adapter: TrabajadoresAdapter? = null
    private var selectedId:String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceWorkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("trabajadores")
        getData()

        binding.btnSave.setOnClickListener{saveData()}
        binding.btnUpdate.setOnClickListener{updateData()}
    }

    private fun updateData(){
        val nombre = binding.edNombre.text.toString()
        val apellido = binding.edApellido.text.toString()
        val correo = binding.edCorreo.text.toString()
        val contrasena = binding.edContrasena.text.toString()
        val dni = binding.edDni.text.toString()
        val rol = binding.edRol.text.toString()
        val trabajadores = Trabajadores(nombre=nombre, apellido = apellido, correo = correo, contrasena = contrasena, dni = dni, rol = rol)

        databaseReference?.child(selectedId.toString())?.setValue(trabajadores)
    }

    private fun initRecyclerView(){
        adapter = TrabajadoresAdapter()
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@ServiceWorkerActivity)
            recyclerView.adapter = adapter

        }
        adapter?.setOnClickView {
            binding.edNombre.setText(it.nombre.toString())
            binding.edApellido.setText(it.apellido.toString())
            binding.edCorreo.setText(it.correo.toString())
            binding.edContrasena.setText(it.contrasena.toString())
            binding.edDni.setText(it.dni.toString())
            binding.edRol.setText(it.rol.toString())
            selectedId = it.id
        }

        adapter?.setOnClickDelete {
            selectedId = it.id
            databaseReference?.child(selectedId.orEmpty())?.removeValue()
        }

    }

    private fun saveData(){
        val nombre = binding.edNombre.text.toString()
        val apellido = binding.edApellido.text.toString()
        val correo = binding.edCorreo.text.toString()
        val contrasena = binding.edContrasena.text.toString()
        val dni = binding.edDni.text.toString()
        val rol = binding.edRol.text.toString()
        val trabajadores = Trabajadores(nombre=nombre, apellido = apellido, correo = correo, contrasena = contrasena, dni = dni, rol = rol)

        databaseReference?.child(getRandomString(3))?.setValue(trabajadores)
    }


    private fun getData(){
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                //Log.e("oooo","onDataChange: $snapshot")
                list.clear()
                for (ds in snapshot.children){
                    val id = ds.key
                    val nombre = ds.child("nombre").value.toString()
                    val apellido = ds.child("apellido").value.toString()
                    val correo = ds.child("correo").value.toString()
                    val contrasena = ds.child("contraseña").value.toString()
                    val dni = ds.child("dni").value.toString()
                    val rol = ds.child("rol").value.toString()

                    val trabajadores = Trabajadores(id=id, nombre=nombre, apellido=apellido, dni=dni, correo=correo, contrasena=contrasena, rol=rol)
                    list.add(trabajadores)
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
