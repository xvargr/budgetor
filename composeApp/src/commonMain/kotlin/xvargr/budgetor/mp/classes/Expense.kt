package xvargr.budgetor.mp.classes

enum class ExpenseCategory {
  Essentials,
  Wants,
  Savings,
}

data class Expense(
  val title: String,
  val value: Int,
  val category: ExpenseCategory,
  val description: String = "",
) {
}
