package com.harmless.autoelitekotlin.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.databinding.FragmentSellThreeBinding
import com.harmless.autoelitekotlin.databinding.FragmentSellTwoBinding
import com.harmless.autoelitekotlin.model.Car
import com.harmless.autoelitekotlin.view.activities.MainActivity
import com.harmless.autoelitekotlin.view.activities.SellActivities.SellCarActivity
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedBodyType
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedBrand
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedColor
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedDescription
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedMileage
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedModel
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedNewOrUsed
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedPrice
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedProvince
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedTransmission
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedVariant
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedWheelDrive
import com.harmless.autoelitekotlin.viewModel.SellCarViewModel.SellSession.selectedYear


class SellThreeFragment : Fragment() {
    private var _binding: FragmentSellThreeBinding? = null
    private val binding get() = _binding!!

    private lateinit var backButton: CardView
    private lateinit var continueButton: CardView

    private lateinit var frontImage: ImageView
    private lateinit var leftImage: ImageView
    private lateinit var rightImage: ImageView
    private lateinit var rearImage: ImageView
    private lateinit var additionalImage: ImageView

    private lateinit var frontImageCardView: CardView
    private lateinit var leftImageCardView: CardView
    private lateinit var rightImageCardView: CardView
    private lateinit var rearImageCardView: CardView


    private val selectedImages = mutableListOf<Uri>()
    private lateinit var pickImageLauncher: ActivityResultLauncher<String>
    private var currentImageTarget: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null && currentImageTarget != null) {
                // Display in the ImageView
                currentImageTarget?.setImageURI(uri)

                // Save in the list
                selectedImages.add(uri)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setUpListeners()
        navigationButtons()
    }

    private fun initViews(){
        backButton = binding.sellOneBackButton
        continueButton = binding.sellOneContinueButton

        frontImage = binding.cameraFrontImage
        leftImage = binding.cameraLeftImage
        rightImage = binding.cameraRightImage
        rearImage = binding.cameraRearImage

        frontImageCardView = binding.sellCarFrontImageCardview
        leftImageCardView = binding.sellCarImageLeftCardview
        rightImageCardView = binding.sellCarRightCardview
        rearImageCardView = binding.sellCarRearCardview
    }

    private fun setUpListeners() {
        // Front image
        frontImageCardView.setOnClickListener {
            currentImageTarget = frontImage
            pickImageLauncher.launch("image/*")
        }

        // Left image
        leftImageCardView.setOnClickListener {
            currentImageTarget = leftImage
            pickImageLauncher.launch("image/*")
        }

        // Right image
        rightImageCardView.setOnClickListener {
            currentImageTarget = rightImage
            pickImageLauncher.launch("image/*")
        }

        // Rear image
        rearImageCardView.setOnClickListener {
            currentImageTarget = rearImage
            pickImageLauncher.launch("image/*")
        }
    }

    private fun navigationButtons(){
            backButton.setOnClickListener {
                (requireActivity() as SellCarActivity).goToPreviousPage()
            }

        continueButton.setOnClickListener {
            val databaseRef = FirebaseDatabase.getInstance().getReference("cars")
            val storageRef = FirebaseStorage.getInstance().reference.child("car_images")

            // Upload images first
            val imageUris: List<Uri> = selectedImages  // must come from your image picker
            val downloadUrls = mutableListOf<String>()

            if (imageUris.isEmpty()) {
                Toast.makeText(requireContext(), "Please select at least one image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            imageUris.mapIndexed { index, uri ->
                val fileRef = storageRef.child("${System.currentTimeMillis()}_$index.jpg")
                fileRef.putFile(uri).continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    fileRef.downloadUrl
                }.addOnSuccessListener { downloadUri ->
                    downloadUrls.add(downloadUri.toString())

                    // Once all images uploaded, upload car
                    if (downloadUrls.size == imageUris.size) {

                        val carId = databaseRef.push().key ?: return@addOnSuccessListener

                        val car = Car(
                            BodyType = selectedBodyType ?: "",
                            IsNew = selectedNewOrUsed ?: false,
                            brand = selectedBrand?.brand ?: "",
                            color = selectedColor ?: "",
                            images = downloadUrls,
                            location = selectedProvince ?: "",
                            mileage = selectedMileage ?: 0,
                            model = selectedModel?.name ?: "",
                            price = selectedPrice?.toDouble() ?: 0.0,
                            provinces = selectedProvince ?: "",
                            transmission = selectedTransmission ?: "",
                            type = selectedBodyType ?: "",
                            year = selectedYear ?: 0,
                            wheelDrive = selectedWheelDrive ?: "",
                            variant = selectedVariant ?: "",
                            description = selectedDescription ?: ""
                        )

                        databaseRef.child(carId).setValue(car).addOnSuccessListener {
                            Toast.makeText(requireContext(), "Car uploaded successfully!", Toast.LENGTH_SHORT).show()

                            // ✅ Navigate back to MainActivity
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            requireActivity().finish() // optional, to close current fragment/activity

                        }.addOnFailureListener { e ->
                            Toast.makeText(requireContext(), "Failed to upload car: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Image upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }}
}