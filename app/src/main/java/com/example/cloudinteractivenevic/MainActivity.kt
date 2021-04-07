package com.example.cloudinteractivenevic

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.cloudinteractivenevic.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(
            R.id.my_host_fragment
        ) as NavHostFragment).navController
    }
    private lateinit var binding: ActivityMainBinding
    private val mCurrentLanguage: Locale = Locale("zh-tw")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        navController.setGraph(R.navigation.nav_graph, null)
    }
    override fun onNavigateUp(): Boolean {
        return navController.popBackStack()
    }
    override fun attachBaseContext(base: Context) {
        //設置語言、app 字體不隨系統字體設置改變
        MyApplication.LanguageContextWrapper.wrap(
            base.createConfigurationContext(
                base.resources.configuration.apply { fontScale = 1.0f }), mCurrentLanguage
        ).apply {
            super.attachBaseContext(this)
            //material:1.1.0以後有夜間模式會影響Android 4.4 ~ 7.0語言切換
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
                applyOverrideConfiguration(this.resources.configuration)
            }
        }
    }
}