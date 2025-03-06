package xvargr.budgetor.mp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xvargr.budgetor.mp.composable.MainBottomBar
import xvargr.budgetor.mp.composable.MainTopBar
import xvargr.budgetor.mp.screens.EditScreen
import xvargr.budgetor.mp.screens.FaceScreen
import xvargr.budgetor.mp.screens.HomeScreen
import xvargr.budgetor.mp.screens.NewExpenseScreen
import xvargr.budgetor.mp.screens.StarScreen
import xvargr.budgetor.mp.state.AppState

enum class AppScreen(val title: String) {
  Home(title = "Budgetor"),
  NewExpense(title = "New Expense"),
  Favorite(title = "Favorites"),
  Edit(title = "Edit"),
  Face(title = "Face"),
  Star(title = "Stars"),
}

@Composable
fun App(
  appState: AppState = AppState(),
  navController: NavHostController = rememberNavController(),
) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentScreen = AppScreen.valueOf(
    backStackEntry?.destination?.route ?: AppScreen.Home.name
  )

  fun navigate(route: AppScreen) {
    navController.navigate(route.name)
  }

  Scaffold(
    topBar = {
      MainTopBar(
        screen = currentScreen,
        canGoBack = navController.previousBackStackEntry != null,
        onBackClicked = { navController.navigateUp() }
      )
    },
    bottomBar = {
      MainBottomBar(
        onNewClicked = { navigate(AppScreen.NewExpense) },
        onEditClicked = { navigate(AppScreen.Edit) },
        onFaceClicked = { navigate((AppScreen.Face)) },
        onStarClicked = { navigate(AppScreen.Star) },
        onFavoriteClicked = { navigate(AppScreen.Favorite) }
      )
    },
  ) { innerPad ->
    NavHost(
      navController = navController,
      startDestination = AppScreen.Home.name,
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(innerPad)
    ) {
      composable(AppScreen.Home.name) { HomeScreen() }
      composable(AppScreen.NewExpense.name) { NewExpenseScreen() }
      composable(AppScreen.Edit.name) { EditScreen() }
      composable(AppScreen.Face.name) { FaceScreen() }
      composable(AppScreen.Star.name) { StarScreen() }
      composable(AppScreen.Favorite.name) { FaceScreen() }
    }
  }
}