# 🏹 Server-Driven UI - Main Application

The **Main Application Module** is responsible for initializing the app, managing dependency injection with **Koin**, and rendering the dynamically generated UI using **Jetpack Compose**. It integrates with the **Server-Driven UI architecture**, fetching and displaying Figma-generated screens.
For an overview of all modules see the [project README](../README.md).

## 📌 Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Kotlinx Serialization**
- **Koin (Dependency Injection)**
- **ViewModel & State Management**
- **Figma API Integration**
- **Server-Driven UI**

## 📁 `app` Module Structure

```
📂 app
 ┣ 📂 di
 ┃ ┣ 📜 Koin.kt
 ┃ ┣ 📜 Module.kt
 ┣ 📂 ui
 ┃ ┣ 📜 MainScreen.kt
 ┃ ┣ 📜 MainScreenViewModel.kt
 ┣ 📜 App.kt
```

---

## 📌 Core Components

### 🔧 Dependency Injection with Koin

#### `Koin.kt`
Initializes **Koin Dependency Injection** for the app.

```kotlin
fun initKoin(appDeclaration: KoinAppDeclaration? = null) =
    startKoin {
        appDeclaration?.invoke(this)
        modules(appModule)
    }
```

#### `Module.kt`
Registers dependencies, including **ViewModels**.

```kotlin
val appModule = module {
    viewModelOf(::MainScreenViewModel)
}
```

---

## 📌 Application Setup

### `App.kt`
The **entry point** of the application, applying **Koin Context** and rendering **MainScreen**.

```kotlin
@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            MainScreen()
        }
    }
}
```

---

## 📌 UI Components

### `MainScreen.kt`
Handles **screen rendering**, **gesture detection**, and **animated transitions** between screens.

```kotlin
@Composable
fun MainScreen() {
    KoinContext {
        val viewModel = koinViewModel<MainScreenViewModel>()
        val updateKey = viewModel.updateKey.value
        val currentScreenIndex = viewModel.currentScreenIndex.value
        val rootComponent by viewModel.rootComponent
        val isLoading by viewModel.isLoading

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 50) {
                            viewModel.goToPreviousScreen()
                        } else if (dragAmount < -50) {
                            viewModel.goToNextScreen()
                        }
                    }
                }
        ) {
            AnimatedContent(
                targetState = currentScreenIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(initialOffsetX = { it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { -it })
                    } else {
                        slideInHorizontally(initialOffsetX = { -it }) togetherWith
                        slideOutHorizontally(targetOffsetX = { it })
                    }
                }
            ) { targetIndex ->
                key(updateKey) {
                    rootComponent?.screens?.getOrNull(targetIndex)?.let { RenderScreen(it) }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.3f))
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}
```

---

## 📌 ViewModel & State Management

### `MainScreenViewModel.kt`
Handles **data fetching**, **state management**, and **screen navigation**.

```kotlin
class MainScreenViewModel : ViewModel() {
    private val _rootComponent = mutableStateOf<ViewScreens?>(null)
    val rootComponent: State<ViewScreens?> = _rootComponent

    private val _updateKey = mutableIntStateOf(0)
    val updateKey: State<Int> = _updateKey

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _currentScreenIndex = mutableIntStateOf(0)
    val currentScreenIndex: State<Int> = _currentScreenIndex

    private var isFetching = false

    init {
        fetchData()
    }

    fun fetchData(onComplete: (() -> Unit)? = null, showLoading: Boolean = false) {
        if (isFetching) return
        isFetching = true

        viewModelScope.launch {
            if (showLoading) _isLoading.value = true

            try {
                convertFigmaData(
                    apiKey = "YOUR_FIGMA_API_KEY",
                    fileKey = "YOUR_FIGMA_FILE_KEY"
                ) { result ->
                    isFetching = false
                    if (showLoading) _isLoading.value = false
                    if (result != null) {
                        _rootComponent.value = JsonParser.parseScreens(result.toString())
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
```

---

## 📌 How It Works

1️⃣ **Initialize Koin** for dependency injection.  
2️⃣ **Fetch Figma data** and parse it into a structured UI format.  
3️⃣ **Render screens dynamically** using Jetpack Compose.  
4️⃣ **Allow swipe gestures** to navigate between screens.  
5️⃣ **Show loading indicators** while fetching data.  

---
