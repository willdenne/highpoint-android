package will.denne.launches.ui.main.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import will.denne.launches.R
import will.denne.launches.data.dataclass.HighPoint
import will.denne.launches.ui.main.MainViewModel
import will.denne.launches.ui.main.PeaksScreenState

@Composable
fun StatePeak(
    viewModel: MainViewModel,
    navController: NavController
) {
    val listState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsState()
    val sortState by viewModel.sortState.collectAsState()
    val highPoints by viewModel.highpoints.collectAsState()
    when (uiState) {
        is PeaksScreenState.Loading -> {
            Loading()
        }
        is PeaksScreenState.Success -> {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                val (sort, content) = createRefs()
                Row(modifier = Modifier
                    .clickable(onClick = {
                        viewModel.sortClicked()
                    })
                    .constrainAs(sort) {
                        top.linkTo(parent.top)
                    }
                    .padding(16.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "Sorting: ${sortState.display}",
                        style = MaterialTheme.typography.h5,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Sort"
                    )
                }
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.constrainAs(content) {
                        top.linkTo(sort.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    items(highPoints) { highPoint ->
                        PeakRow(highPoint, navController)
                    }
                }
            }
        }
        is PeaksScreenState.Error -> {
            Error(error = (uiState as PeaksScreenState.Error).error.message)
        }
    }
}

@Composable
fun PeakRow(highPoint: HighPoint, navController: NavController) {
    Card(
        border = BorderStroke(2.dp, colorResource(id = R.color.black)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.clickable {
                    navController.navigate("highpoint/${highPoint.urlKey}")
                }
            ) {
                Text(
                    text = "${highPoint.state}: ${highPoint.name}",
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}
