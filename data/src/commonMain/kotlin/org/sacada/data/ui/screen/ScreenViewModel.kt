package org.sacada.data.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


class ScreenViewModel : ViewModel() {
    private val _componentStates = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val componentStates: StateFlow<Map<String, Boolean>> = _componentStates

    fun updateComponentState(componentId: String, isValid: Boolean) {
        _componentStates.value =
            _componentStates.value.toMutableMap().apply {
                this[componentId] = isValid
            }
    }

    val areAllComponentsValid: StateFlow<Boolean> =
        _componentStates
            .map { states -> states.values.all { it } }
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                true
            )
}
