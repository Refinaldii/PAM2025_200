package com.example.e_kos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_kos.data.model.Finance
import com.example.e_kos.data.repository.FinanceRepository

class FinanceViewModel : ViewModel() {

    private val repository = FinanceRepository()

    private val _financeList = MutableLiveData<List<Finance>>()
    val financeList: LiveData<List<Finance>> = _financeList

    private val _totalMasuk = MutableLiveData<Int>()
    val totalMasuk: LiveData<Int> = _totalMasuk

    private val _totalKeluar = MutableLiveData<Int>()
    val totalKeluar: LiveData<Int> = _totalKeluar

    private val _saldo = MutableLiveData<Int>()
    val saldo: LiveData<Int> = _saldo

    fun loadFinance() {
        repository.getAllFinance { list ->
            _financeList.value = list

            var masuk = 0
            var keluar = 0

            list.forEach { data ->
                when (data.tipe.uppercase()) {
                    "MASUK" -> masuk += data.jumlah
                    "KELUAR" -> keluar += data.jumlah
                }
            }

            _totalMasuk.value = masuk
            _totalKeluar.value = keluar
            _saldo.value = masuk - keluar
        }
    }

    fun addFinance(finance: Finance) {
        repository.addFinance(finance)
        loadFinance()
    }

    fun updateFinance(finance: Finance) {
        repository.updateFinance(finance)
        loadFinance()
    }

    fun deleteFinance(id: String) {
        repository.deleteFinance(id)
        loadFinance()
    }
}
