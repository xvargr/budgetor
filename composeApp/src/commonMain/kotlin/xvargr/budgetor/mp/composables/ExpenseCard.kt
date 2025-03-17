package xvargr.budgetor.mp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xvargr.budgetor.mp.classes.Expense

@Composable
fun ExpenseCard(
  expense: Expense,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier
  ) {
    Column(
      modifier = Modifier.padding(10.dp).fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = expense.name,
          fontSize = 15.sp,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
        )
        Text(
          text = expense.getDate(),
          fontSize = 15.sp,
          maxLines = 1,
        )
      }
      Text(
        text = expense.price.toString(),
        fontSize = 15.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}
