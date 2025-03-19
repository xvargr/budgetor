package xvargr.budgetor.mp.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import xvargr.budgetor.mp.domain.models.Expense
import xvargr.budgetor.mp.domain.models.ExpenseCategory

class ExpenseViewModel : ViewModel() {
  private val _newExpenseDialogOpen = MutableStateFlow(false)
  val newExpenseDialogOpen = _newExpenseDialogOpen.asStateFlow()

  private val _needs = MutableStateFlow<List<Expense>>(mutableListOf())
  val needs = _needs.asStateFlow()

  private val _wants = MutableStateFlow<List<Expense>>(mutableListOf())
  val wants = _wants.asStateFlow()

  private val _savings = MutableStateFlow<List<Expense>>(mutableListOf())
  val savings = _savings.asStateFlow()

  fun openNewExpenseDialog() {
    _newExpenseDialogOpen.value = true
  }

  fun closeNewExpenseDialog() {
    _newExpenseDialogOpen.value = false
  }

  fun addExpense(expense: Expense) {
    // TODO: Maintain Sort
    when (expense.category) {
      ExpenseCategory.Needs -> {
        _needs.update { curr -> curr + expense }
      }

      ExpenseCategory.Wants -> {
        _wants.update { curr -> curr + expense }
      }

      ExpenseCategory.Savings -> {
        _savings.update { curr -> curr + expense }
      }
    }
  }
}