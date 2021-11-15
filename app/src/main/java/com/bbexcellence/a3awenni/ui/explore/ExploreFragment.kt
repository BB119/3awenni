package com.bbexcellence.a3awenni.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.adapters.ExploreListAdapter
import com.bbexcellence.a3awenni.data.Datasource
import com.bbexcellence.a3awenni.databinding.FragmentExploreBinding
import com.bbexcellence.a3awenni.ui.CenterZoomLayoutManager

class ExploreFragment : Fragment() {

    private val exploreViewModel: ExploreViewModel by viewModels()
    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExploreBinding.inflate(inflater)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize layout data variables
        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.viewModel = exploreViewModel
        //_binding?.lifecycleOwner = this
        binding.exploreOffersRecyclerView.apply {
            adapter = ExploreListAdapter(Datasource().loadUserData())

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}