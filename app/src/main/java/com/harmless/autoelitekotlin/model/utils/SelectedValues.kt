import com.harmless.autoelitekotlin.model.CarBrand
import com.harmless.autoelitekotlin.model.CarModel

object SelectedValues {

     var carBrandsSelected = mutableListOf<CarBrand>()

     var selectedType:String? = null

     var selectedYear = mutableListOf<Int>()

     var selectedPrice = mutableListOf<Double>()

     var selectedColor = mutableListOf<String>()

     var selectedProvince = mutableListOf<String>()

     var selectedFuelType:String? = null

     var isNewOrUsed:String? = null

     var selectedMaxMileage:Int? = null

     var selectedMinMileage:Int? = null

     var selectedMileage:String? = null




}