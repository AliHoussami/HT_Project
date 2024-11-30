package com.example.ht

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ht.data.model.Food
import com.example.ht.databinding.ItemFoodBinding

class FoodAdapter(
    private val onFoodItemClick: (Food, Boolean) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private var originalList = listOf<Food>() // Original list for filtering
    private var filteredList = listOf<Food>() // Current filtered list

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            // Set food name, calories, and initial quantity
            binding.textViewName.text = food.name
            binding.textViewCalories.text = "Calories: ${food.calories}"
            binding.textViewQuantity.text = "Qty: ${food.quantity}"

            // Set checkbox state based on food selection (unchecked by default)
            binding.checkboxSelectFood.isChecked = false // All checkboxes are unchecked by default

            // Handle checkbox changes to update selection
            binding.checkboxSelectFood.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    food.quantity = 1 // When checked, set quantity to 1
                } else {
                    food.quantity = 0 // When unchecked, set quantity to 0
                }
                updateFoodSelection(food, isChecked)
            }

            // Handle Add button click
            binding.buttonAdd.setOnClickListener {
                food.quantity++
                binding.textViewQuantity.text = "Qty: ${food.quantity}"
                binding.checkboxSelectFood.isChecked = true // Ensure checkbox is checked
                updateCalories(food, true)
            }

            // Handle Subtract button click
            binding.buttonSubtract.setOnClickListener {
                if (food.quantity > 1) {
                    food.quantity--
                    binding.textViewQuantity.text = "Qty: ${food.quantity}"
                    updateCalories(food, false)
                } else if (food.quantity == 1) {
                    binding.checkboxSelectFood.isChecked = false // Uncheck if quantity is 0
                    food.quantity = 0
                }
            }
        }

        private fun updateFoodSelection(food: Food, isSelected: Boolean) {
            // Trigger callback to update the total calories
            onFoodItemClick(food, isSelected)
        }

        private fun updateCalories(food: Food, isAdded: Boolean) {
            // Update total calories
            onFoodItemClick(food, isAdded)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun submitList(foods: List<Food>) {
        originalList = foods
        filteredList = foods
        // Set default quantity to 1 for all foods when they are added to the list
        filteredList.forEach { it.quantity = 1 }
        notifyDataSetChanged()
    }

    // Filter function for search bar
    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            originalList
        } else {
            originalList.filter { it.name.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
