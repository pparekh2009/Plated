package com.priyanshparekh.plated.startup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.auth0.jwt.JWT
import com.priyanshparekh.plated.databinding.ActivitySplashBinding
import java.util.Date
import androidx.core.content.edit
import com.priyanshparekh.core.model.ingredient.AddIngredientsRequest
import com.priyanshparekh.core.model.recipe.IngredientDto
import com.priyanshparekh.core.navigation.NavigatorProvider
import com.priyanshparekh.core.network.Status
import com.priyanshparekh.core.utils.Constants
import com.priyanshparekh.feature.auth.ui.AuthActivity
import com.priyanshparekh.plated.HomeActivity
import com.priyanshparekh.plated.navigatorimpl.CommonNavigatorImpl
import com.priyanshparekh.plated.navigatorimpl.RecipeNavigatorImpl

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        NavigatorProvider.recipeNavigator = RecipeNavigatorImpl()
        NavigatorProvider.commonNavigator = CommonNavigatorImpl()


        splashViewModel.getDataCount()

        splashViewModel.countStatus.observe(this) { status ->
            when (status) {
                is Status.ERROR -> {
                    splashViewModel.getIngredientsFromDb()
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    if (status.data < 575) {
                        splashViewModel.getIngredientsFromDb()
                    }
                }
            }
        }



        val jwtToken = getSharedPreferences(Constants.SharedPrefs.USER_DETAILS_PREF, MODE_PRIVATE).getString(Constants.SharedPrefs.KEY_TOKEN, "")
        Log.d("TAG", "onCreate: jwtToken: $jwtToken")

        if (jwtToken.isNullOrEmpty()) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val decodedJWT = JWT.decode(jwtToken)
        val isExpired = decodedJWT.expiresAt.before(Date())
        Log.d("TAG", "onCreate: isExpired: $isExpired")

        if (isExpired) {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        val prefs = getSharedPreferences(Constants.SharedPrefs.APP_PREF, MODE_PRIVATE)

        if (prefs.getBoolean(Constants.SharedPrefs.KEY_IS_INGREDIENTS_FETCHED, false)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        splashViewModel.fetchIngredients()

        splashViewModel.fetchStatus.observe(this) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "splashActivity: onCreate: fetchStatus: error: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    val ingredientsResponse = status.data
                    Log.d("TAG", "splashActivity: onCreate: fetchStatus: ingredientsResponse: $ingredientsResponse")
                    val ingredientsRequest = ingredientsResponse.map { ingredient -> IngredientDto(ingredient.id.toLong(), ingredient.name) }
                    Log.d("TAG", "splashActivity: onCreate: fetchStatus: ingredientsRequest: $ingredientsRequest")

                    splashViewModel.uploadIngredients(AddIngredientsRequest(ingredientsRequest))
                }
            }
        }

        splashViewModel.uploadStatus.observe(this) { status ->
            when (status) {
                is Status.ERROR -> {
                    Log.d("TAG", "splashActivity: onCreate: uploadStatus: error: ${status.message}")
                }
                is Status.LOADING -> {}
                is Status.SUCCESS -> {
                    prefs.edit {
                        putBoolean(Constants.SharedPrefs.KEY_IS_INGREDIENTS_FETCHED, true)
                    }

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}