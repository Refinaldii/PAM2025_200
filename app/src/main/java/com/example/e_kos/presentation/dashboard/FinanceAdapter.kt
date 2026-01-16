package com.example.e_kos.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_kos.data.model.Finance
import com.example.e_kos.databinding.ItemFinanceBinding

class FinanceAdapter(
    private val list: List<Finance>
) : RecyclerView.Adapter<FinanceAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFinanceBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinanceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvJudul.text = data.judul
        holder.binding.tvJumlah.text = data.jumlah.toString()
        holder.binding.tvTipe.text = data.tipe
    }

    override fun getItemCount() = list.size
}
