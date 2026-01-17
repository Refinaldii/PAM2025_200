package com.example.e_kos.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyHelper {

    fun formatRupiah(amount: Int): String {
        val localeID = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(localeID)
        formatter.maximumFractionDigits = 0
        return formatter.format(amount)
    }
}
