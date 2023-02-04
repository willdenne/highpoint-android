package will.denne.launches.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import will.denne.launches.data.DataRepository
import will.denne.launches.data.dataclass.HighPoint
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PeaksScreenState> = MutableStateFlow(PeaksScreenState.Loading)
    val uiState: StateFlow<PeaksScreenState> = _uiState

    private val _sortState: MutableStateFlow<SortState> = MutableStateFlow(SortState.ElevationHigh())
    val sortState: StateFlow<SortState> = _sortState

    private val _highpoints: MutableStateFlow<List<HighPoint>> = MutableStateFlow(listOf())
    val highpoints: StateFlow<List<HighPoint>> = _highpoints

    init {
        getStates()
    }

    fun sortClicked() {
        when(_sortState.value) {
            is SortState.ElevationHigh -> {
                _sortState.value = SortState.ElevationLow()
                _highpoints.value = _highpoints.value.sortedBy {
                    it.elevation
                }
            }
            is SortState.ElevationLow -> {
                _sortState.value = SortState.PeakAlpha()
                _highpoints.value = _highpoints.value.sortedBy {
                    it.name
                }
            }
            is SortState.PeakAlpha -> {
                _sortState.value = SortState.StateAlpha()
                _highpoints.value = _highpoints.value.sortedBy {
                    it.state
                }
            }
            is SortState.StateAlpha -> {
                _sortState.value = SortState.ElevationHigh()
                _highpoints.value = _highpoints.value.sortedBy {
                    it.elevation
                }.reversed()
            }
        }
    }

    private fun getStates() {
        viewModelScope.launch {
            try {
                val highpointsResponse = dataRepository.getPeaks().body()
                if (highpointsResponse == null) {
                    _uiState.value = PeaksScreenState.Error(Exception("No peaks found"))
                } else {
                    _uiState.value = PeaksScreenState.Success
                    Timber.d("Got back ${highpointsResponse.size} highpoints")
                    _highpoints.value = highpointsResponse
                }
            } catch (e: Exception) {
                _uiState.value = PeaksScreenState.Error(e)
            }

        }
    }
}

sealed class SortState {
    abstract val display: String
    data class ElevationHigh(
        override val display: String = "Elevation High to Low"
    ): SortState()
    data class ElevationLow(
        override val display: String = "Elevation Low to High"
    ): SortState()
    data class StateAlpha(
        override val display: String = "States A-Z"
    ): SortState()
    data class PeakAlpha(
        override val display: String = "Highpoints A-Z"
    ): SortState()
}

sealed class PeaksScreenState {
    object Loading : PeaksScreenState()
    object Success: PeaksScreenState()
    class Error(val error: Exception) : PeaksScreenState()
}
