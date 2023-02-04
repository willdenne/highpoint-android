package will.denne.launches.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import will.denne.launches.data.DataRepository
import will.denne.launches.data.dataclass.HighPoint
import javax.inject.Inject

data class HighPointViewState(
    val state: String
)

@HiltViewModel
class HighPointViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<HighPointScreenState> = MutableStateFlow(HighPointScreenState.Loading)
    val uiState: StateFlow<HighPointScreenState> = _uiState

    private val _highpoint: MutableStateFlow<HighPoint?> = MutableStateFlow(null)
    val highpoint: StateFlow<HighPoint?> = _highpoint

    init {
        getHighPoint(savedStateHandle.get<String>(stateArg).orEmpty())
    }

    private fun getHighPoint(state: String) {
        viewModelScope.launch {
            try {
                val highPoint = dataRepository.getHighPoint(state).body()
                if (highPoint == null) {
                    _uiState.value = HighPointScreenState.Error(java.lang.Exception("Whoops something went wrong"))
                } else {
                    _uiState.value = HighPointScreenState.Success
                    _highpoint.value = highPoint
                }
            } catch (e: Exception) {
                _uiState.value = HighPointScreenState.Error(e)
            }
        }
    }
}

const val stateArg = "arg"

sealed class HighPointScreenState {
    object Loading : HighPointScreenState()
    object Success: HighPointScreenState()

    class Error(val error: java.lang.Exception) : HighPointScreenState()
}
