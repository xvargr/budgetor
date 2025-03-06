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
import androidx.compose.runtime.mutableStateListOf
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

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  val fmt = CurrencyFormat.USD
  var entryVal by remember { mutableStateOf("") }
  val items = remember { mutableStateListOf<Currency>() }
  var menuOpen by remember { mutableStateOf(false) }
  var selectedItem by remember { mutableStateOf<String?>(null) }

  Column(
    modifier = modifier.fillMaxSize().padding(10.dp)
  ) {
    for (item in items) {
      Text(
        text = item.toString(),
        fontSize = 15.sp,
        modifier = Modifier.padding(2.dp)
      )
    }

    Spacer(Modifier.padding(5.dp))

    Text(
      text = "Total: ${items.fold(Currency(0, fmt)) { acc, next -> acc.add(next) }}",
      fontSize = 20.sp,
    )

    Spacer(Modifier.padding(5.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      TextField(
        value = entryVal,
        onValueChange = {
          if (it.isNotEmpty() && !it.matches(Regex("\\d+\$"))) return@TextField
          entryVal = it
        },
        visualTransformation = CurrencyVisualTransformation(fmt),
        label = { Text("Price") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//        modifier = Modifier.weight(1.0f)
      )
//      Box(modifier = Modifier.weight(1f)) {
      FilledTonalButton(onClick = { menuOpen = !menuOpen }) {
        Text(selectedItem:?"Category")
      }
//      }
    }
    DropdownMenu(
      expanded = menuOpen,
      onDismissRequest = { menuOpen = false },
      modifier = Modifier.fillMaxWidth()
    ) {
      DropdownMenuItem(text = { Text("Needs") }, onClick = {
        menuOpen = false
        selectedItem = "Needs"
      })
      DropdownMenuItem(text = { Text("Wants") }, onClick = {
        menuOpen = false
        selectedItem = "Wants"
      })
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
          if (entryVal.isEmpty()) return@TextButton
          items.add(Currency.fromString(entryVal, fmt))
          entryVal = ""
        },
      )
    }
  }
}