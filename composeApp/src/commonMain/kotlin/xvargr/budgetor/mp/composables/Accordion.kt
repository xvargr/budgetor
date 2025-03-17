package xvargr.budgetor.mp.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
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
  _modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  val arrowDeg by animateFloatAsState(
    targetValue = if (isOpen) 180f else 0f,
    animationSpec = tween(300)
  )

  Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
    modifier = _modifier.fillMaxWidth(),
  ) {
    Card(
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.secondary
      ),
      onClick = onClick,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .padding(10.dp, 20.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = title,
          fontSize = 20.sp,
          fontWeight = FontWeight(500)
        )
        Icon(
          Icons.Filled.ArrowDropDown,
          contentDescription = "toggle category drawer",
          modifier = Modifier.rotate(arrowDeg)
        )
      }
    }

    if (isOpen) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(2.dp)
      ) {
        content()
      }
    }
  }
}