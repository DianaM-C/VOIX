package com.danmc.voixsac.ui.servicework
/*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danmc.voixsac.databinding.FragmentServiceWorkListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * A fragment representing a list of Items.
 */
class ServiceWorkerFragment : AppCompatActivity() {
    private lateinit var binding: FragmentServiceWorkListBinding
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var list = mutableListOf<Trabajadores>()

    private var adapter: TrabajadoresAdapter? = null
    private var selectedId:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentServiceWorkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        firebaseDatabase = firebaseDatabase.getInstance()
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
            recyclerView.layoutManager =LinearLayoutManager(this@ServiceWorkerFragment)
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

        databaseReference?.child(getRandomString(2))?.setValue(trabajadores)
    }


    private fun getData(){
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                //Log.e("0000","onDataChange: $snapshot")
                for (ds in snapshot.children){
                    val id = ds.key
                    val nombre = ds.child("nombre").value.toString()
                    val apellido = ds.child("apellido").value.toString()
                    val dni = ds.child("dni").value.toString()
                    val correo = ds.child("correo").value.toString()
                    val contrasena = ds.child("contraseña").value.toString()
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

   /* fun getRandomString(length:Int) : String{
        val allowedChars = ('A'..'Z')+('a'..'z')+('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }*/
  fun FirebaseDatabase?.getInstance(): FirebaseDatabase? {

    return TODO("Provide the return value")
}

/* private var columnCount = 1

 override fun onCreate(savedInstanceState: Bundle?) {
     super.onCreate(savedInstanceState)

     arguments?.let {
         columnCount = it.getInt(ARG_COLUMN_COUNT)
     }
 }

 override fun onCreateView(
     inflater: LayoutInflater, container: ViewGroup?,
     savedInstanceState: Bundle?
 ): View? {
     val view = inflater.inflate(R.layout.fragment_service_work_list, container, false)

     // Set the adapter
     if (view is RecyclerView) {
         with(view) {
             layoutManager = when {
                 columnCount <= 1 -> LinearLayoutManager(context)
                 else -> GridLayoutManager(context, columnCount)
             }
             adapter = MyServiceWorkerRecyclerViewAdapter(PlaceholderContent.ITEMS)
         }
     }
     return view
 }

 companion object {

     // TODO: Customize parameter argument names
     const val ARG_COLUMN_COUNT = "column-count"

     // TODO: Customize parameter initialization
     @JvmStatic
     fun newInstance(columnCount: Int) =
         ServiceWorkerFragment().apply {
             arguments = Bundle().apply {
                 putInt(ARG_COLUMN_COUNT, columnCount)
             }
         }
 }
}*/*/