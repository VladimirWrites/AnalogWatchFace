package com.vlad1m1r.watchface.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.vlad1m1r.watchface.R
import com.vlad1m1r.watchface.settings.hands.hand.WatchFaceStateHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var navigator: Navigator

    val stateHolder: WatchFaceStateHolder by lazy {
        WatchFaceStateHolder(
            lifecycleScope,
            this@MainActivity,
        )
    }

    private lateinit var watchFaceCurrentSate: WatchFaceStateHolder.WatchFaceCurrentState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigator.goToSettingsFragment(this)

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            stateHolder.uiState.collect { uiState: WatchFaceStateHolder.EditWatchFaceUiState ->
                when (uiState) {
                    is WatchFaceStateHolder.EditWatchFaceUiState.Loading -> {
                        Log.d("TAG", "StateFlow Loading: ${uiState.message}")
                    }
                    is WatchFaceStateHolder.EditWatchFaceUiState.Success -> {
                        Log.d("TAG", "StateFlow Success.")
                        watchFaceCurrentSate = uiState.watchFaceCurrentState
                        Log.d("TEST", watchFaceCurrentSate.backgroundStyle.toString())
                    }
                    is WatchFaceStateHolder.EditWatchFaceUiState.Error -> {
                        Log.e("TAG", "Flow error: ${uiState.exception}")
                    }
                }

            }
        }
    }
}
