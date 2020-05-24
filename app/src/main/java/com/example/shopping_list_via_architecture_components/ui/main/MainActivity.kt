package com.example.shopping_list_via_architecture_components.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.shopping_list_via_architecture_components.R
import com.example.shopping_list_via_architecture_components.databinding.ActivityMainBinding
import com.example.shopping_list_via_architecture_components.ui.base.BaseActivity
import com.example.shopping_list_via_architecture_components.ui.history.HistoryShoppingFragment
import com.example.shopping_list_via_architecture_components.ui.itemsList.ItemsListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val contentLayoutId = R.layout.activity_main

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val newInstance = ItemsListFragment.newInstance()
        startFragment(newInstance)
    }

    override fun setupBinding(binding: ActivityMainBinding) {
        setSupportActionBar(binding.toolbar)
        setupBottomNavigation(binding.bottomNavigation)
    }

    private fun setupBottomNavigation(bottomNavigation: BottomNavigationView) {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.item_list -> {
                    val newInstance = ItemsListFragment.newInstance()
                    startFragment(newInstance)
                }
                R.id.history -> {
                    val newInstance = HistoryShoppingFragment.newInstance()
                    startFragment(newInstance)
                }
            }
            true
        }
    }

    private fun startFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
