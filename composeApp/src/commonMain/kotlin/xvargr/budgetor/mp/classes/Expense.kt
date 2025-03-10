package xvargr.budgetor.mp.classes

enum class ExpenseCategory {
  Needs,
  Wants,
  Savings,
}

data class Expense(
  val name: String,
  val price: Currency,
  val category: ExpenseCategory,
  val description: String? = "",
) {
}
