package org.csystem.app.android.basicviews.model

import java.io.Serializable

data class UserModel(
    var username: String = "", var name: String = "", var email: String = "",
    var maritalStatus: Char = 'S', var lastEducation: Int = 0) : Serializable {
        override fun toString() = username
    }