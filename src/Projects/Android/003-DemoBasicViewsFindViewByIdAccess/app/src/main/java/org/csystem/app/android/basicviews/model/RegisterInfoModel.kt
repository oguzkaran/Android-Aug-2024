package org.csystem.app.android.basicviews.model

import java.io.Serializable

data class RegisterInfoModel(
    var username: String = "", var name: String = "", var email: String = "",
    var maritalStatus: Char = 'S', var lastEducation: Int = 0, var password: String = "") : Serializable