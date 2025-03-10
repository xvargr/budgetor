package xvargr.budgetor.mp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.sp
import xvargr.budgetor.mp.classes.Currency
import xvargr.budgetor.mp.classes.CurrencyFormat
import xvargr.budgetor.mp.classes.CurrencyVisualTransformation
import xvargr.budgetor.mp.classes.Expense
import xvargr.budgetor.mp.classes.ExpenseCategory
import xvargr.budgetor.mp.state.AppState

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  val appState = AppState
  val fmt = CurrencyFormat.USD
  var nameInput by remember { mutableStateOf("") }
  var valueInput by remember { mutableStateOf("") }
  var menuOpen by remember { mutableStateOf(false) }
  var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }

  Column(
    modifier = modifier.fillMaxSize().padding(10.dp)
  ) {

    Row(
      modifier = Modifier.fillMaxSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceEvenly
    ) {
      Column(
        verticalArrangement = Arrangement.SpaceAround,
//        modifier = Modifier.padding(2.dp).weight(0.33f)
      ) {
        Text(ExpenseCategory.Needs.name, fontSize = 18.sp)
        for (item in appState.needs) {
          Text(
            text = "${item.name} - ${item.price}",
            fontSize = 15.sp,
          )
        }
      }
      Column(
        verticalArrangement = Arrangement.SpaceAround,
//        modifier = Modifier.padding(2.dp).weight(0.33f)
      ) {
        Text(ExpenseCategory.Wants.name, fontSize = 18.sp)
        for (item in appState.wants) {
          Text(
            text = "${item.name} - ${item.price}",
            fontSize = 15.sp,
          )
        }
      }
      Column(
        verticalArrangement = Arrangement.SpaceAround,
//        modifier = Modifier.padding(2.dp).weight(0.33f)
      ) {
        Text(ExpenseCategory.Savings.name, fontSize = 18.sp)
        for (item in appState.savings) {
          Text(
            text = "${item.name} - ${item.price}",
            fontSize = 15.sp,
          )
        }
      }
    }

    Spacer(Modifier.padding(5.dp))

//    Text(
//      text = "Total: ${items.fold(Currency(0, fmt)) { acc, next -> acc.add(next) }}",
//      fontSize = 20.sp,
//    )

    Spacer(Modifier.padding(5.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      TextField(
        value = nameInput,
        onValueChange = {
          nameInput = it.trim()
        },
        label = { Text("Name") },
        modifier = Modifier.weight(0.6f)
      )
      TextField(
        value = valueInput,
        onValueChange = {
          if (it.isNotEmpty() && !it.matches(Regex("\\d+\$"))) return@TextField
          valueInput = it
        },
        visualTransformation = CurrencyVisualTransformation(fmt),
        label = { Text("Price") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.weight(0.4f)
      )
    }

    Spacer(Modifier.padding(5.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      FilledTonalButton(onClick = { menuOpen = !menuOpen }) {
        Text(selectedCategory?.name ?: "Category")
      }
      DropdownMenu(
        expanded = menuOpen,
        onDismissRequest = { menuOpen = false },
        modifier = Modifier.fillMaxWidth()
      ) {
        DropdownMenuItem(text = { Text(ExpenseCategory.Needs.name) }, onClick = {
          menuOpen = false
          selectedCategory = ExpenseCategory.Needs
        })
        DropdownMenuItem(text = { Text(ExpenseCategory.Wants.name) }, onClick = {
          menuOpen = false
          selectedCategory = ExpenseCategory.Wants
        })
        DropdownMenuItem(text = { Text(ExpenseCategory.Savings.name) }, onClick = {
          menuOpen = false
          selectedCategory = ExpenseCategory.Savings
        })
      }
    }

    Spacer(Modifier.padding(5.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      TextButton(
        content = {
          Text("Add expense")
        },
        onClick = {
          if (valueInput.isEmpty()) return@TextButton
          if (nameInput.isEmpty()) return@TextButton
          // todo input error

          val price = Currency.fromString(valueInput, fmt)
          when (selectedCategory) {
            ExpenseCategory.Needs -> appState.needs.add(
              Expense(
                name = nameInput,
                price = price,
                category = ExpenseCategory.Needs,
              )
            )

            ExpenseCategory.Wants -> appState.wants.add(
              Expense(
                name = nameInput,
                price = price,
                category = ExpenseCategory.Wants,
              )
            )

            ExpenseCategory.Savings -> appState.savings.add(
              Expense(
                name = nameInput,
                price = price,
                category = ExpenseCategory.Savings,
              )
            )

            else -> return@TextButton
            // todo err
          }

          nameInput = ""
          valueInput = ""
          selectedCategory = null
        },
      )
    }
  }
}