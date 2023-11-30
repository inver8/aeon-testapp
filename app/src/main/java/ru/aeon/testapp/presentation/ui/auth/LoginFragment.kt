package ru.aeon.testapp.presentation.ui.auth

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.FragmentLoginBinding
import ru.aeon.testapp.domain.error.ErrorCode
import ru.aeon.testapp.domain.error.NetworkError
import ru.aeon.testapp.presentation.base.BaseFragment
import ru.aeon.testapp.presentation.model.auth.LoginUI
import ru.aeon.testapp.presentation.model.state.UIState
import ru.aeon.testapp.presentation.util.OnTextChanged
import ru.aeon.testapp.presentation.util.getString
import ru.aeon.testapp.presentation.util.setTextAndVisibility

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {
    
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupUI()
        subscribeToLoginState()
    }
    
    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.updatePadding(
                top = systemBarInsets.top, 
                bottom = systemBarInsets.bottom + imeInsets.bottom
            )
            return@setOnApplyWindowInsetsListener insets
        }
        
        binding.loginButton.setOnClickListener { onLoginClick() }
        binding.usernameEdit.addTextChangedListener(OnTextChanged(binding.usernameEdit) { _, _ -> clearError() })
        binding.passwordEdit.addTextChangedListener(OnTextChanged(binding.passwordEdit) { _, _ -> clearError() })
    }
    
    private fun subscribeToLoginState() {
        viewModel.loginState.collectUIState(
            state = { setupVisibility(it) },
            onError = { handleNetworkErrors(it) },
            onSuccess = { onSuccess(it) }
        )
    }
    
    private fun setupVisibility(state: UIState<*>) {
        if (state is UIState.Idle) {
            binding.errorMessage.isVisible = false
        }
    }
    
    private fun handleNetworkErrors(networkError: NetworkError) {
        when (networkError) {
            is NetworkError.Api -> onApiError(networkError.apiError?.errorCode)
        
            is NetworkError.Timeout, is NetworkError.Connection, is NetworkError.Unexpected -> onNetworkError(networkError.defaultUserMessageRes)
        }
    }
    
    private fun onApiError(errorCode: Int?) {
        when (errorCode) {
            ErrorCode.AUTH_INCORRECT_CREDENTIALS -> binding.errorMessage.setTextAndVisibility(R.string.auth_incorrect_credentials)
        }
    }
    
    private fun onNetworkError(@StringRes errorMessageRes: Int) {
        Snackbar.make(binding.root, errorMessageRes, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_button) { binding.loginButton.performClick() }
            .show()
    }
    
    private fun onSuccess(loginUI: LoginUI) {
        if (loginUI.success)
            findNavController().navigate(R.id.action_loginFragment_to_payments)
    }
    
    private fun onLoginClick() {
        val username = binding.usernameEdit.getString().trim()
        val password = binding.passwordEdit.getString()
        viewModel.loginWithUsernamePassword(username, password)
    }
    
    private fun clearError() {
        viewModel.setLoginStateIdle()
    }
    
}