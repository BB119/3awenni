package com.bbexcellence.a3awenni.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AppUser(var id: String? = null, var name: String? = null) {
}