package xvargr.budgetor.mp.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import xvargr.budgetor.mp.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
  screen: AppScreen,
  onBackClicked: () -> Unit,
  canGoBack: Boolean,
  modifier: Modifier = Modifier,
) {
  CenterAlignedTopAppBar(
    modifier = modifier,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
      titleContentColor = MaterialTheme.colorScheme.primary,
    ),
    title = {
      Text(
        screen.title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    navigationIcon = {
      if (canGoBack) {
        IconButton(onClick = onBackClicked) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Localized description"
          )
        }
      }
    },
    actions = {
      IconButton(onClick = {}) {
        Icon(
          imageVector = Icons.Filled.Menu,
          contentDescription = "Localized description"
        )
      }
    },
  )
}