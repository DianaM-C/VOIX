package com.danmc.voixsac.ui.gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danmc.voixsac.R

class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val images = listOf<Image>(
            Image("Images 1",R.drawable.img1),
            Image("Images 2",R.drawable.img2),
            Image("Images 3",R.drawable.img3),
            Image("Images 4",R.drawable.img4),
            Image("Images 5",R.drawable.img5),
            Image("Images 6",R.drawable.img6),
            Image("Images 7",R.drawable.img7),
            Image("Images 8",R.drawable.img8)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.imagesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ImageAdapter(this,images)
    }
}