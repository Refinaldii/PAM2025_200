package com.example.e_kos.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_kos.R
import com.example.e_kos.viewmodel.FinanceViewModel

class DashboardActivity : AppCompatActivity() {

    private val viewModel: FinanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // RecyclerView
        val rv = findViewById<RecyclerView>(R.id.rvFinance)
        rv.layoutManager = LinearLayoutManager(this)

        viewModel.financeList.observe(this) {
            rv.adapter = FinanceAdapter(it)
        }

        viewModel.loadFinance()

        // 🔥 BUTTON TAMBAH CATATAN
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            startActivity(
                Intent(this, AddFinanceActivity::class.java)
            )
        }
    }

    // biar pas balik dari AddFinance, data ke-refresh
    override fun onResume() {
        super.onResume()
        viewModel.loadFinance()
    }
}
