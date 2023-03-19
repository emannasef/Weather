package eg.gov.iti.jets.mad.weather

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import eg.gov.iti.jets.mad.weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var drawer: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawer = binding.drawerLayout
        navigationView = binding.navigatorLayout

        val actionBar = supportActionBar

        actionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var navController = findNavController(this, R.id.nav_host_fragment)

        setupWithNavController(navigationView, navController)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}