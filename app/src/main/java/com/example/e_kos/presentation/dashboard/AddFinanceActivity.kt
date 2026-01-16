package com.example.e_kos.presentation.dashboard

import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.e_kos.R
import com.example.e_kos.data.model.Finance
import com.example.e_kos.viewmodel.FinanceViewModel

class AddFinanceActivity : AppCompatActivity() {

    private val viewModel: FinanceViewModel by viewModels()
    private var financeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // === ACTION BAR (BACK BUTTON) ===
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tambah Catatan"

        setContentView(R.layout.activity_add_finance)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)
        val spinner = findViewById<Spinner>(R.id.spTipe)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // === SPINNER PUTIH (CUSTOM) ===
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.finance_type,
            R.layout.item_spinner
        )
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown)
        spinner.adapter = adapter

        // === MODE EDIT ===
        financeId = intent.getStringExtra("id")
        if (financeId != null) {
            etJudul.setText(intent.getStringExtra("judul"))
            etJumlah.setText(intent.getIntExtra("jumlah", 0).toString())

            val tipe = intent.getStringExtra("tipe")
            spinner.setSelection(if (tipe == "KELUAR") 1 else 0)
        }

        // === SIMPAN DATA ===
        btnSimpan.setOnClickListener {
            if (etJudul.text.isNullOrEmpty() || etJumlah.text.isNullOrEmpty()) {
                Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = Finance(
                id = financeId ?: "",
                judul = etJudul.text.toString(),
                jumlah = etJumlah.text.toString().toInt(),
                tanggal = System.currentTimeMillis().toString(),
                tipe = spinner.selectedItem.toString()
            )

            if (financeId == null) {
                viewModel.addFinance(data)
            } else {
                viewModel.updateFinance(data)
            }

            finish()
        }
    }

    // === BACK ARROW HANDLER ===
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
