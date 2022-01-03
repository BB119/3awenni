package com.bbexcellence.a3awenni.ui.explore

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbexcellence.a3awenni.models.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.database.DatabaseReference




class ExploreViewModel : ViewModel() {

    var database: FirebaseDatabase?
    private var currentUser: AppUser?

    private var deadlineCalendar: Calendar? = null
    private val _deadline = MutableLiveData<String>()
    val deadline get() = _deadline

    private val _offerCurrency = MutableLiveData<Currency>()
    val offerCurrency get() = _offerCurrency

    private val _isOfferFree = MutableLiveData<Boolean>()
    val isOfferFree get() = _isOfferFree

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
            currentUser = AppUser(it?.uid, it?.displayName)
        }
        database = Firebase.database
        _offersToExplore.value = arrayListOf()
        _isOfferFree.value = false
        Firebase.database.setPersistenceEnabled(true)
        addOfferEventListener()
        setCurrency(Currency.getInstance("USD")!!)
    }

    fun clearPreviousOfferData() {
        _deadline.value = ""
        deadlineCalendar = null
    }

    fun setCurrency(currency: Currency) {
        _offerCurrency.value = currency
    }

    fun checkVisibility(conditionVariable: Boolean): Int = if (conditionVariable) {
        View.GONE
    } else {
        View.VISIBLE
    }

    fun setDeadline(calendar: Calendar, dateString: String) {
        _deadline.value = dateString
        deadlineCalendar = calendar
    }

    fun createOffer(title: String, content: String, status: String, category: String, price: Long) {
        val dbRef = database!!.reference.child("offers")

        val key = dbRef.push().key!! // Adding offer entry with a random key
        val offer = Offer(key, currentUser, title, content, price,
            0, deadlineCalendar?.timeInMillis, 0, status, category)

        dbRef.child("$key/creationTime").setValue(ServerValue.TIMESTAMP)
        dbRef.child(key).setValue(offer)
    }

    private fun addOfferEventListener() {
        database!!.reference.child("offers").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _offersToExplore.value?.clear() // clear offers to load them again
                for (dataSnapshot in snapshot.children) {
                    val offer: Offer = dataSnapshot.getValue(Offer::class.java)!!
                    _offersToExplore.value?.add(offer)
                    _offersToExplore.value = _offersToExplore.value // To notify the observer
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        //}
    }

    override fun onCleared() {
        super.onCleared()
        database = null
    }
}