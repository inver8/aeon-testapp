package ru.aeon.testapp.presentation.ui.payments

import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import ru.aeon.testapp.R
import ru.aeon.testapp.databinding.PaymentRecyclerItemBinding
import ru.aeon.testapp.domain.model.Payment
import ru.aeon.testapp.presentation.util.FormatUtil.format
import ru.aeon.testapp.presentation.util.FormatUtil.moneyFormat

class PaymentRecyclerItem(
    private val payment: Payment
) : BindableItem<PaymentRecyclerItemBinding>(payment.id) {
    
    override fun getLayout() = R.layout.payment_recycler_item
    
    override fun initializeViewBinding(view: View) = PaymentRecyclerItemBinding.bind(view)
    
    override fun bind(binding: PaymentRecyclerItemBinding, position: Int) {
        binding.paymentTitle.text = payment.title
        binding.paymentAmount.text = payment.amount.moneyFormat()
        
        if (payment.created != null) {
            binding.paymentDateCaption.isVisible = true
            binding.paymentDate.text = payment.created.format()
        } else {
            binding.paymentDateCaption.isVisible = false
            binding.paymentDate.text = null
        } 
    }
    
    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is PaymentRecyclerItem)
            return payment == other.payment
    
        return super.hasSameContentAs(other)
    }
}