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

    // SALDO
    private val _saldo = MutableLiveData<Int>()
    val saldo: LiveData<Int> = _saldo

    fun loadFinance() {
        repository.getAllFinance { list ->
            _financeList.value = list

            var total = 0
            list.forEach { finance ->
                if (finance.tipe.uppercase() == "MASUK") {
                    total += finance.jumlah
                } else if (finance.tipe.uppercase() == "KELUAR") {
                    total -= finance.jumlah
                }
            }
            _saldo.value = total
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
