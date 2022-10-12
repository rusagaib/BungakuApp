package com.mantab.bungakuapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mantab.bungakuapp.databinding.LvIcoBinding
import com.squareup.picasso.Picasso


class HeroAdapter(
    val heroes: MutableList<dataIco>,
    val listener: HeroListener
) : RecyclerView.Adapter<HeroHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): HeroHolder {
        return HeroHolder(LvIcoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun getItemCount(): Int = heroes.size

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        val pos = heroes.get(position)
        holder.bindHero(pos, listener)
    }
}

interface HeroListener {
    fun onHeroClick(hero: dataIco)
}

class HeroHolder(private val binding: LvIcoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindHero(hero: dataIco, listener: HeroListener) {
        binding.imgbind.text = hero.text
        Picasso.get().load(hero.image).into(binding.ico)

        itemView.setOnClickListener { listener.onHeroClick(hero) }
    }
}




