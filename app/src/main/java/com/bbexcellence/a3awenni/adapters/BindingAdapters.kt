package com.bbexcellence.a3awenni.adapters

import android.content.res.ColorStateList
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.bbexcellence.a3awenni.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


@BindingAdapter("category")
fun bindFab(fab: FloatingActionButton, category: String) {
    val appResources = fab.context

    val imgResourceId = when (category) {
        appResources.getString(R.string.category_healthcare) -> R.drawable.ic_health_care_category
        appResources.getString(R.string.category_social) -> R.drawable.ic_social_category
        appResources.getString(R.string.category_business) -> R.drawable.ic_business_category
        appResources.getString(R.string.category_free) -> R.drawable.ic_volunteering_category
        else -> R.drawable.ic_volunteering_category
    }
    fab.setImageResource(imgResourceId)
}

@BindingAdapter("status")
fun bindLinearLayout(linearLayout: LinearLayout, status: String) {
    val viewList = linearLayout.children.toList()
    val imageView = viewList[0] as FloatingActionButton
    val textView = viewList[1] as TextView

    val appResources = linearLayout.context.resources

    val imgBgdColorId = when (status) {
        appResources.getString(R.string.status_closed) -> R.color.red
        appResources.getString(R.string.status_confirmed) -> R.color.purple_500
        else -> R.color.green
    }

    imageView.backgroundTintList =
        ColorStateList.valueOf(appResources.getColor(imgBgdColorId))
    textView.text = status
}
