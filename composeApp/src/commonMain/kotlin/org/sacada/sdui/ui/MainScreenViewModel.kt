package org.sacada.sdui.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sacada.core.model.ViewScreens
import org.sacada.core.util.JsonParser
import org.sacada.jsonbuilder.convertFigmaData

class MainScreenViewModel(
//    application: Application
) : ViewModel() {
    private val _rootComponent = mutableStateOf<ViewScreens?>(null)
    val rootComponent: State<ViewScreens?> = _rootComponent

    private val _updateKey = mutableIntStateOf(0)
    val updateKey: State<Int> = _updateKey

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentScreenIndex = mutableIntStateOf(0)
    val currentScreenIndex: State<Int> = _currentScreenIndex

    private var isFetching = false

//    private val screenDao = (application as CustomApplication).database.screenDao()

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

            try {
//                val savedScreen = screenDao.getScreen()
//                if (savedScreen != null) {
//                    _rootComponent.value = JsonParser.parseScreens(savedScreen.jsonData)
//                }
                convertFigmaData(
                    apiKey = "figd_xjEUEhJs8g0pBeToo8tGr9HqEYbIniWSSyXcleIh",
                    fileKey = "AoKFU3cc2C8hr74wq6lDca",
                ) { result ->
                    isFetching = false
                    if (showLoading) _isLoading.value = false
                    if (result != null) {
                        _rootComponent.value = JsonParser.parseScreens(result.toString())
//                        viewModelScope.launch {
//                            screenDao.insert(ScreenEntity(jsonData = result.toString()))
//                        }
//                        _updateKey.intValue++
                    } else {
                        println("Error converting the Figma file.")
                    }
                }
            } finally {
                onComplete?.invoke()
            }
        }
    }

    fun goToNextScreen() {
        val currentIndex = _currentScreenIndex.value
        _rootComponent.value?.screens?.let {
            if (currentIndex < it.size - 1) {
                _currentScreenIndex.value = currentIndex + 1
            }
        }
    }

    fun goToPreviousScreen() {
        val currentIndex = _currentScreenIndex.value
        if (currentIndex > 0) {
            _currentScreenIndex.value = currentIndex - 1
        }
    }
}
