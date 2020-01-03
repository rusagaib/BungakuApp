package com.mantab.bungakuapp

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.mantab.bungakuapp.adapter.AdapterEdit
import com.mantab.bungakuapp.adapter.isiPaket
import kotlinx.android.synthetic.main.activity_show.*

class show : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<isiPaket>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );
        setContentView(R.layout.activity_show)

        ref = FirebaseDatabase.getInstance().getReference("paket")
        list = mutableListOf()
        listView = findViewById(R.id.listView)

        val loading = ProgressDialog(this)
        loading.setMessage("Tunggu Sebentar...")
        loading.show()

        btn_insert.setOnClickListener{
            intent = Intent(this, FormTambahPaket::class.java)
            startActivity(intent)
        }

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){

                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(isiPaket::class.java)
                        list.add(user!!)
                    }



                    val adapter = AdapterEdit(this@show,R.layout.view_design,list)
                    listView.adapter = adapter
                    loading.dismiss()
                }
            }
        })

    }
    override fun onBackPressed() {
        val buil = AlertDialog.Builder(this)
        buil.setTitle("Yakin Ingin Keluar Aplikasi ?")
        buil.setPositiveButton("Ya",{ dialog: DialogInterface?, i: Int -> finishAffinity() })
        buil.setNegativeButton("Tidak",{dialog: DialogInterface?, i: Int -> })
        buil.show()
    }
}
