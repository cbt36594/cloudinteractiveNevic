package com.example.cloudinteractivenevic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.cloudinteractivenevic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(
            R.id.my_host_fragment
        ) as NavHostFragment).navController
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {

                }
        navController.setGraph(R.navigation.nav_graph, null)

    }
    override fun onNavigateUp(): Boolean {
        return navController.popBackStack()
    }

}