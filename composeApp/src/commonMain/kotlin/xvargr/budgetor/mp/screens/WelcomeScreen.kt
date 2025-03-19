package xvargr.budgetor.mp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import xvargr.budgetor.mp.classes.Currency
import xvargr.budgetor.mp.classes.CurrencyFormat
import xvargr.budgetor.mp.classes.CurrencyVisualTransformation

@Composable
fun WelcomeScreen(
  onContinue: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var income by remember { mutableStateOf("") }
  var currency by remember { mutableStateOf(CurrencyFormat.USD) }
  var needsPct by remember { mutableStateOf("50") }
  var wantsPct by remember { mutableStateOf("30") }
  var savingsPct by remember { mutableStateOf("20") }
  var currencySelectionOpen by remember { mutableStateOf(false) }

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.fillMaxWidth()
    ) {

      Box(modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Text("Currency:")
          FilledTonalButton(
            onClick = { currencySelectionOpen = !currencySelectionOpen },
          ) {
            Text(currency.name)
            DropdownMenu(
              expanded = currencySelectionOpen,
              onDismissRequest = { currencySelectionOpen = false },
            ) {
              DropdownMenuItem(text = { Text(CurrencyFormat.USD.name) }, onClick = {
                currencySelectionOpen = false
                currency = CurrencyFormat.USD
              })
              DropdownMenuItem(text = { Text(CurrencyFormat.MYR.name) }, onClick = {
                currencySelectionOpen = false
                currency = CurrencyFormat.MYR
              })
              DropdownMenuItem(text = { Text(CurrencyFormat.JPY.name) }, onClick = {
                currencySelectionOpen = false
                currency = CurrencyFormat.JPY
              })
            }
          }
        }
      }

      TextField(
        value = income,
        onValueChange = {
          if (it.isEmpty()) return@TextField
          if (it.length > 9) return@TextField
          if (!it.matches(Regex("\\d+\$"))) return@TextField
          income = it
        },
        visualTransformation = CurrencyVisualTransformation(currency),
        label = { Text("Income") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        maxLines = 1
      )
      CategoryInput(
        categoryName = "Needs",
        percentage = needsPct,
        income = income,
        currencyFormat = currency,
        onChange = { needsPct = it }
      )
      CategoryInput(
        categoryName = "Wants",
        percentage = wantsPct,
        income = income,
        currencyFormat = currency,
        onChange = { wantsPct = it }
      )
      CategoryInput(
        categoryName = "Savings",
        percentage = savingsPct,
        income = income,
        currencyFormat = currency,
        onChange = { savingsPct = it }
      )
      Button(onClick = onContinue) {
        Text("Continue")
      }
    }
  }
}

@Composable
fun CategoryInput(
  categoryName: String,
  percentage: String,
  income: String,
  currencyFormat: CurrencyFormat,
  onChange: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  fun getPercentageOfIncome(income: String, pct: String): Int {
    if (income == "" || pct == "") return 0
    return Currency.divide(income.toInt(), pct.toInt())
//    return (income.toDouble() / 100) * pct.toInt()
  }

  TextField(
    value = percentage,
    onValueChange = { onChange(it) },
    label = { Text("$categoryName %") },
    suffix = {
      Text(
        "% (${
          Currency.formatString(
            getPercentageOfIncome(
              income, percentage
            ).toString(),
            currencyFormat
          )
        })"
      )
    },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    maxLines = 1,
    modifier = modifier
  )
}