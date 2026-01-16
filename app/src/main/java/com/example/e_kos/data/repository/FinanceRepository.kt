package com.example.e_kos.data.repository

import com.example.e_kos.data.model.Finance
import com.google.firebase.firestore.FirebaseFirestore

class FinanceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("finance")

    fun addFinance(finance: Finance, callback: (Boolean) -> Unit) {
        val doc = collection.document()
        val newData = finance.copy(id = doc.id)
        doc.set(newData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun getAllFinance(callback: (List<Finance>) -> Unit) {
        collection.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = task.result.toObjects(Finance::class.java)
                    callback(list)
                } else {
                    callback(emptyList())
                }
            }
    }

    fun updateFinance(finance: Finance, callback: (Boolean) -> Unit) {
        collection.document(finance.id)
            .set(finance)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }

    fun deleteFinance(id: String, callback: (Boolean) -> Unit) {
        collection.document(id)
            .delete()
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }


}
