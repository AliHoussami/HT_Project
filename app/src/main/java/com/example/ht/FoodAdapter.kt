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
            binding.textViewName.text = food.name
            binding.textViewCalories.text = "Calories: ${food.calories}"

            binding.buttonAdd.setOnClickListener { onFoodItemClick(food, true) }
            binding.buttonSubtract.setOnClickListener { onFoodItemClick(food, false) }
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