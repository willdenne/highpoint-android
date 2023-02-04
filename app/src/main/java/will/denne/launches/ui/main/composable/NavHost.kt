package will.denne.launches.ui.main.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import timber.log.Timber
import will.denne.launches.ui.main.stateArg

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "states") {
        Timber.d("WILL: ${navController.backQueue.size}")
        composable(route = "states") {
            Timber.d("WILL: ${navController.backQueue.size}")
            StatePeak(navController = navController, viewModel = hiltViewModel())
        }
        composable(route = "highpoint/{$stateArg}") {
            Timber.d("WILL: ${navController.backQueue.size}")
            SingleHighPoint(navController = navController, viewModel = hiltViewModel())
        }
    }
}
