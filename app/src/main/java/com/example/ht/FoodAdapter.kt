package com.example.ht

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ht.data.model.Food
import android.widget.CheckBox

class FoodAdapter(
    private val selectedFoods: Set<String>, // List of already selected food names
    private val onItemChecked: (Food, Boolean) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val foodList = mutableListOf<Food>()

    fun submitList(newList: List<Food>?) {
        foodList.clear()
        if (newList != null) {
            foodList.addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_view_name)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.text_view_calories)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox_select_food)

        fun bind(food: Food) {
            nameTextView.text = food.name
            caloriesTextView.text = "Calories: ${food.calories}"

            // Pre-check based on saved selectedFoods
            checkBox.setOnCheckedChangeListener(null) // Prevent recycling issues
            checkBox.isChecked = selectedFoods.contains(food.name)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                onItemChecked(food, isChecked)
            }
        }
    }
}