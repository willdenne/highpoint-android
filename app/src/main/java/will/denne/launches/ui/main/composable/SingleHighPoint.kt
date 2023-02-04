package will.denne.launches.ui.main.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import will.denne.launches.ui.main.HighPointViewModel
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import will.denne.launches.data.getStateCode
import will.denne.launches.ui.main.HighPointScreenState

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@ExperimentalCoilApi
@Composable
fun SingleHighPoint(
    viewModel: HighPointViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val highPoint by viewModel.highpoint.collectAsState()
    when (uiState) {
        is HighPointScreenState.Loading -> {
            Loading()
        }
        is HighPointScreenState.Error -> {
            Error((uiState as HighPointScreenState.Error).error.message)
        }
        is HighPointScreenState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 4.dp)
            ) {
                HorizontalPager(
                    count = 3,
                    contentPadding = PaddingValues(end = 32.dp, start = 32.dp)
                ) { page ->
                    when (page) {
                        0 -> {
                            AsyncImage(
                                model = highPoint?.image1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentDescription = "image one"
                            )
                        }
                        1 -> {
                            AsyncImage(
                                model = highPoint?.image2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentDescription = "image two"
                            )
                        }
                        2 -> {
                            AsyncImage(
                                model = highPoint?.imageSelf,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentDescription = "selfie"
                            )
                        }
                    }
                }
                Text(
                    text = "${highPoint?.name} in ${highPoint?.state}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = "${highPoint?.desc1}\n",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Elevation: ${"%,d".format(highPoint?.elevation)} ft", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Date Hiked: ${highPoint?.dateStamp}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                    AsyncImage(model = "https://flagpedia.net/data/us/w1160/${getStateCode(highPoint?.state ?: "")}.png",
                        contentDescription = "Flag",
                        modifier = Modifier.height(height = 96.dp)
                    )
                }
                Text(
                    text = "\n${highPoint?.desc2}",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(4.dp),
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}
