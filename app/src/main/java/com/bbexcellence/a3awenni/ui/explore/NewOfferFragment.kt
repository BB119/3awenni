package com.bbexcellence.a3awenni.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.databinding.FragmentNewOfferBinding
import com.bbexcellence.a3awenni.login.hideKeyboardWhenClickOutside
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout

private enum class NewOfferFields {
    TITLE,
    CONTENT,
    DEADLINE,
    CATEGORY,
    PRICE,
    STATUS
}


class NewOfferFragment : Fragment() {

    companion object {
        const val IS_NEW_OFFER = "isNew"
    }

    //private val exploreViewModel: ExploreViewModel by viewModels()
    private val sharedExploreViewModel: ExploreViewModel by activityViewModels()
    private var _binding: FragmentNewOfferBinding? = null
    var isNewOffer = false

    var offerStatus = ""
    var offerCategory = ""
    lateinit var categoryItems: Array<String>
    lateinit var statusItems: Array<String>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.explode)
        arguments?.let {
            isNewOffer = it.getBoolean(IS_NEW_OFFER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNewOfferBinding.inflate(inflater)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize layout data variables
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.viewModel = sharedExploreViewModel
        binding.fragment = this
        binding.lifecycleOwner = this

        // Hide keyboard when user clicks outside any of the edit texts
        binding.offerTitleEditText.hideKeyboardWhenClickOutside(context)
        binding.offerContentEditText.hideKeyboardWhenClickOutside(context)

        // Create category drop-down menu
        categoryItems = arrayOf(
            getString(R.string.category_business),
            getString(R.string.category_healthcare),
            getString(R.string.category_social),
            getString(R.string.category_free)
        )
        val categoryAdapter =
            ArrayAdapter(requireContext(), R.layout.category_menu_list_item, categoryItems)
        binding.offerCategoryMenu.setAdapter(categoryAdapter)
        binding.offerCategoryMenu.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = categoryItems[position]
            offerCategory = selectedItem
            sharedExploreViewModel.isOfferFree.value =
                (offerCategory == getString(R.string.category_free)) // selected item is "Free Service"
        }

        // Create status drop-down menu
        statusItems = arrayOf(
            getString(R.string.status_closed),
            getString(R.string.status_confirmed),
            getString(R.string.status_open)
        )
        val statusAdapter =
            ArrayAdapter(requireContext(), R.layout.category_menu_list_item, statusItems)
        binding.offerStatusMenu.setAdapter(statusAdapter)
        binding.offerStatusMenu.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = statusItems[position]
            offerStatus = selectedItem
        }
    }

    fun createNewOffer() {
        if (checkInputValidity(
                binding.offerTitleEditText,
                binding.offerTitleTextInputLayout,
                NewOfferFields.TITLE
            ) &&
            checkInputValidity(
                binding.offerContentEditText,
                binding.offerContentTextInputLayout,
                NewOfferFields.CONTENT
            ) &&
            checkInputValidity(inputType = NewOfferFields.DEADLINE) &&
            checkInputValidity(
                binding.offerCategoryMenu,
                binding.offerCategoryOuterMenu,
                NewOfferFields.CATEGORY
            ) &&
            ((!isNewOffer && checkInputValidity(
                binding.offerStatusMenu,
                binding.offerStatusOuterMenu,
                NewOfferFields.STATUS
            ))
                    || isNewOffer) &&
            ((!sharedExploreViewModel.isOfferFree.value!! && checkInputValidity(
                binding.offerPriceEditText,
                binding.offerPriceTextInputLayout,
                NewOfferFields.PRICE
            ))
                    || sharedExploreViewModel.isOfferFree.value!!)
        ) {
            returnToPreviousScreen()

            val enteredPriceString = binding.offerPriceEditText.text
            var enteredPrice: Long = 0
            if (!enteredPriceString.isNullOrEmpty()) {
                enteredPrice = enteredPriceString.toString().toLong()
            }
            sharedExploreViewModel.createOffer(
                binding.offerTitleEditText.text.toString(),
                binding.offerContentEditText.text.toString(),
                getString(R.string.status_open),
                offerCategory,
                enteredPrice
            )
        }
    }

    private fun returnToPreviousScreen() {
        findNavController().navigateUp()
    }

    fun cancelNewOffer() {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(R.string.cancel_dialog_title)
            setMessage(R.string.cancel_dialog_message)
            setCancelable(false)
            setPositiveButton(R.string.cancel_positive_button) { _, _ ->
                returnToPreviousScreen()
            }
            setNegativeButton(R.string.cancel_negative_button, null)
            show()
        }
    }

    fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(parentFragmentManager, "datePicker")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Check if text input is valid
     */
    private fun checkInputValidity(
        inputEditText: EditText? = null,
        inputLayout: TextInputLayout? = null,
        inputType: NewOfferFields
    ): Boolean {
        val inputText = inputEditText?.text?.toString()?.trim()
            ?: binding.offerUserDeadlineTextView.text.toString().trim()
        val isInputValid = inputText.isNotEmpty()
        if (isInputValid) {
            setErrorTextField(inputLayout, inputEditText, false, inputType)
        } else {
            setErrorTextField(inputLayout, inputEditText, true, inputType)
        }

        return isInputValid
    }

    /**
     * Set or clear text field error
     */
    private fun setErrorTextField(
        editTextLayout: TextInputLayout?,
        inputEditText: EditText?,
        error: Boolean,
        fieldType: NewOfferFields
    ) {
        val errorMessageStringId = when (fieldType) {
            NewOfferFields.TITLE -> R.string.try_again_offer_title
            NewOfferFields.CONTENT -> R.string.try_again_offer_content
            NewOfferFields.DEADLINE -> R.string.try_again_offer_deadline
            NewOfferFields.CATEGORY -> R.string.try_again_offer_category
            NewOfferFields.PRICE -> R.string.try_again_offer_price
            NewOfferFields.STATUS -> R.string.try_again_offer_status
        }

        if (error) {
            editTextLayout?.isErrorEnabled = true
            if (editTextLayout != null) {
                editTextLayout.error = getString(errorMessageStringId)
            } else {
                binding.offerUserDeadlineTextView.error = getString(errorMessageStringId)
            }
            inputEditText?.text = null
        } else {
            editTextLayout?.isErrorEnabled = false
            if (editTextLayout == null) {
                binding.offerUserDeadlineTextView.error = null
            }
        }
    }
}