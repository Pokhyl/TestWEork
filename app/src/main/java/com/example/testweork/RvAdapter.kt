package com.example.testweork

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweork.databinding.ItemNumberBinding
import com.example.testweork.db.DaoNumberFact
import com.example.testweork.db.NumberFact

class RvAdapter(var list: MutableList<NumberFact>): RecyclerView.Adapter<RvAdapter.NumberViewHolder>() {

    class NumberViewHolder(var binding: ItemNumberBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        var binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NumberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        var numberFact = list[position]

       holder.binding.cl.setOnClickListener {
           var context = holder.binding.cl.context
           var intent = Intent(context, MainActivity2::class.java)
           intent.putExtra("numberFact",numberFact)
           context.startActivity(intent)
       }
        holder.binding.textViewText.text = numberFact.fact
    }


    override fun getItemCount(): Int {
        return list.size
    }
    fun updateList(list:List<NumberFact>) {
        this.list.clear()
        this.list.addAll(list)


        notifyDataSetChanged()
    }
}