package com.ascstb.mygympassion.core

import com.ascstb.mygympassion.model.Person
import com.ascstb.mygympassion.model.RulesAcceptance
import com.google.firebase.auth.FirebaseUser

object Session  {
    var user: FirebaseUser? = null
    var person: Person? = null
    var rulesAcceptance: RulesAcceptance? = null
}