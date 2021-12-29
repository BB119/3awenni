package com.bbexcellence.a3awenni.ui.explore

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbexcellence.a3awenni.models.AppUser
import com.bbexcellence.a3awenni.models.Offer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel() {

    var database: FirebaseDatabase?
    private var _currentUser: AppUser?

    private val _deadline = MutableLiveData<String>()
    val deadline get() = _deadline

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    private val _offersToExplore = MutableLiveData<ArrayList<Offer>>()
    val offersToExplore: LiveData<ArrayList<Offer>> = _offersToExplore

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        Firebase.auth.currentUser.let {
            _currentUser = AppUser(it?.uid, it?.displayName)
        }
        database = Firebase.database
        _offersToExplore.value = arrayListOf()
        addOfferEventListener()
    }

    fun getStatusVisibility(isOfferNew: Boolean): Int = if (isOfferNew) {
        View.GONE
    } else {
        View.VISIBLE
    }

    fun setDeadline(dateString: String) {
        _deadline.value = dateString
    }

    fun createOffer() {
    }

    private fun addOfferEventListener() {
        viewModelScope.launch {
            database!!.getReference("offers").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val newOffer = dataSnapshot.getValue(Offer::class.java)!!
                        _offersToExplore.value?.add(newOffer)
                        _offersToExplore.value = _offersToExplore.value // To notify the observer
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    override fun onCleared() {
        super.onCleared()
        database = null
        _currentUser = null
    }
}