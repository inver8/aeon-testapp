package ru.aeon.testapp.presentation.ui.payments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.FragmentPaymentsBinding

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.fragment_payments) {
    
    private val binding by viewBinding(FragmentPaymentsBinding::bind)
    private val viewModel: PaymentsViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
    }
}