package com.example.testweork

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweork.databinding.ItemNumberBinding

import com.example.testweork.db.NumberFact

class RvAdapter(private var list: MutableList<NumberFact>): RecyclerView.Adapter<RvAdapter.NumberViewHolder>() {

    class NumberViewHolder(var binding: ItemNumberBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val numberFact = list[position]

       holder.binding.cl.setOnClickListener {
           val context = holder.binding.cl.context
           val intent = Intent(context, MainActivity2::class.java)
           intent.putExtra("numberFact",numberFact)
           context.startActivity(intent)
       }
        if (numberFact.fact.length > 30) {
            val splitString = numberFact.fact.substring(0, 30) + "..."
            holder.binding.textViewText.text = splitString
        } else{
            holder.binding.textViewText.text =numberFact.fact
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list:List<NumberFact>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}