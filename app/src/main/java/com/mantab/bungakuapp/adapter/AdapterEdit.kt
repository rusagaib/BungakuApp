package com.mantab.bungakuapp.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.mantab.bungakuapp.R
import com.mantab.bungakuapp.show


@Suppress("UNREACHABLE_CODE", "NAME_SHADOWING")
class AdapterEdit(val mCtx: Context, val layoutResId: Int, val list: List<isiPaket> )
    : ArrayAdapter<isiPaket>(mCtx,layoutResId,list){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.txt_paket)
        val textHarga = view.findViewById<TextView>(R.id. txt_harga)
        val textDes = view.findViewById<TextView>(R.id. txt_des)
        val GambarUrl = view.findViewById<ImageView>(R.id.gambar)
        val bindUrlGambar = view.findViewById<TextView>(R.id.txt_bindambar)

        val textUpdate = view.findViewById<Button>(R.id.TextUpdate)
        val textDelete = view.findViewById<ImageView>(R.id.TextDelete)

        val user = list[position]

        Glide.with(context)
            .load(user.url)
            .into(GambarUrl)

        bindUrlGambar.text = user.url

        textNama.text = user.namapaket
        textHarga.text = user.harga
        textDes.text = user.diskripsi

        textUpdate.setOnClickListener {
            showUpdateDialog(user)
        }
        textDelete.setOnClickListener {
            Deleteinfo(user)
        }

        return view
    }

    private fun Deleteinfo(user: isiPaket) {
        val buil = androidx.appcompat.app.AlertDialog.Builder(context)
        buil.setTitle("Delete Paket?")
        buil.setPositiveButton("Ya",{ dialog: DialogInterface?, i: Int ->
            val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Deleting...")
            progressDialog.show()
            val mydatabase = FirebaseDatabase.getInstance().getReference("paket")
            mydatabase.child(user.id).removeValue()
            Toast.makeText(mCtx,"Deleted!!",Toast.LENGTH_SHORT).show()
            val intent = Intent(context, show::class.java)
            context.startActivity(intent)
        })
        buil.setNegativeButton("Tidak",{ dialog: DialogInterface?, i: Int -> })
        buil.show()
    }

    private fun showUpdateDialog(user: isiPaket) {
        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update")
        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update, null)

        val textNamaP = view.findViewById<EditText>(R.id.inputNamaPak)
        val textHargaP = view.findViewById<EditText>(R.id.inputHargaPak)
        val textDesP = view.findViewById<EditText>(R.id.inputDesPak)
//            val textUrlP = user.url
        val textUrlP = view.findViewById<EditText>(R.id.inputBindUrl)

        textNamaP.setText(user.namapaket)
        textHargaP.setText(user.harga)
        textDesP.setText(user.diskripsi)
        textUrlP.setText(user.url)

        builder.setView(view)

        builder.setPositiveButton("Update") { dialog, which ->

            val dbUsers = FirebaseDatabase.getInstance().getReference("paket")

            val namapaK = textNamaP.text.toString().trim()
            val urlpaK = textUrlP.text.toString().trim()
            val hargapaK = textHargaP.text.toString().trim()
            val despaK = textDesP.text.toString().trim()

            if (namapaK.isEmpty()){
                textNamaP.error = "please enter name"
                textNamaP.requestFocus()
                return@setPositiveButton
            }

            if (urlpaK.isEmpty()){
                return@setPositiveButton
            }

            if (hargapaK.isEmpty()){
                textHargaP.error = "please enter name"
                textHargaP.requestFocus()
                return@setPositiveButton
            }

            if (despaK.isEmpty()){
                textDesP.error = "please enter name"
                textDesP.requestFocus()
                return@setPositiveButton
            }

            val user = isiPaket(namapaK,urlpaK,hargapaK,despaK,user.id)

            dbUsers.child(user.id).setValue(user).addOnCompleteListener {
                Toast.makeText(mCtx,"Updated",Toast.LENGTH_SHORT).show()
            }

        }

        builder.setNegativeButton("No") { dialog, which ->

        }

        val alert = builder.create()
        alert.show()
    }

}