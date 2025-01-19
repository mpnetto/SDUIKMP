import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.sacada.sdui.appModule

fun initKoin(appDeclaration: KoinAppDeclaration? = null) =
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            appModule
        )
    }