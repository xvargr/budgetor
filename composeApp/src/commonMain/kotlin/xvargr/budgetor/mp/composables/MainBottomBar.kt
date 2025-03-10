package xvargr.budgetor.mp.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainBottomBar(
  modifier: Modifier = Modifier,
  onNewClicked: () -> Unit,
  onFavoriteClicked: () -> Unit,
  onEditClicked: () -> Unit,
  onFaceClicked: () -> Unit,
  onStarClicked: () -> Unit,
) {
  BottomAppBar(
    actions = {
      IconButton(onClick = onFavoriteClicked) {
        Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
      }
      IconButton(onClick = onEditClicked) {
        Icon(
          Icons.Filled.Edit,
          contentDescription = "Localized description",
        )
      }
      IconButton(onClick = onFaceClicked) {
        Icon(
          Icons.Filled.Face,
          contentDescription = "Localized description",
        )
      }
      IconButton(onClick = onStarClicked) {
        Icon(
          Icons.Filled.Star,
          contentDescription = "Localized description",
        )
      }
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = onNewClicked,
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
      ) {
        Icon(Icons.Filled.Add, "Localized description")
      }
    },
    modifier = modifier,
  )
}