package com.bbexcellence.a3awenni.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbexcellence.a3awenni.databinding.ExploreListItemBinding
import com.bbexcellence.a3awenni.databinding.OfferListItemBinding
import com.bbexcellence.a3awenni.models.Offer
import android.widget.TableRow
import android.widget.TextView
import com.bbexcellence.a3awenni.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ExploreListAdapter(private val dataSet: List<Offer>) :
    RecyclerView.Adapter<ExploreListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.offer_title_text_view)
        val iconButton: FloatingActionButton = view.findViewById(R.id.fab_service_icon)
        val contentTextView: TextView = view.findViewById(R.id.offer_content_text_view)
        val statusLinearLayout: LinearLayout = view.findViewById(R.id.status_linear_layout)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.explore_list_item, viewGroup, false)

        val height: Int = viewGroup.measuredHeight
        view.minimumHeight = height

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentOffer = dataSet[position]
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.titleTextView.text = currentOffer.title
        bindFab(viewHolder.iconButton, currentOffer.category)
        viewHolder.contentTextView.text = currentOffer.content
        bindLinearLayout(viewHolder.statusLinearLayout, currentOffer.status)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}



