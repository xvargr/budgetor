package xvargr.budgetor.mp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xvargr.budgetor.mp.composables.Accordion
import xvargr.budgetor.mp.composables.ExpenseCard
import xvargr.budgetor.mp.domain.models.Expense
import xvargr.budgetor.mp.domain.models.ExpenseCategory
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
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(15.dp),
  ) {
    val openWeight = if (needsOpen || wantsOpen || savingsOpen) 1f else 0f

    Accordion(
      title = ExpenseCategory.Needs.name,
      onClick = {
        needsOpen = !needsOpen
        wantsOpen = false
        savingsOpen = false
      },
      isOpen = needsOpen,
      _modifier = if (needsOpen) Modifier.weight(openWeight) else Modifier
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        SectionContainer(needs)
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
      _modifier = if (wantsOpen) Modifier.weight(openWeight) else Modifier
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        SectionContainer(wants)
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
      _modifier = if (savingsOpen) Modifier.weight(openWeight) else Modifier
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .weight(1f)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        SectionContainer(savings)
      }
    }
  }
}

@Composable
fun SectionContainer(
  items: List<Expense>,
) {
  if (items.isEmpty()) {
    Text("Nothing to show")
  } else {
    for (item in items) {
      ExpenseCard(item)
    }
  }
}