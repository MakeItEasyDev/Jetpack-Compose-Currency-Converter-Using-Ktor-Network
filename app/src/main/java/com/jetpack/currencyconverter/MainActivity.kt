package com.jetpack.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetpack.currencyconverter.application.currencyconverter.*
import com.jetpack.currencyconverter.currencyconverter.CurrencySelectorPage
import com.jetpack.currencyconverter.currencyconverter.HomePage
import com.jetpack.currencyconverter.currencyconverter.di.viewModel
import com.jetpack.currencyconverter.ui.theme.CurrencyConverterTheme
import com.jetpack.currencyconverter.utils.HOME_PAGE
import com.jetpack.currencyconverter.utils.SELECTOR_PAGE
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var logic: CurrencyConverterLogic
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val snackBarHostState = SnackbarHostState()
        val homePageState: HomePageState = viewModel
        val currencySelectorPageState: CurrencySelectorPageState = viewModel
        logic = viewModel

        setContent {
            CurrencyConverterTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = HOME_PAGE
                    ) {
                        composable(HOME_PAGE) {
                            HomePage(
                                state = homePageState,
                                logic = logic,
                                snackbarHostState = snackBarHostState,
                                navController = navController
                            )
                        }

                        composable(SELECTOR_PAGE) {
                            CurrencySelectorPage(
                                state = currencySelectorPageState,
                                logic = logic,
                                snackBarHostState = snackBarHostState,
                                navController = navController
                            )
                        }
                    }
                }
            }

            lifecycleScope.launch {
                homePageState.effectStream.collect {
                    when (it) {
                        is CurrencyConverterEffect.ShowToast -> snackBarHostState.showSnackbar(
                            it.message
                        )
                    }
                }
            }
        }
    }

    override fun onStop() {
        logic.onEvent(CurrencyConverterEvent.OnStop)
        super.onStop()
    }
}















