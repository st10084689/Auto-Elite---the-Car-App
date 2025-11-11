package com.harmless.autoelitekotlin.view.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.harmless.autoelitekotlin.R
import com.harmless.autoelitekotlin.view.adapters.FullScreenImageAdapter


class FullscreenImageDialog(
private val images: List<String>,
private val startPosition: Int,
private val onPageChanged: ((Int) -> Unit)? = null
) : DialogFragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var closeButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_fullscreen_image, container, false)
        viewPager = view.findViewById(R.id.fullscreen_viewpager)
        closeButton = view.findViewById(R.id.close_button)

        val adapter = FullScreenImageAdapter(images)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(startPosition, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onPageChanged?.invoke(position) // Notify main ViewPager
            }
        })

        closeButton.setOnClickListener { dismiss() }
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
    }
}

