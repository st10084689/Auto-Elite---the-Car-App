package com.harmless.autoelitekotlin.model.utils

data class CarModel(
    val name: String = "",
    val variants: MutableList<String> = mutableListOf()
) {
    fun getOrderedVariants(selectedVariants: List<String>): List<String> {
        val allList = mutableListOf("All")
        val sortedSelected = variants.filter { selectedVariants.contains(it) }
        val remaining = variants.filter { !selectedVariants.contains(it) }
        return allList + sortedSelected + remaining
    }
}
data class SelectedCar (
    val brand: String = "",
    val model: String = "",
    val variant: String = "",
    val isAll: Boolean = false
)