package com.example.e_kos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_kos.data.model.Finance
import com.example.e_kos.data.repository.FinanceRepository

class FinanceViewModel : ViewModel() {

    private val repo = FinanceRepository()

    private val _financeList = MutableLiveData<List<Finance>>()
    val financeList: LiveData<List<Finance>> = _financeList

    fun loadFinance() {
        repo.getAllFinance {
            _financeList.value = it
        }
    }

    fun addFinance(finance: Finance) {
        repo.addFinance(finance) {
            loadFinance()
        }
    }
}
