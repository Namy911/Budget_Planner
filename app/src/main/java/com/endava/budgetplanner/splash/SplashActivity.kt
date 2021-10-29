package com.endava.budgetplanner.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.endava.budgetplanner.MainActivity
import com.endava.budgetplanner.R
import com.endava.budgetplanner.authentication.ui.vm.RegisterViewModel
import com.endava.budgetplanner.common.ext.provideAppComponent
import com.endava.budgetplanner.data.api.ApiServiceImp
import com.endava.budgetplanner.data.repo.SplashRepositoryImp
import com.endava.budgetplanner.di.components.AppComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SplashActivity"

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = application.provideAppComponent().factories.create(SplashViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        observeState()
    }
    //stub to delete
    private fun observeState(){
        lifecycleScope.launch {
            viewModel.splashState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { viewState->
                    when(viewState){
                        SplashState.Loading -> {
                            Log.d(TAG, "observeState: loading config data")
                        }
                        is SplashState.LoadComplete ->{
                            Log.d(TAG, "User: ${viewState.data.name}/${viewState.data.email}")
                            startActivity(MainActivity.newIntent(this@SplashActivity))
                        }
                         is SplashState.Error -> {
                             Log.d(TAG, "observeState: Error splash state")
                        }
                    }
                }
        }
    }
}

