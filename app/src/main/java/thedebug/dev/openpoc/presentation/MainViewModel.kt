package thedebug.dev.openpoc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import thedebug.dev.openpoc.data.model.Bet
import thedebug.dev.openpoc.domain.usecase.CalculateOddsUseCase
import thedebug.dev.openpoc.domain.usecase.GetBetsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBetsUseCase: GetBetsUseCase,
    private val calculateOddsUseCase: CalculateOddsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Bet>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Bet>>> = _uiState.asStateFlow()

    init {
        observeBets()
    }

    private fun observeBets() {
        viewModelScope.launch {
            getBetsUseCase().onStart { _uiState.value = UiState.Loading }
                .catch { e -> _uiState.value = UiState.Error(e.message) }
                .collect { bets ->
                    _uiState.value = UiState.Success(bets)
                }
        }
    }

    fun recalculateOdds() {
        viewModelScope.launch {
            try {
                _uiState.value = UiState.Loading
                val updated = calculateOddsUseCase()
                _uiState.value = UiState.Success(updated)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message)
            }
        }
    }
}
