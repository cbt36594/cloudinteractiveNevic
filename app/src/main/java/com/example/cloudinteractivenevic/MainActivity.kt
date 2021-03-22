package com.example.cloudinteractivenevic

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cloudinteractivenevic.common.navigateForward
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