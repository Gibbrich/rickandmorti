package com.github.gibbrich.rickandmorti.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.github.gibbrich.rickandmorti.R
import com.github.gibbrich.rickandmorti.core.manager.INavigationManager
import com.github.gibbrich.rickandmorti.di.DI
import com.github.gibbrich.rickandmorti.manager.NavigationManager
import com.github.gibbrich.rickandmorti.ui.fragment.CharacterListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationManager: INavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        DI.appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()

        (navigationManager as NavigationManager).navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onPause() {
        super.onPause()

        (navigationManager as NavigationManager).navController = null
    }
}
