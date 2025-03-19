package xvargr.budgetor.mp.domain.models

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import xvargr.budgetor.mp.classes.Currency
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

  fun getTime(): String {
    return date.toLocalDateTime(TimeZone.currentSystemDefault()).time.toString()
  }

  fun getDateRelative(): String {
    val days = Clock.System.now().minus(date).inWholeDays.toString()
    return "$days day${if (days.toInt() > 1) "s" else ""} ago"
  }
}
