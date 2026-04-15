package ci.nsu.mobile.main

import androidx.lifecycle.*
import ci.nsu.mobile.main.data.local.DepositCalculation
import ci.nsu.mobile.main.data.local.DepositRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DepositViewModel(private val repository: DepositRepository) : ViewModel() {

    val allCalculations: StateFlow<List<DepositCalculation>> = repository.allCalculations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun calculateAndSave(amount: Double, months: Int, topUp: Double, rate: Double) {
        viewModelScope.launch {
            val interest = amount * (rate / 100) * (months.toDouble() / 12)
            val final = amount + interest + (topUp * months)

            val newRecord = DepositCalculation(
                initialAmount = amount,
                months = months,
                rate = rate,
                monthlyTopUp = topUp,
                finalAmount = final,
                profit = interest,
                date = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date())
            )
            repository.insert(newRecord)
        }
    }

    class Factory(private val repository: DepositRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DepositViewModel(repository) as T
        }
    }
}