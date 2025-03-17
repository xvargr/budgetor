package xvargr.budgetor.mp.classes

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

enum class ExpenseCategory {
  Needs,
  Wants,
  Savings,
}

data class Expense @OptIn(ExperimentalUuidApi::class) constructor(
  val id: Uuid = Uuid.random(),
  val date: Instant = Clock.System.now(),
  val name: String,
  val price: Currency,
  val category: ExpenseCategory,
  val notes: String? = "",
) {
  fun getDate(): String {
    return date.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
  }
}
