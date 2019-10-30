package com.ascstb.mygympassion.repository

import com.ascstb.mygympassion.core.PERSON_DATABASE_REFERENCE
import com.ascstb.mygympassion.model.Person
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

object FirebaseDBManager {
    private val database = FirebaseDatabase.getInstance()

    fun getPerson(personId: String, onDone: (Person?) -> Unit) = GlobalScope.launch {
        Timber.d("FirebaseDBManager_TAG: getPerson: $personId")
        try {
            val personDBRef = database.getReference(PERSON_DATABASE_REFERENCE)

            personDBRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Timber.d("FirebaseDBManager_TAG: onCancelled: $databaseError")
                    onDone(null)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Timber.d("FirebaseDBManager_TAG: onDataChange: ")
                    dataSnapshot.children.forEach { item ->
                        if (item.child("id").value == personId) {
                            val person = item.getValue(Person::class.java)
                            onDone(person)
                            return
                        }
                    }

                    onDone(null)
                }
            })
        } catch (e: Exception) {
            Timber.d("FirebaseDBManager_TAG: getPerson: ${e.message}")
            onDone(null)
        }
    }

    fun createPerson(person: Person, onDone: (Boolean, String) -> Unit) {
        Timber.d("FirebaseDBManager_TAG: createPerson: ")
        //database.setPersistenceEnabled(true)

        val personDBRef = database.getReference(PERSON_DATABASE_REFERENCE)
        personDBRef.push().setValue(person).addOnCompleteListener {
            it.exception?.let { e ->
                e.message?.let { msg -> onDone(false, msg) }
                    ?: onDone(false, e.localizedMessage)
            } ?: onDone(true, "")
        }.addOnFailureListener {
            it.message?.let { msg ->
                onDone(false, msg)
            } ?: onDone(false, it.localizedMessage)
        }
    }
}