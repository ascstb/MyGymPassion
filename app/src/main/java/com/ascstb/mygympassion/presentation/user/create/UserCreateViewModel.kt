package com.ascstb.mygympassion.presentation.user.create

import androidx.databinding.Bindable
import com.ascstb.mygympassion.core.Session
import com.ascstb.mygympassion.core.simpleDateFormat
import com.ascstb.mygympassion.model.Person
import com.ascstb.mygympassion.presentation.base.BaseViewModel
import java.util.*

class UserCreateViewModel : BaseViewModel() {
    val person: Person
        get() = Person(
            id = Session.user!!.uid,
            name = name,
            lastName = lastName,
            gender = gender,
            dateOfBirth = dateOfBirth,
            height = height.toInt(),
            weight = currentWeight.toFloat(),
            goalWeight = goalWeight.toFloat()
        )

    @Bindable
    var name: String = ""

    @Bindable
    var lastName: String = ""

    var gender: String = ""

    val dobCalendar: Calendar = Calendar.getInstance()

    val dateOfBirth: String
        get() = simpleDateFormat.format(dobCalendar.time)

    @Bindable
    var height: String = ""

    @Bindable
    var currentWeight: String = ""

    @Bindable
    var goalWeight: String = ""
}