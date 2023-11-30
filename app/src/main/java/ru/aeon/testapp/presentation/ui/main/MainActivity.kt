package ru.aeon.testapp.presentation.ui.main

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.ActivityMainBinding
import ru.aeon.testapp.domain.usecase.auth.IsAuthorizedUseCase
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    private val binding by viewBinding { ActivityMainBinding.inflate(layoutInflater) }
    
    @Inject
    internal lateinit var isAuthorized: IsAuthorizedUseCase
    
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
    
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        if (isAuthorized()) {
            val navHost = binding.navHostFragmentContentMain.getFragment<NavHostFragment>()
            navHost.navController.navigate(R.id.payments)
        }
    
        setContentView(binding.root)
    }
}