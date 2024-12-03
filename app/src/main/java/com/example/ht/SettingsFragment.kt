package com.example.ht


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ht.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WaterTrackerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle "Reset Water Intake" button click
        binding.resetButton.setOnClickListener {
            viewModel.resetWaterIntake()
            Toast.makeText(requireContext(), "Water intake reset", Toast.LENGTH_SHORT).show()
        }

        // Handle "Set Daily Goal" button click
        binding.setGoalButton.setOnClickListener {
            val goal = binding.dailyGoalEditText.text.toString().toIntOrNull()
            if (goal != null && goal > 0) {
                viewModel.setDailyGoal(goal)
                Toast.makeText(requireContext(), "Daily goal set to $goal ml", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Invalid goal value", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}