package com.bbexcellence.a3awenni.models

import java.util.*

enum class OfferStatus {
    OPEN,
    BID_CONFIRMED,
    CLOSED
}

enum class EmergencyLevel {
    PLANNED,
    IMMEDIATE,
}

enum class Category {
    HEALTH_CARE,
    SOCIAL,
    BUSINESS,
    VOLUNTEERING
}

data class Offer(
    val id: Int,
    var title: String,
    val owner: AppUser,
    var content: String,
    var amount: Long,
    var renewable: Boolean,
    val creationDate: String,
    var deadline: String,
    var distance: Long,
    var status: OfferStatus = OfferStatus.OPEN,
    var emergency: EmergencyLevel = EmergencyLevel.IMMEDIATE,
    var category: Category = Category.VOLUNTEERING
) {
}