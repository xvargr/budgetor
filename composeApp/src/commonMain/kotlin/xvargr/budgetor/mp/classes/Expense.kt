package xvargr.budgetor.mp.classes

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

enum class ExpenseCategory {
  Needs,
  Wants,
  Savings,
}

data class Expense @OptIn(ExperimentalUuidApi::class) constructor(
  val id: Uuid = Uuid.random(),
  val name: String,
  val price: Currency,
  val category: ExpenseCategory,
  val notes: String? = "",
) {
}
