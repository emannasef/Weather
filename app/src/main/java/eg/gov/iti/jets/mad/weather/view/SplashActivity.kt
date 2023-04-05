package eg.gov.iti.jets.mad.weather.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import eg.gov.iti.jets.mad.weather.MainActivity
import eg.gov.iti.jets.mad.weather.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
        }
    }
}