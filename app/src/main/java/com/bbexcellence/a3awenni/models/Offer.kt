package com.bbexcellence.a3awenni.models

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

enum class OfferStatus {
    OPEN,
    BID_CONFIRMED,
    CLOSED
}

enum class Category {
    HEALTH_CARE,
    SOCIAL,
    BUSINESS,
    VOLUNTEERING
}

@IgnoreExtraProperties
data class Offer(
    val id: Int? = null,
    var title: String? = null,
    val owner: AppUser? = null,
    var content: String? = null,
    var amount: Long? = null,
    var renewable: Boolean? = null,
    val creationTime: String? = null,
    var deadline: String? = null,
    var distance: Long? = null,
    var status: OfferStatus? = null,
    var category: Category? = null
) {
    fun getDeadlineString(): String {
        TODO("convert deadline long to string")
    }

    fun getTimeSinceCreation(): String {
        TODO("calculate time since the offer has been created")
    }
}