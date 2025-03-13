package xvargr.budgetor.mp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xvargr.budgetor.mp.classes.Currency
import xvargr.budgetor.mp.classes.CurrencyVisualTransformation
import xvargr.budgetor.mp.classes.Expense
import xvargr.budgetor.mp.classes.ExpenseCategory
import xvargr.budgetor.mp.viewModels.ExpenseViewModel
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun NewExpenseDialog(
  expenseViewModel: ExpenseViewModel,
  onDismiss: () -> Unit,
) {
  val currencyFormat by expenseViewModel.currencyFormat.collectAsState()
  var nameInput by remember { mutableStateOf("") }
  var valueInput by remember { mutableStateOf("") }
  var menuOpen by remember { mutableStateOf(false) }
  var selectedCategory by remember { mutableStateOf<ExpenseCategory?>(null) }

  Dialog(
    onDismissRequest = onDismiss,
  ) {
    Card(
      shape = RoundedCornerShape(16.dp),
    ) {
      Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
      ) {
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
              if (it.isEmpty()) return@TextField
              if (it.length > 9) return@TextField
              if (!it.matches(Regex("\\d+\$"))) return@TextField
              valueInput = it
            },
            visualTransformation = CurrencyVisualTransformation(currencyFormat),
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(0.4f)
          )
        }

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

              val price = Currency.fromString(valueInput, currencyFormat)

              selectedCategory?.let {
                expenseViewModel.addExpense(
                  @OptIn(ExperimentalUuidApi::class)
                  Expense(
                    name = nameInput,
                    price = price,
                    category = it,
                  )
                )
              }

              nameInput = ""
              valueInput = ""
              selectedCategory = null
              onDismiss()
            },
          )
        }
      }
    }
  }
}
