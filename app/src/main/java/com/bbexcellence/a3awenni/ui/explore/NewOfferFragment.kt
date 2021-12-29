package com.bbexcellence.a3awenni.ui.explore

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.adapters.ExploreListAdapter
import com.bbexcellence.a3awenni.databinding.FragmentExploreBinding
import com.bbexcellence.a3awenni.databinding.FragmentNewOfferBinding
import com.bbexcellence.a3awenni.login.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout


/**
 * A simple [Fragment] subclass.
 * Use the [NewOfferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewOfferFragment : Fragment() {

    companion object {
        const val IS_NEW_OFFER = "isNew"
    }

    //private val exploreViewModel: ExploreViewModel by viewModels()
    private val sharedExploreViewModel: ExploreViewModel by activityViewModels()
    private var _binding: FragmentNewOfferBinding? = null
    var isNewOffer = false

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
        val categoryItems = resources.getStringArray(R.array.offer_category_array)
        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.category_menu_list_item, categoryItems)
        binding.offerCategoryMenu.setAdapter(categoryAdapter)

        // Create status drop-down menu
        val statusItems = resources.getStringArray(R.array.offer_status_array)
        val statusAdapter = ArrayAdapter(requireContext(), R.layout.category_menu_list_item, statusItems)
        binding.offerStatusMenu.setAdapter(statusAdapter)
    }

    fun createNewOffer() {
        returnToPreviousScreen()
        sharedExploreViewModel.createOffer()
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
}