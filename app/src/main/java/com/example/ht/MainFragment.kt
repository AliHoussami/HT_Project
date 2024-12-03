package com.example.ht

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ht.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WaterTrackerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe water intake and update UI
        viewModel.waterIntake.observe(viewLifecycleOwner) { intake ->
            binding.waterIntakeTextView.text = "Water Intake: $intake ml"
            binding.waterProgressBar.progress = intake // Explicitly set progress
        }


        // Observe daily goal and update ProgressBar max value
        viewModel.dailyGoal.observe(viewLifecycleOwner) { goal ->
            binding.goalTextView.text = "Daily Goal: $goal ml"
            binding.waterProgressBar.max = goal // Update max value for progress bar
        }

        // Handle "Add Water" button click
        binding.addWaterButton.setOnClickListener {
            viewModel.addWater(250)
            Toast.makeText(requireContext(), "Added 250 ml of water", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}