package com.priyanshparekh.plated

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.plated.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHost = binding.navHostFragment
        Log.d("TAG", "onCreate: navHost: $navHost")
        Log.d("TAG", "onCreate: navHost id: ${navHost.id}")

        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        binding.homeBottomNav.setupWithNavController(navController)

        val fragmentsToHideBottomNav = arrayOf(
            R.id.detailsFragment,
            R.id.ingredientsFragment,
            R.id.stepsFragment,
            R.id.reviewFragment,
            R.id.viewRecipeFragment
        )

        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                 in fragmentsToHideBottomNav -> {
                    Log.d("TAG", "onCreate: currentDestination: ${navController.currentDestination?.displayName}")
                    binding.homeBottomNav.visibility = View.GONE
                }
                R.id.profileFragment -> {
                    val isMyProfile = args?.getBoolean(Constants.Args.KEY_IS_MY_PROFILE)
                    if (isMyProfile == false) {
                        binding.homeBottomNav.visibility = View.GONE
                    }
                }
                else -> {
                    binding.homeBottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
}