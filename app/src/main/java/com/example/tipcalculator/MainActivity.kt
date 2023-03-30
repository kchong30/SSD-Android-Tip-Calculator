package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()

                }
            }
        }
    }
}

@Composable
fun TipCalculatorScreen() {
    var serviceCostAmountInput by remember { mutableStateOf("") }
    var tipSelectedAmount by remember { mutableStateOf(10.0) }
    val amount = serviceCostAmountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount, tipSelectedAmount)
    val tipConverted = NumberFormat.getCurrencyInstance().format(tip)
    val amountConverted = NumberFormat.getCurrencyInstance().format(amount)
    val total = NumberFormat.getCurrencyInstance().format((amount + tip))


    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.tip_calculator_heading),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            val randomAmount = (0..2000).random().toDouble()
            serviceCostAmountInput = randomAmount.toString()
        }) {
            Text("Randomize Bill Amount Between $0 and $2000")
        }
        Spacer(Modifier.height(16.dp))
        EditServiceCostField(
            value = serviceCostAmountInput,
            onValueChange = { serviceCostAmountInput = it }
        )
        Spacer(Modifier.height(24.dp))
        SelectTipAmount(tipSelectedAmount) { tipSelectedAmount = it }
        Spacer(Modifier.height(50.dp))
        Text(
            text = "Bill Amount:$" + amountConverted,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )
        Text(
            text = "Tip Amount:" + tipConverted,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )
        Text(
            text = "Total:" + total,
            modifier = Modifier.align(Alignment.CenterHorizontally),

            )
    }
}


@Composable
fun EditServiceCostField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.service_cost)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun SelectTipAmount(
    value: Double,
    onValueChange: (Double) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onValueChange(10.0) }
        ) {
            Text("10%")
        }
        Spacer(Modifier.width(16.dp))
        Button(
            onClick = { onValueChange(15.0) }
        ) {
            Text("15%")
        }
    }
    Spacer(Modifier.height(24.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { onValueChange(20.0) }
        ) {
            Text("20%")
        }
        Spacer(Modifier.width(16.dp))
        Button(
            onClick = { onValueChange(25.0) }
        ) {
            Text("25%")
        }
    }
}
private fun calculateTip(
    amount: Double,
    tipPercent: Double
): Double {
    val tip = tipPercent / 100 * amount
    // NumberFormat.getCurrencyInstance().format(tip)
    return tip
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        TipCalculatorScreen()

    }
}