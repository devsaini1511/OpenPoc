package thedebug.dev.openpoc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OpenPocApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}