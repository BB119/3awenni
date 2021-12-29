package com.bbexcellence.a3awenni.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.adapters.ExploreListAdapter
import com.bbexcellence.a3awenni.databinding.FragmentExploreBinding
import com.bbexcellence.a3awenni.models.Offer

class ExploreFragment : Fragment() {

    //private val exploreViewModel: ExploreViewModel by viewModels()
    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val sharedExploreViewModel: ExploreViewModel by activityViewModels()

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
        binding.viewModel = sharedExploreViewModel
        binding.fragment = this
        //_binding?.lifecycleOwner = this

        binding.exploreOffersRecyclerView.setHasFixedSize(true)
        binding.exploreOffersRecyclerView.adapter =
            ExploreListAdapter(sharedExploreViewModel.offersToExplore.value!!)

        // Create the observer which updates the UI.
        val offersObserver = Observer<ArrayList<Offer>> { _ ->
            // Update the recycler view
            binding.exploreOffersRecyclerView.adapter?.notifyDataSetChanged()
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        sharedExploreViewModel.offersToExplore.observe(this, offersObserver)
    }

    /**
     * Start a new offer fragment
     */
    fun startNewOfferFragment() {
        //Clear any preexisting offer deadline
        sharedExploreViewModel.clearDeadline()
        val action = ExploreFragmentDirections.actionNavigationExploreToNewOfferFragment(isNew = true)
        findNavController().navigate(action)
        //findNavController().navigate(R.id.action_navigation_explore_to_newOfferFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}