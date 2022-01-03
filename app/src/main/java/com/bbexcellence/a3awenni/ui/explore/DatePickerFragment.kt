package com.bbexcellence.a3awenni.ui.explore

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bbexcellence.a3awenni.databinding.FragmentExploreBinding
import com.bbexcellence.a3awenni.databinding.FragmentNewOfferBinding
import java.text.DateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val sharedExploreViewModel: ExploreViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val dateFormatter = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
            // Do something for lollipop and above versions
            DateFormat.getDateInstance(DateFormat.SHORT, resources.configuration.locales[0])
        } else{
            // do something for phones running an SDK before lollipop
            DateFormat.getDateInstance(DateFormat.SHORT, resources.configuration.locale)
        }
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        sharedExploreViewModel.setDeadline(calendar, dateFormatter.format(calendar.time))
    }
}