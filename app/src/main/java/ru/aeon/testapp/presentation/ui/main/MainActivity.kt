package ru.aeon.testapp.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.ActivityMainBinding
import ru.aeon.testapp.domain.usecase.auth.IsLoggedInUseCase
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val binding by viewBinding { ActivityMainBinding.inflate(layoutInflater) }
    
    @Inject
    internal lateinit var isLoggedIn: IsLoggedInUseCase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        val graph = if (isLoggedIn()) R.navigation.payments else R.navigation.auth
        val navHost = binding.navHostFragmentContentMain.getFragment<NavHostFragment>()
        navHost.navController.setGraph(graph)
    
        setContentView(binding.root)
    }
}