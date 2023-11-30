package ru.aeon.testapp.presentation.ui.payments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.FragmentPaymentsBinding
import ru.aeon.testapp.domain.error.NetworkError
import ru.aeon.testapp.presentation.base.BaseFragment
import ru.aeon.testapp.presentation.model.payments.PaymentsUI
import ru.aeon.testapp.presentation.model.state.UIState
import ru.aeon.testapp.presentation.ui.main.MainActivity
import ru.aeon.testapp.presentation.util.dp
import java.util.stream.Collectors

@AndroidEntryPoint
class PaymentsFragment : BaseFragment(R.layout.fragment_payments) {
    
    private val binding by viewBinding(FragmentPaymentsBinding::bind)
    private val viewModel: PaymentsViewModel by viewModels()
    
    private val mainSection = Section()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        
        setupUI()
        setupRequest()
        subscribeToPaymentsState()
    }
    
    private fun setupUI() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updatePadding(top = systemBarInsets.top)
            binding.recycler.updatePadding(bottom = systemBarInsets.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
        
        binding.swipeRefresh.setOnRefreshListener { viewModel.fetchPayments() }
        binding.logoutButton.setOnClickListener { onLogoutClick() }
        
        val adapter = GroupieAdapter().apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            add(mainSection)
        }
        
        val dividerDecorator = MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL).apply {
            dividerInsetStart = 8.dp()
            dividerInsetEnd = 8.dp()
        }
        
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(dividerDecorator)
    }
    
    private fun setupRequest() {
        if (viewModel.paymentsState.value is UIState.Idle)
            viewModel.fetchPayments()
    }
    
    private fun subscribeToPaymentsState() {
        viewModel.paymentsState.collectUIState(
            state = { setupVisibility(it) },
            onError = { handleNetworkErrors(it) },
            onSuccess = { onSuccess(it) }
        )
    }
    
    private fun setupVisibility(state: UIState<*>) {
        binding.swipeRefresh.isRefreshing = state is UIState.Loading
    }
    
    private fun handleNetworkErrors(networkError: NetworkError) {
        when (networkError) {
            is NetworkError.Api -> Toast.makeText(requireContext(), networkError.apiError?.errorMessage, Toast.LENGTH_LONG).show()
            
            is NetworkError.Timeout, is NetworkError.Connection, is NetworkError.Unexpected -> onNetworkError(networkError.defaultUserMessageRes)
        }
    }
    
    private fun onNetworkError(@StringRes errorMessageRes: Int) {
        Snackbar.make(binding.root, errorMessageRes, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_button) { viewModel.fetchPayments() }
            .show()
    }
    
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().moveTaskToBack(true)
        }
    }
    
    private fun onSuccess(paymentsUI: PaymentsUI) {
        val paymentItems = paymentsUI.payments.stream()
            .map { payment -> PaymentRecyclerItem(payment) }
            .collect(Collectors.toList())
        
        mainSection.update(paymentItems)
    }
    
    private fun onLogoutClick() {
        fun logout() {
            viewModel.logout()
            val mainActivity = requireActivity()
            val intent = Intent(mainActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mainActivity.finish()
            startActivity(intent)
        }
        
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.logout_dialog_confirm) { _, _ -> logout() }
            .setNegativeButton(R.string.logout_dialog_cancel) { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }
}