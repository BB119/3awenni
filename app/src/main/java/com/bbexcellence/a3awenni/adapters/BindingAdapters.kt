package com.bbexcellence.a3awenni.adapters

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.models.Category
import com.bbexcellence.a3awenni.models.Offer
import com.bbexcellence.a3awenni.models.OfferStatus
import com.google.android.material.floatingactionbutton.FloatingActionButton


/*@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Offer>?) {
    val adapter = recyclerView.adapter as ExploreListAdapter
    adapter.submitList(data)
}*/

@BindingAdapter("category")
fun bindFab(fab: FloatingActionButton, category: Category) {
    val imgResourceId = when (category) {
        Category.HEALTH_CARE -> R.drawable.ic_health_care_category
        Category.SOCIAL -> R.drawable.ic_social_category
        Category.BUSINESS -> R.drawable.ic_business_category
        Category.VOLUNTEERING -> R.drawable.ic_volunteering_category
    }
    fab.setImageResource(imgResourceId)
}

@BindingAdapter("status")
fun bindLinearLayout(linearLayout: LinearLayout, status: OfferStatus) {
    val viewList = linearLayout.children.toList()
    val imageView = viewList[0] as ImageView
    val textView = viewList[1] as TextView
    var imgBgdColorId = R.color.green


    val statusText = when(status) {
        OfferStatus.CLOSED -> {
            imgBgdColorId = R.color.red
            "Closed"
        }
        OfferStatus.BID_CONFIRMED -> {
            imgBgdColorId = R.color.purple_500
            "Bid Confirmed"
        }
        OfferStatus.OPEN -> "Open"
    }

    imageView.setBackgroundColor(imgBgdColorId)
    textView.text =statusText
}
