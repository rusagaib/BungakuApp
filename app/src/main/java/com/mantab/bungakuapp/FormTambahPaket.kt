package com.mantab.bungakuapp

//import android.app.ProgressDialog
//import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.mantab.bungakuapp.adapter.*
import com.mantab.bungakuapp.databinding.ActivityFormTambahPaketBinding


class FormTambahPaket : AppCompatActivity(), HeroListener {

    var urlbid: String = ""
    var id: String? = null
    private lateinit var adapter: HeroAdapter

    lateinit var ref: DatabaseReference
    private lateinit var binding: ActivityFormTambahPaketBinding

    val listHeroes = mutableListOf<dataIco>(
        dataIco(
            "pepe",
            "https://spng.pngfly.com/20180420/ccq/kisspng-flower-bouquet-cut-flowers-rose-floristry-camellia-vector-5ad9918630e988.9134934215242080062004.jpg"
        ),
        dataIco(
            "pepe1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxAtmZNVis82s-_vctVIezFWVRlFhPYMTyogqGkbJRG50sz27L&s"
        ),
        dataIco(
            "pepe2",
            "https://png.pngtree.com/png-vector/20190114/ourmid/pngtree-watercolor-flowers-green-leaf-flower-hand-painted-flowers-hand-painted-flowers-png-image_330026.jpg"
        ),
        dataIco(
            "pepe3",
            "https://png.pngtree.com/png-clipart/20190117/ourmid/pngtree-watercolor-flowers-flower-plant-png-image_442077.jpg"
        )
    )

    override fun onHeroClick(hero: dataIco) {
        Toast.makeText(this, "hero clicked - ${hero.text}", Toast.LENGTH_LONG).show()
        Log.i("Image_", hero.text)
        urlbid = hero.image
        binding.txtBidurl.text = urlbid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTambahPaketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ref = FirebaseDatabase.getInstance().getReference("paket")

        binding.apply {
            rvMain.layoutManager =
                LinearLayoutManager(this@FormTambahPaket, LinearLayoutManager.HORIZONTAL, false)
            adapter = HeroAdapter(listHeroes, this@FormTambahPaket)
            rvMain.adapter = adapter
        }
        binding.submitButton.setOnClickListener {
            savedata()
            val intent = Intent(this, show::class.java)
            startActivity(intent)
        }
    }

    private fun savedata() {
        binding.apply {
            val namaP = edNamapaket.text.toString()
            val hargaP = edHarga.text.toString()
            val desP = edDes.text.toString()
            val urlP = txtBidurl.text.toString()
            val userId = ref.push().key.toString()

            val user = isiPaket(namaP, urlP, hargaP, desP, userId)

            ref.child(userId).setValue(user).addOnCompleteListener {
                Toast.makeText(this@FormTambahPaket, "Successs", Toast.LENGTH_SHORT).show()
                clearField()
            }
        }
    }

    fun clearField() {
        binding.apply {
            edNamapaket.setText("")
            edHarga.setText("")
            edDes.setText("")
            txtBidurl.setText("")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
//        val bundle = intent.extras
        intent = Intent(this, show::class.java)
        intent.extras
        startActivity(intent)
        return true
    }
}
