package org.sacada.sdui.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sacada.core.model.ViewScreens
import org.sacada.data.domain.useCase.GetSduiScreensUseCase

class MainScreenViewModel(
    private val getSduiScreensUseCase: GetSduiScreensUseCase,
) : ViewModel() {
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _rootComponent = mutableStateOf<ViewScreens?>(null)
    val rootComponent: State<ViewScreens?> = _rootComponent

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentScreenId = mutableStateOf<String?>(null)
    val currentScreenId: State<String?> = _currentScreenId

    private var isFetching = false

    init {
        fetchData()
    }

    fun fetchData(
        onComplete: (() -> Unit)? = null,
        showLoading: Boolean = false,
    ) {
        if (isFetching) return
        isFetching = true

        viewModelScope.launch {
            if (showLoading) _isLoading.value = true
            _errorMessage.value = null

            try {
                val result = getSduiScreensUseCase()

                if (result != null) {
                    _rootComponent.value = result
                    _currentScreenId.value =
                        _rootComponent.value
                            ?.screens
                            ?.firstOrNull()
                            ?.id
                } else {
                    _errorMessage.value =
                        "Failed to load layout. Please check your connection or try again later."
                }
            } catch (e: Exception) {
                println("Exception during data fetch: ${e.message}")
                _errorMessage.value =
                    "An error occurred: ${e.message ?: "Unknown error"}"
            } finally {
                isFetching = false
                if (showLoading) _isLoading.value = false
                onComplete?.invoke()
            }
        }
    }

    fun goToScreen(index: Int) {
        _rootComponent.value?.screens?.get(index)?.let {
            _currentScreenId.value = it.id
        }
    }
}
