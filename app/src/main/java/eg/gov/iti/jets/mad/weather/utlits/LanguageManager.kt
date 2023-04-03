package eg.gov.iti.jets.mad.weather.utlits

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*


class LanguageManager(var context: Context) {

    fun updateResources(language:String): Context {
        var locale: Locale = Locale(language)
        Locale.setDefault(locale)

        var res: Resources = context.resources
        var config: Configuration = Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config)
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }



}