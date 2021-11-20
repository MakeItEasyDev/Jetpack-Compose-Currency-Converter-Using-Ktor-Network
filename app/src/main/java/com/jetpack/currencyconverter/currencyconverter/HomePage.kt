package com.jetpack.currencyconverter.currencyconverter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jetpack.currencyconverter.application.currencyconverter.CurrencyConverterEvent
import com.jetpack.currencyconverter.application.currencyconverter.CurrencyConverterLogic
import com.jetpack.currencyconverter.application.currencyconverter.HomePageState
import com.jetpack.currencyconverter.domain.entity.Currency
import com.jetpack.currencyconverter.ui.theme.Black
import com.jetpack.currencyconverter.ui.theme.BlueDark
import com.jetpack.currencyconverter.ui.theme.BlueLight
import com.jetpack.currencyconverter.ui.theme.Purple500
import com.jetpack.currencyconverter.utils.SELECTOR_PAGE
import com.jetpack.currencyconverter.utils.noRippleClickable

@ExperimentalAnimationApi
@Composable
fun HomePage(
    state: HomePageState,
    logic: CurrencyConverterLogic,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    val baseCurrency: State<Currency> = state.baseCurrency.collectAsState()
    val baseCurrencyDisplay = state.baseCurrencyDisplay.collectAsState()

    val targetCurrency: State<Currency> = state.targetCurrency.collectAsState()
    val targetCurrencyDisplay = state.targetCurrencyDisplay.collectAsState()

    val error = state.error.collectAsState()
    val isLoading = state.isLoading.collectAsState()

    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    val focusManager = LocalFocusManager.current

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primarySurface,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Currency Converter",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = Purple500
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    focusManager.clearFocus()
                }
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            CurrencyCard(
                currency = baseCurrency.value,
                amount = baseCurrencyDisplay.value,
                currencyClickHandler = {
                    logic.onEvent(CurrencyConverterEvent.BaseCurrencyChangeRequested)
                    navController.navigate(SELECTOR_PAGE)
                },
                textChangeHandler = {
                    logic.onEvent(
                        CurrencyConverterEvent.BaseCurrencyDisplayTextChanged(it)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            MiddleSection(
                onSwitchCurrenciesPressed = {
                    logic.onEvent(CurrencyConverterEvent.SwitchCurrenciesPressed)
                },
                onEvaluatePressed = {
                    logic.onEvent(CurrencyConverterEvent.EvaluatePressed)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CurrencyCard(
                currency = targetCurrency.value,
                amount = targetCurrencyDisplay.value,
                currencyClickHandler = {
                    logic.onEvent(CurrencyConverterEvent.TargetCurrencyChangeRequested)
                    navController.navigate(SELECTOR_PAGE)
                },
                textChangeHandler = {}, readonly = true
            )
            if (error.value != null) {
                Spacer(modifier = Modifier.padding(32.dp))
                Text(
                    text = error.value.toString(),
                    color = Color.Red,
                    style = MaterialTheme.typography.body2
                )
            }
        }

        AnimatedVisibility(visible = isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black.copy(alpha = .2f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BlueDark)
            }
        }
    }
}

@Composable
fun MiddleSection(
    onSwitchCurrenciesPressed: () -> Unit,
    onEvaluatePressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Button(
            onClick = {
                onSwitchCurrenciesPressed()
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp,
                disabledElevation = 4.dp
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Switch Currencies")
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                focusManager.clearFocus()
                onEvaluatePressed()
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp,
                disabledElevation = 4.dp
            )
        ) {
            Icon(
                painter = rememberVectorPainter(Icons.Filled.Send),
                contentDescription = "Evaluate",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}


@Composable
fun CurrencyCard(
    currency: Currency,
    amount: String,
    currencyClickHandler: () -> Unit,
    textChangeHandler: (String) -> Unit,
    modifier: Modifier = Modifier,
    readonly: Boolean = false
) {

    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 16.dp,
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        currencyClickHandler()
                    }
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = currency.code.uppercase(),
                        color = MaterialTheme.colors.onPrimary
                    )
                    Text(text = currency.name)
                }
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.ArrowForward),
                    contentDescription = "right arrow",
                    tint = BlueDark,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    textChangeHandler(it)
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Text(text = java.util.Currency.getInstance(currency.code).symbol)
                },
                readOnly = readonly,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                placeholder = {
                    Text(text = "0.00")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BlueLight,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary
                )
            )
        }
    }
}