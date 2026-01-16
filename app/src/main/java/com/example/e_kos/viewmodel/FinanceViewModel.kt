package com.example.e_kos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_kos.data.model.Finance
import com.example.e_kos.data.repository.FinanceRepository

class FinanceViewModel : ViewModel() {

    // 🔥 INI YANG TADI HILANG
    private val repository = FinanceRepository()

    // LIST DATA
    private val _financeList = MutableLiveData<List<Finance>>()
    val financeList: LiveData<List<Finance>> = _financeList

    private val _totalMasuk = MutableLiveData<Int>()
    val totalMasuk: LiveData<Int> = _totalMasuk

    private val _totalKeluar = MutableLiveData<Int>()
    val totalKeluar: LiveData<Int> = _totalKeluar



    // SALDO
    private val _saldo = MutableLiveData<Int>()
    val saldo: LiveData<Int> = _saldo

    fun loadFinance() {
        repository.getAllFinance { list ->
            _financeList.value = list

            var masuk = 0
            var keluar = 0

            list.forEach {
                if (it.tipe.uppercase() == "MASUK") {
                    masuk += it.jumlah
                } else if (it.tipe.uppercase() == "KELUAR") {
                    keluar += it.jumlah
                }
            }

            _totalMasuk.value = masuk
            _totalKeluar.value = keluar
            _saldo.value = masuk - keluar
        }
    }


    fun addFinance(finance: Finance) {
        repository.addFinance(finance) {
            loadFinance()
        }
    }

    fun updateFinance(finance: Finance) {
        repository.updateFinance(finance) {
            loadFinance()
        }
    }

    fun deleteFinance(id: String) {
        repository.deleteFinance(id) {
            loadFinance()
        }
    }

}
