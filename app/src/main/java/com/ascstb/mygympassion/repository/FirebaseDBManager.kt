package com.ascstb.mygympassion.repository

import com.ascstb.mygympassion.core.PERSON_DATABASE_REFERENCE
import com.ascstb.mygympassion.core.RULES_ACCEPTANCE_DATABASE_REFERENCE
import com.ascstb.mygympassion.core.RULES_DATABASE_REFERENCE
import com.ascstb.mygympassion.model.Person
import com.ascstb.mygympassion.model.RulesAcceptance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

object FirebaseDBManager {
    private val database = FirebaseDatabase.getInstance()

    //region Person
    fun getPersonAsync(personId: String, onDone: (Person?) -> Unit) = GlobalScope.launch {
        Timber.d("FirebaseDBManager_TAG: getPersonAsync: $personId")
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
            Timber.d("FirebaseDBManager_TAG: getPersonAsync: ${e.message}")
            onDone(null)
        }
    }

    fun createPerson(person: Person, onDone: (Boolean, String) -> Unit) {
        Timber.d("FirebaseDBManager_TAG: createPerson: ")

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
    //endregion

    //region Terms
    fun getRulesAcceptanceAsync(personId: String, onDone: (RulesAcceptance?) -> Unit) =
        GlobalScope.launch {
            Timber.d("FirebaseDBManager_TAG: getRulesAcceptance: ")

            try {
                val rulesDBRef = database.getReference(RULES_ACCEPTANCE_DATABASE_REFERENCE)

                rulesDBRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        Timber.d("FirebaseDBManager_TAG: onCancelled: ")
                        onDone(null)
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Timber.d("FirebaseDBManager_TAG: onDataChange: ")
                        dataSnapshot.children.forEach { item ->
                            if (item.child("personId").value == personId) {
                                val rulesAcceptance = item.getValue(RulesAcceptance::class.java)
                                onDone(rulesAcceptance)
                                return
                            }
                        }

                        onDone(null)
                    }
                })

            } catch (e: Exception) {
                Timber.d("FirebaseDBManager_TAG: getRulesAcceptance: exception: ${e.message}")
                onDone(null)
            }
        }

    fun getRulesContentAsync(onDone: (String?) -> Unit) = GlobalScope.launch {
        Timber.d("FirebaseDBManager_TAG: getRulesContent: ")

        try {
            val rulesDBRef = database.getReference(RULES_DATABASE_REFERENCE)

            rulesDBRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Timber.d("FirebaseDBManager_TAG: onCancelled: ")
                    onDone(null)
                }

                override fun onDataChange(rulesHtml: DataSnapshot) {
                    Timber.d("FirebaseDBManager_TAG: onDataChange: ")
                    val rules = rulesHtml.value.toString()
                    onDone(rules)
                    return
                }
            })

        } catch (e: Exception) {
            Timber.d("FirebaseDBManager_TAG: getRulesContent: exception: ${e.message}")
            onDone(null)
        }
    }

    fun acceptRules(rulesAcceptance: RulesAcceptance, onDone: (Boolean, String) -> Unit) {
        Timber.d("FirebaseDBManager_TAG: acceptRules: ")
        val rulesDBRef = database.getReference(RULES_ACCEPTANCE_DATABASE_REFERENCE)

        rulesDBRef.push().setValue(rulesAcceptance).addOnCompleteListener {
            it.exception?.let { e ->
                e.message?.let { msg ->
                    onDone(false, msg)
                } ?: onDone(false, e.localizedMessage)
            } ?: onDone(true, "")
        }.addOnFailureListener {
            it.message?.let { msg ->
                onDone(false, msg)
            } ?: onDone(false, it.localizedMessage)
        }
    }
    //endregion
}