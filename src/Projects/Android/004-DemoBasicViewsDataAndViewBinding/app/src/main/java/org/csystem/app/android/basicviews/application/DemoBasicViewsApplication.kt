package org.csystem.app.android.basicviews.application

import android.app.Application
import org.csystem.app.android.basicviews.constant.USERS
import java.io.File

class DemoBasicViewsApplication : Application() {
    override fun onCreate() {
        File(filesDir, USERS).mkdirs()
        super.onCreate()
    }
}