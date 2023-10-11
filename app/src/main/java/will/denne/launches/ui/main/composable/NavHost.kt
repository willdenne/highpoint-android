package will.denne.launches.ui.main.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import will.denne.launches.ui.main.stateArg

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "states") {
        composable(route = "states") {
            StatePeak(navController = navController, viewModel = hiltViewModel())
        }
        composable(route = "highpoint/{$stateArg}") {
            SingleHighPoint(viewModel = hiltViewModel())
        }
    }
}
