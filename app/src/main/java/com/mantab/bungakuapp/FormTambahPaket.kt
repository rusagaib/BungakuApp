package com.mantab.bungakuapp

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
//import android.app.ProgressDialog
//import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.mantab.bungakuapp.adapter.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_form_tambah_paket.*
import kotlinx.android.synthetic.main.lv_ico.*
import java.util.HashMap

class FormTambahPaket : AppCompatActivity() , HeroListener {

    var urlbid: String = ""
    var id: String? = null
    private lateinit var adapter: HeroAdapter

    lateinit var ref : DatabaseReference

    val listHeroes = mutableListOf<dataIco>(
        dataIco("pepe", "https://spng.pngfly.com/20180420/ccq/kisspng-flower-bouquet-cut-flowers-rose-floristry-camellia-vector-5ad9918630e988.9134934215242080062004.jpg"),
        dataIco("pepe1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxAtmZNVis82s-_vctVIezFWVRlFhPYMTyogqGkbJRG50sz27L&s"),
        dataIco("pepe2", "https://png.pngtree.com/png-vector/20190114/ourmid/pngtree-watercolor-flowers-green-leaf-flower-hand-painted-flowers-hand-painted-flowers-png-image_330026.jpg"),
        dataIco("pepe3", "https://png.pngtree.com/png-clipart/20190117/ourmid/pngtree-watercolor-flowers-flower-plant-png-image_442077.jpg")
    )

    override fun onHeroClick(hero: dataIco) {
        Toast.makeText(this, "hero clicked - ${hero.text}", Toast.LENGTH_LONG).show()
        Log.i("Image_",hero.text)
        urlbid = hero.image
        txt_bidurl.text = urlbid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_tambah_paket)

        ref = FirebaseDatabase.getInstance().getReference("paket")

        val heroList = findViewById<RecyclerView>(R.id.rvMain)
        heroList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = HeroAdapter(listHeroes, this)
        heroList.adapter = adapter

        submit_button.setOnClickListener {
            savedata()
            val intent = Intent (this, show::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        val namaP = ed_namapaket.text.toString()
        val hargaP = ed_harga.text.toString()
        val desP = ed_des.text.toString()
        val urlP = txt_bidurl.text.toString()
        val userId = ref.push().key.toString()

        val user = isiPaket(namaP,urlP,hargaP,desP,userId)

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Successs",Toast.LENGTH_SHORT).show()
            clearField()
        }
    }

    fun clearField(){
        ed_namapaket.setText("")
        ed_harga.setText("")
        ed_des.setText("")
        txt_bidurl.setText("")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val bundle = intent.extras
        intent = Intent(this, show::class.java)
        startActivity(intent)
        return true
    }
}
