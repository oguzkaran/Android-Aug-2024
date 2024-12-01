package org.csystem.app.android.basicviews.model

import java.time.LocalDateTime

data class LoginInfoModel(var username: String, var password: String,
                          var dateTime: LocalDateTime = LocalDateTime.now())