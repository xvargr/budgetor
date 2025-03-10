package xvargr.budgetor.mp.state

import xvargr.budgetor.mp.classes.Expense

object AppState {
  val needs: MutableList<Expense> = mutableListOf()
  val wants: MutableList<Expense> = mutableListOf()
  val savings: MutableList<Expense> = mutableListOf()
}
