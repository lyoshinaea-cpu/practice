package ci.nsu.mobile.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.data.local.DepositCalculation
import ci.nsu.mobile.main.data.local.DepositRepository
import ci.nsu.mobile.main.domain.DepositCalculator
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DepositViewModel(
    private val repository: DepositRepository,
    private val calculator: DepositCalculator = DepositCalculator()
) : ViewModel() {

    val allCalculations: StateFlow<List<DepositCalculation>> = repository.allCalculations
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun calculateAndSave(amount: Double, months: Int, topUp: Double) {
        viewModelScope.launch {
            val result = calculator.calculate(amount, months, topUp)
            repository.insert(result)
        }
    }

    class Factory(private val repository: DepositRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DepositViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DepositViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}