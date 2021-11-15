package com.bbexcellence.a3awenni.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbexcellence.a3awenni.data.Datasource
import com.bbexcellence.a3awenni.models.Offer
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _offersToExplore = MutableLiveData<List<Offer>>()
    val offersToExplore: LiveData<List<Offer>> = _offersToExplore

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getExploreOffers()
    }

    private fun getExploreOffers() {
        viewModelScope.launch {
            try {
                _offersToExplore.value = Datasource().loadUserData()
            } catch (e: Exception) {
                _offersToExplore.value = listOf()
            }
        }
    }*/
}