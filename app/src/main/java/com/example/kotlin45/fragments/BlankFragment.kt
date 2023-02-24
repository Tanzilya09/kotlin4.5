package com.example.kotlin45.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.kotlin45.R
import com.example.kotlin45.databinding.FragmentBlankBinding
import com.example.kotlin45.permissionCheckAndRequest
import com.example.kotlin45.setImage

class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPermissions()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { isGranted ->
        for (permission in isGranted) {
            when {
                permission.value -> fileChooseContract.launch("image/*")
                !shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    findNavController().navigate(R.id.action_blankFragment_to_dialogFragment)
                }
            }
        }
    }

    private fun setupPermissions() {
        binding.btnGallery.setOnClickListener {
            if (permissionCheckAndRequest(
                    requestPermissionLauncher,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                )
            ) {
                fileChooseContract.launch("image/*")
            }
        }
    }

    private val fileChooseContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                binding.gallery.setImage(imageUri.toString())
                uri = imageUri
            }
        }
}