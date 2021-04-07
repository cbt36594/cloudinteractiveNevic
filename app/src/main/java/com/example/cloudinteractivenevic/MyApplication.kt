package com.example.cloudinteractivenevic

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import com.example.cloudinteractivenevic.common.PreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import java.util.*

class MyApplication: Application() {

    private var mInstance: MyApplication? = null

    @Synchronized
    fun getInstance(): MyApplication? {
        return mInstance
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
    class LanguageContextWrapper(base: Context?) : ContextWrapper(base) {
        companion object {
            @JvmStatic
            fun wrap(context: Context, newLocale: Locale?): Context {
                var mContext = context
                val res = context.resources
                val configuration = res.configuration
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    configuration.setLocale(newLocale)
                    val localeList = LocaleList(newLocale)
                    LocaleList.setDefault(localeList)
                    configuration.setLocales(localeList)
                    mContext = context.createConfigurationContext(configuration)
                } else {
                    configuration.locale = newLocale
                    res.updateConfiguration(configuration, res.displayMetrics)
                }
                return ContextWrapper(mContext)
            }
        }
    }
    companion object {
        @JvmStatic
        lateinit var appContext: Context
            private set
        lateinit var preferencesHelper: PreferencesHelper
    }
}
val appModule = module {
//    viewModel { (loginType: LoginType) ->
//        HomeViewModel(
//            loginType = loginType
//        )
//    }
}