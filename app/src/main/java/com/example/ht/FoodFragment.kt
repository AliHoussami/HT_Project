package com.example.ht

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ht.data.model.Food
import com.example.ht.data.network.FoodsRetrofitInstance
import com.example.ht.databinding.FragmentFoodBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FoodAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("FoodPrefs", Context.MODE_PRIVATE)
        val totalCalories = sharedPreferences.getInt("totalCalories", 0)
        updateTotalCaloriesDisplay(totalCalories)

        setupRecyclerView()
        setupSearchBar()
        fetchFoods()

        // Reset calories button functionality
        binding.buttonResetCalories.setOnClickListener {
            sharedPreferences.edit().putInt("totalCalories", 0).apply()
            updateTotalCaloriesDisplay(0)
            Toast.makeText(requireContext(), "Calories reset!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = FoodAdapter { food, change ->
            val currentCalories = sharedPreferences.getInt("totalCalories", 0)
            val newCalories = if (change) {
                currentCalories + food.calories
            } else {
                (currentCalories - food.calories).coerceAtLeast(0)
            }
            sharedPreferences.edit().putInt("totalCalories", newCalories).apply()
            updateTotalCaloriesDisplay(newCalories)
        }

        binding.recyclerViewFood.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFood.adapter = adapter
    }

    private fun setupSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString()) // Filter the adapter list based on search input
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchFoods() {
        FoodsRetrofitInstance.apiService.getAllFoods().enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if (response.isSuccessful) {
                    response.body()?.let { foods ->
                        adapter.submitList(foods) // Pass the list to the adapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch foods", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateTotalCaloriesDisplay(calories: Int) {
        binding.textTotalCalories.text = getString(R.string.total_calories, calories)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
