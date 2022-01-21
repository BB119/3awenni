package com.bbexcellence.a3awenni.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Offer(
    val id: String? = null,
    val owner: AppUser? = null,
    var title: String? = null,
    var content: String? = null,
    var price: Long? = null,
    val creationTime: Long? = null,
    var deadline: Long? = null,
    var distance: Long? = null,
    var status: String? = null,
    var category: String? = null
) {
}