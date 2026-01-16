package com.example.e_kos.data.repository

import com.example.e_kos.data.model.Finance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FinanceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    private val collection
        get() = db.collection("users")
            .document(uid ?: "unknown")
            .collection("finance")

    fun addFinance(finance: Finance) {
        val doc = collection.document()
        val data = finance.copy(id = doc.id)
        doc.set(data)
    }

    fun getAllFinance(callback: (List<Finance>) -> Unit) {
        collection.get()
            .addOnSuccessListener {
                callback(it.toObjects(Finance::class.java))
            }
    }

    fun deleteFinance(id: String) {
        collection.document(id).delete()
    }

    fun updateFinance(finance: Finance) {
        collection.document(finance.id).set(finance)
    }
}
