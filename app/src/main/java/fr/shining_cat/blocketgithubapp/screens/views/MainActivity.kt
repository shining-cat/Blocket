package fr.shining_cat.blocketgithubapp.screens.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.shining_cat.blocketgithubapp.commons.Logger
import fr.shining_cat.blocketgithubapp.databinding.ActivityMainBinding
import fr.shining_cat.blocketgithubapp.screens.viewmodels.MainViewModel
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.name

    private val mainViewModel: MainViewModel by viewModel()
    private val logger: Logger = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiBindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(uiBindings.root)

    }

}