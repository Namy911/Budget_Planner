package com.endava.budgetplanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.endava.budgetplanner.databinding.ActivityMainBinding

private const val NAVIGATION_KEY = "navigation_key"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        val navigateToLogin = intent.getBooleanExtra(NAVIGATION_KEY, false)
        if (navigateToLogin)
            navController?.navigate(R.id.action_global_loginFragment)
        setContentView(binding.root)

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            binding.appBar.isVisible =
                destination.id != R.id.registerFragment && destination.id != R.id.loginFragment
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        navController = null
    }

    override fun onBackPressed() {
        if (isStartDestination(navController?.currentDestination)) {
            finish()
        } else {
            navController?.popBackStack()
        }
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        return destination.id == R.id.registerFragment || destination.id == R.id.loginFragment
    }

    companion object {

        @JvmStatic
        fun newIntent(context: Context, navigateToLogin: Boolean) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(NAVIGATION_KEY, navigateToLogin)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
    }
}