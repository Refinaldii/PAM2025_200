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


class DashboardActivity : AppCompatActivity() {

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // ✅ CEK LOGIN (BENAR)
        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // 🔴 LOGOUT
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // ➕ TAMBAH CATATAN
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(Intent(this, AddFinanceActivity::class.java))
        }

        // 📋 RECYCLERVIEW
        val rv = findViewById<RecyclerView>(R.id.rvFinance)
        rv.layoutManager = LinearLayoutManager(this)

        viewModel.financeList.observe(this) { list ->
            rv.adapter = FinanceAdapter(
                list,
                onEdit = { data ->
                    val intent = Intent(this, AddFinanceActivity::class.java)
                    intent.putExtra("id", data.id)
                    intent.putExtra("judul", data.judul)
                    intent.putExtra("jumlah", data.jumlah)
                    intent.putExtra("tipe", data.tipe)
                    startActivity(intent)
                },
                onDelete = { data ->
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Catatan")
                        .setMessage("Yakin mau hapus catatan ini?")
                        .setPositiveButton("Hapus") { _, _ ->
                            viewModel.deleteFinance(data.id)
                        }
                        .setNegativeButton("Batal", null)
                        .show()
                }
            )
        }
        viewModel.saldo.observe(this) { total ->
            findViewById<TextView>(R.id.tvBalance).text = "Rp $total"
        }

        viewModel.totalMasuk.observe(this) {
            findViewById<TextView>(R.id.tvIncome).text = "Masuk: Rp $it"
        }

        viewModel.totalKeluar.observe(this) {
            findViewById<TextView>(R.id.tvExpense).text = "Keluar: Rp $it"
        }

        viewModel.saldo.observe(this) {
            findViewById<TextView>(R.id.tvBalance).text = "Rp $it"
        }



        viewModel.loadFinance()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFinance()
    }
}
