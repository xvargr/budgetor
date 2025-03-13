package xvargr.budgetor.mp.classes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.pow

enum class CurrencySymbolPosition {
  Prefix,
  Suffix
}

enum class CurrencyFormat(
  val symbol: String,
  val decimalLocation: Int = 2,
  val decimalSymbol: String = ".",
  val symbolLocation: CurrencySymbolPosition = CurrencySymbolPosition.Prefix,
) {
  USD(symbol = "$"),
  MYR(symbol = "RM"),
  JPY(symbol = "¥", decimalLocation = 0)
}

open class Currency(
  private var units: Double,
  private val format: CurrencyFormat,
) {
  companion object {
    fun fromString(input: String, format: CurrencyFormat): Currency {
      val cleanedInput = input.replace(format.decimalSymbol, "")
      val doubleInput = if (format.decimalLocation == 0) {
        cleanedInput.toDouble()
      } else {
        cleanedInput.toDouble() / 10.0.pow(format.decimalLocation)
      }
      return Currency(doubleInput, format)
    }

    fun formatString(input: String, fmt: CurrencyFormat): String {
      val sanitizedInput = input.trim().filter { it.isDigit() }  // Remove non-digits
      if (sanitizedInput.isEmpty()) return fmt.symbol + "0"  // Default if empty

      val strUnit = sanitizedInput.padStart(fmt.decimalLocation + 1, '0')
      val decimalSplit = strUnit.length - fmt.decimalLocation
      val formatted = buildString {
        append(strUnit.substring(0, decimalSplit))
        if (fmt.decimalLocation > 0) {
          append(fmt.decimalSymbol)
          append(strUnit.substring(decimalSplit))
        }
      }

      return if (fmt.symbolLocation == CurrencySymbolPosition.Prefix) {
        "${fmt.symbol}$formatted"
      } else {
        "$formatted${fmt.symbol}"
      }

    }
  }

  fun replaceUnit(unit: Double): Currency {
    units = unit
    return this
  }

  fun add(cur: Currency): Currency {
    units += cur.units
    return this
  }

  fun subtract(cur: Currency): Currency {
    units += cur.units
    return this
  }

  override fun toString(): String {
    return formatString(units.toString(), format)
  }
}

class CurrencyVisualTransformation(currencyFormat: CurrencyFormat) : VisualTransformation {
  private val fmt = currencyFormat

  override fun filter(text: AnnotatedString): TransformedText {
    fun String.isDigitsOnly(): Boolean {
      forEach { if (it.isDigit().not()) return false }
      return true
    }

    val preFormat = text.text.trim()
    if (preFormat.isEmpty()) return TransformedText(text, OffsetMapping.Identity)
    if (preFormat.isDigitsOnly().not()) return TransformedText(text, OffsetMapping.Identity)
    val formatted = Currency.formatString(preFormat, fmt)

    return TransformedText(AnnotatedString(formatted), CurrencyOffsetMapping(preFormat, formatted))
  }
}

class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {
  private val originalLength = originalText.length
  private val indexes = findDigitIndexes(originalText, formattedText)

  private fun findDigitIndexes(first: String, second: String): List<Int> {
    val indices = mutableListOf<Int>()
    var currentIndex = 0

    for (digit in first) {
      val index = second.indexOf(digit, currentIndex)
      if (index != -1) {
        indices.add(index)
        currentIndex = index + 1
        continue
      }
      return emptyList()
    }

    return indices
  }

  override fun originalToTransformed(offset: Int): Int {
    if (offset >= originalLength) return indexes.last() + 1
    return indexes[offset]
  }

  override fun transformedToOriginal(offset: Int): Int {
    return indexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
  }
}