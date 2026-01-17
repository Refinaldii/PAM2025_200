package com.example.e_kos.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_kos.R
import com.example.e_kos.presentation.auth.LoginActivity
import com.example.e_kos.viewmodel.FinanceViewModel
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView
import com.example.e_kos.utils.CurrencyHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import android.graphics.Color


class DashboardActivity : AppCompatActivity() {

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvIncome = findViewById<TextView>(R.id.tvIncome)
        val tvExpense = findViewById<TextView>(R.id.tvExpense)
        val tvBalance = findViewById<TextView>(R.id.tvBalance)
        val barChart = findViewById<BarChart>(R.id.barChart)

        // LOGOUT
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // TAMBAH
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddFinanceActivity::class.java))
        }

        // RECYCLERVIEW
        val rv = findViewById<RecyclerView>(R.id.rvFinance)
        rv.layoutManager = LinearLayoutManager(this)

        viewModel.financeList.observe(this) { list ->

            rv.adapter = FinanceAdapter(
                list,
                onEdit = { data ->
                    val i = Intent(this, AddFinanceActivity::class.java)
                    i.putExtra("id", data.id)
                    i.putExtra("judul", data.judul)
                    i.putExtra("jumlah", data.jumlah)
                    i.putExtra("tipe", data.tipe)
                    startActivity(i)
                },
                onDelete = { data ->
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Catatan")
                        .setMessage("Yakin mau hapus?")
                        .setPositiveButton("Hapus") { _, _ ->
                            viewModel.deleteFinance(data.id)
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }
            )

            // ===== BAR CHART (ALA BCA) =====
            var masuk = 0f
            var keluar = 0f

            list.forEach {
                if (it.tipe.uppercase() == "MASUK") masuk += it.jumlah
                else keluar += it.jumlah
            }

            val entries = listOf(
                BarEntry(0f, masuk),
                BarEntry(1f, keluar)
            )

            val dataSet = BarDataSet(entries, "")
            dataSet.colors = listOf(
                Color.parseColor("#22D3EE"),
                Color.parseColor("#EC4899")
            )
            dataSet.valueTextColor = Color.WHITE
            dataSet.valueTextSize = 13f

            val barData = BarData(dataSet)
            barChart.data = barData

            barChart.description.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.axisRight.isEnabled = false

            barChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(listOf("Masuk", "Keluar"))
                position = XAxis.XAxisPosition.BOTTOM
                textColor = Color.WHITE
                granularity = 1f
                setDrawGridLines(false)
            }

            barChart.axisLeft.textColor = Color.WHITE
            barChart.axisLeft.setDrawGridLines(false)

            barChart.invalidate()
        }

        viewModel.totalMasuk.observe(this) {
            tvIncome.text = CurrencyHelper.formatRupiah(it)
        }

        viewModel.totalKeluar.observe(this) {
            tvExpense.text = CurrencyHelper.formatRupiah(it)
        }

        viewModel.saldo.observe(this) {
            tvBalance.text = CurrencyHelper.formatRupiah(it)
        }

        viewModel.loadFinance()
    }



    override fun onResume() {
        super.onResume()
        viewModel.loadFinance()
    }
}
