package com.example.e_kos.presentation.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.e_kos.R
import com.example.e_kos.data.model.Finance
import com.example.e_kos.viewmodel.FinanceViewModel
import android.widget.Toast


class AddFinanceActivity : AppCompatActivity() {

    private val viewModel: FinanceViewModel by viewModels()
    private var financeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_finance)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)
        val etTipe = findViewById<EditText>(R.id.etTipe)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            if (etJudul.text.isNullOrEmpty() || etJumlah.text.isNullOrEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = Finance(
                judul = etJudul.text.toString(),
                jumlah = etJumlah.text.toString().toInt(),
                tanggal = System.currentTimeMillis().toString(),
                tipe = etTipe.text.toString()
            )
            viewModel.addFinance(data)
            finish()
        }
        financeId = intent.getStringExtra("id")

        if (financeId != null) {
            etJudul.setText(intent.getStringExtra("judul"))
            etJumlah.setText(intent.getIntExtra("jumlah", 0).toString())
            etTipe.setText(intent.getStringExtra("tipe"))
        }
        btnSimpan.setOnClickListener {
            val data = Finance(
                id = financeId ?: "",
                judul = etJudul.text.toString(),
                jumlah = etJumlah.text.toString().toInt(),
                tanggal = System.currentTimeMillis().toString(),
                tipe = etTipe.text.toString()
            )

            if (financeId == null) {
                viewModel.addFinance(data)
            } else {
                viewModel.updateFinance(data)
            }
            finish()
        }

    }
}
