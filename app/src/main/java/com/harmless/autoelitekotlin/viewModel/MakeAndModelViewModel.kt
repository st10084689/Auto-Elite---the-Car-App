package com.harmless.autoelitekotlin.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModel
import com.harmless.autoelitekotlin.model.utils.SelectedCar
import com.harmless.autoelitekotlin.model.utils.SelectedValues

interface CarBrandCallback {
    fun onCarBrandLoaded(carBrands: List<CarBrand>)
    fun onCancelled(error: DatabaseError)
}

class MakeAndModelViewModel {

    private var carBrands = mutableListOf<CarBrand>()
    val selectedCars = MutableLiveData<MutableList<SelectedCar>>(mutableListOf())

    /**
     * Loads all car brands and their models from Firebase.
     * The data is stored under the "carRecycler" path.
     */
    fun getCarBrand(callback: CarBrandCallback) {
        val carsRef = FirebaseDatabase.getInstance().getReference("carRecycler")
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                carBrands.clear()
                for (carSnapshot in snapshot.children) {
                    val carBrand = carSnapshot.getValue(CarBrand::class.java)
                    if (carBrand != null) carBrands.add(carBrand)
                }
                callback.onCarBrandLoaded(carBrands)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.onCancelled(error)
            }
        }
        carsRef.addValueEventListener(listener)
    }

    /**
     * Returns the currently loaded list of car brands.
     */
    fun getCarBrand(): List<CarBrand> = carBrands

    /**
     * Used to determine if a brand item should be expandable
     * (i.e., has model data to show).
     */
    fun isExpandable(carsBrandList: List<CarModel>): Boolean =
        carsBrandList.isNotEmpty()

    /**
     * Updates the LiveData when selection changes.
     */
    private fun updateList(newList: MutableList<SelectedCar>) {
        selectedCars.value = newList
    }

    // ------------------------------------------------------------
    // SELECTION LOGIC
    // ------------------------------------------------------------

    fun toggleBrand(brand: String, models: List<String>) {
        val list = selectedCars.value ?: mutableListOf()
        val brandSelected = list.any { it.brand == brand && it.isAll && it.model.isBlank() }

        if (brandSelected) {
            list.removeAll { it.brand == brand }
        } else {
            list.removeAll { it.brand == brand }
            list.add(SelectedCar(brand, "", "All", true))
        }

        updateList(list)
        syncToSelectedValues() // ✅ add this
    }

    fun toggleModel(brand: String, model: String, variants: List<String>) {
        val list = selectedCars.value ?: mutableListOf()
        val modelSelected = list.any { it.brand == brand && it.model == model && it.isAll }

        if (modelSelected) {
            list.removeAll { it.brand == brand && it.model == model }
        } else {
            list.removeAll { it.brand == brand && it.model.isBlank() }
            list.add(SelectedCar(brand, model, "All", true))
        }

        updateList(list)
        syncToSelectedValues() // ✅ add this
    }

    fun toggleVariant(brand: String, model: String, variant: String, allVariants: List<String>) {
        val list = selectedCars.value ?: mutableListOf()
        val existing = list.find { it.brand == brand && it.model == model && it.variant == variant }

        when {
            variant == "All" -> {
                list.removeAll { it.brand == brand && it.model == model }
                list.add(SelectedCar(brand, model, "All", true))
            }
            existing != null -> {
                list.remove(existing)
                val hasAny = list.any { it.brand == brand && it.model == model && !it.isAll }
                if (!hasAny) list.add(SelectedCar(brand, model, "All", true))
            }
            else -> {
                list.removeAll { it.brand == brand && it.model == model && it.isAll }
                list.add(SelectedCar(brand, model, variant))
            }
        }

        updateList(list)
        syncToSelectedValues() // ✅ add this
    }

    /**
     * Ensures “All” is always first, followed by selected variants,
     * then unselected variants.
     */
    private fun sortVariants(variants: List<String>, selectedVariants: List<String>): List<String> {
        val allList = mutableListOf("All")
        val selected = variants.filter { selectedVariants.contains(it) }
        val remaining = variants.filter { !selectedVariants.contains(it) && it != "All" }
        return allList + selected + remaining
    }

    // ------------------------------------------------------------
    // SELECTION CHECKS
    // ------------------------------------------------------------

    fun isBrandSelected(brand: String): Boolean =
        selectedCars.value?.any { it.brand == brand && it.isAll && it.model.isBlank() } == true

    fun isModelSelected(brand: String, model: String): Boolean =
        selectedCars.value?.any { it.brand == brand && it.model == model && it.isAll } == true

    fun isVariantSelected(brand: String, model: String, variant: String): Boolean =
        selectedCars.value?.any { it.brand == brand && it.model == model && it.variant == variant } == true

    // returns true if there's any selection for the brand (any model/variant)
    fun hasAnySelectionForBrand(brand: String): Boolean {
        return selectedCars.value?.any { it.brand == brand } == true
    }

    // returns true if there is any selection for the brand+model (specific variants or "All")
    fun hasSelectionsForModel(brand: String, model: String): Boolean {
        return selectedCars.value?.any { it.brand == brand && it.model == model } == true
    }

    private fun syncToSelectedValues() {
        val selectedList = selectedCars.value ?: return

        // Clear existing list
        SelectedValues.carBrandsSelected.clear()

        // Group by brand
        val groupedByBrand = selectedList.groupBy { it.brand }

        for ((brand, selections) in groupedByBrand) {
            // Group by model
            val models = selections
                .filter { it.model.isNotBlank() }
                .groupBy { it.model }
                .map { (modelName, variants) ->
                    // Collect variants (filter out "All" unless it’s the only one)
                    val variantList = variants.map { it.variant }
                        .filter { it != "All" || variants.size == 1 }
                        .toMutableList()

                    CarModel(
                        name = modelName,
                        variants = if (variantList.isEmpty()) mutableListOf("All") else variantList
                    )
                }
                .toMutableList()

            SelectedValues.carBrandsSelected.add(
                CarBrand(
                    brand = brand,
                    models = if (models.isEmpty()) mutableListOf(CarModel("All", mutableListOf("All"))) else models
                )
            )
        }

        Log.d("SelectedValuesSync", "Synced: ${SelectedValues.carBrandsSelected}")
    }


}


