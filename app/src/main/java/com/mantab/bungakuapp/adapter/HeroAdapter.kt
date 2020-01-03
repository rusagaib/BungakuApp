package com.mantab.bungakuapp.adapter

import android.content.Context
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.google.android.material.snackbar.Snackbar
import androidx.cardview.widget.CardView
import com.mantab.bungakuapp.R
import kotlinx.android.synthetic.main.lv_ico.view.*
import com.mantab.bungakuapp.adapter.dataIco


class HeroAdapter(val heroes: MutableList<dataIco>,
                  val listener: HeroListener) : RecyclerView.Adapter<HeroHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HeroHolder {
        return HeroHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.lv_ico, viewGroup, false))
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val pos = heroes.get(position)
        holder.bindHero(pos, listener)
//        val pos = heroes.get(position)

    }
}

interface HeroListener {
    fun onHeroClick(hero: dataIco)
}

class HeroHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvHeroName = view.imgbind
    private val imgHero = view.ico

    fun bindHero(hero: dataIco, listener: HeroListener) {
        tvHeroName.text = hero.text
        Picasso.get().load(hero.image).into(imgHero)

        itemView.setOnClickListener { listener.onHeroClick(hero) }
    }
}




