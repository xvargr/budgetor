package xvargr.budgetor.mp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import xvargr.budgetor.mp.classes.ExpenseCategory
import xvargr.budgetor.mp.composables.Accordion
import xvargr.budgetor.mp.composables.ExpenseCard
import xvargr.budgetor.mp.viewModels.ExpenseViewModel


@Composable
fun HomeScreen(
  expenseViewModel: ExpenseViewModel,
) {
  val needs by expenseViewModel.needs.collectAsState()
  val wants by expenseViewModel.wants.collectAsState()
  val savings by expenseViewModel.savings.collectAsState()
  var needsOpen by remember { mutableStateOf(false) }
  var wantsOpen by remember { mutableStateOf(false) }
  var savingsOpen by remember { mutableStateOf(false) }

  Column(
    verticalArrangement = Arrangement.spacedBy(15.dp),
  ) {
    Accordion(
      title = ExpenseCategory.Needs.name,
      onClick = {
        needsOpen = !needsOpen
        wantsOpen = false
        savingsOpen = false
      },
      isOpen = needsOpen,
    ) {
      for (item in needs) {
        ExpenseCard(item)
      }
    }
    Accordion(
      title = ExpenseCategory.Wants.name,
      onClick = {
        wantsOpen = !wantsOpen
        needsOpen = false
        savingsOpen = false
      },
      isOpen = wantsOpen,
    ) {
      for (item in wants) {
        ExpenseCard(item)
      }
    }
    Accordion(
      title = ExpenseCategory.Savings.name,
      onClick = {
        savingsOpen = !savingsOpen
        wantsOpen = false
        needsOpen = false
      },
      isOpen = savingsOpen,
    ) {
      for (item in savings) {
        ExpenseCard(item)
      }
    }
  }
}