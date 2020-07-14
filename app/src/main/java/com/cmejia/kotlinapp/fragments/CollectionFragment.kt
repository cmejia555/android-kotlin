package com.cmejia.kotlinapp.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2

import com.cmejia.kotlinapp.R
import com.cmejia.kotlinapp.adapters.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class CollectionFragment : Fragment() {

    private lateinit var tabLayout : TabLayout
    private lateinit var viewPager : ViewPager2
    private lateinit var bottomNavigation : BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigation = view.findViewById(R.id.bottom_nav)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = ViewPagerAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = resources.getText(R.string.details_tab)
                1 -> tab.text = resources.getText(R.string.gallery_tab)
                2 -> tab.text = getString(R.string.map_tab)
            }
        }.attach()

        checkPermission()

        val navController = findNavController()
        bottomNavigation.setupWithNavController(navController)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_phone -> {
                    contactByPhone()
                }
                R.id.menu_whatsapp -> {
                    contactByWhatsApp()
                }
            }
            true
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.CALL_PHONE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42)
            }
        } else {
            // Permission has already been granted
            //   callPhone()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 42) {
            // If request is cancelled, the result arrays are empty.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // permission was granted, yay!
                //  callPhone()
            } else {
                // permission denied, boo! Disable the
                // functionality
            }
            return
        }
    }

    private fun contactByPhone(){
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "1138007715"))
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        startActivity(intent)
    }

    private fun contactByWhatsApp() {
        val uri: Uri = Uri.parse("smsto:1138007715")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.setPackage("com.whatsapp")
        startActivity(Intent.createChooser(intent, ""))
    }

}
