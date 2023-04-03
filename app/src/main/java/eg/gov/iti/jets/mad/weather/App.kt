package eg.gov.iti.jets.mad.weather

import android.app.Application
import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration

class App : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
       // LocaleManager.setLocale(this)
    }
}