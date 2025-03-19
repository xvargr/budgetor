package xvargr.budgetor.mp.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xvargr.budgetor.mp.classes.CurrencyFormat

class ConfigurationViewModel : ViewModel() {
  private val _configComplete = MutableStateFlow(false)
  val configComplete = _configComplete.asStateFlow()

  private val _currencyFormat = MutableStateFlow(CurrencyFormat.USD)
  val currencyFormat = _currencyFormat.asStateFlow()

  fun setConfigComplete() {
    _configComplete.value = true
  }
}