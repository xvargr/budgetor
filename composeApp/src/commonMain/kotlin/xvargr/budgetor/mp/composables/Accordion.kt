package xvargr.budgetor.mp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Accordion(
  title: String,
  isOpen: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
    modifier = modifier,
  ) {
    Card(
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondary
      ),
      onClick = onClick,
      modifier = modifier.fillMaxWidth()
    ) {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp, 20.dp).fillMaxWidth()
      ) {
        Text(
          text = title,
          fontSize = 20.sp,
          fontWeight = FontWeight(500)
        )
        Icon(
          Icons.Filled.ArrowDropDown,
          contentDescription = "toggle category drawer",
          modifier = Modifier.rotate(if (isOpen) 180f else 0f)
        )
      }
    }

    if (isOpen) {
      Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(2.dp).fillMaxWidth()
      ) {
        content()
      }
    }
  }
}