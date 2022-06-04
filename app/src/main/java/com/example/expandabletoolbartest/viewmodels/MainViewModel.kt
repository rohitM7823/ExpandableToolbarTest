package com.example.expandabletoolbartest.viewmodels

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.expandabletoolbartest.presentaion.TopBarState
import com.example.expandabletoolbartest.presentaion.screens.destinations.ScrollScreenDestination
import com.example.expandabletoolbartest.presentaion.scrollflags.ScrollState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    val minToolbarHeight = 96.dp
    val maxToolbarHeight = 176.dp

    private lateinit var navigator: DestinationsNavigator

    fun initNavigator(destinationsNavigator: DestinationsNavigator) = kotlin.run {
        navigator = destinationsNavigator
    }

    val isExpanded = mutableStateOf(false)

    val scrollTypes = MutableStateFlow(
        listOf(
            "scroll",
            "enterAlways",
            "enterAlwaysCollapsed",
            "exitUntilCollapsed"
        )
    )


    val selected = mutableStateOf(scrollTypes.value.first())

    fun navigateToCheck() {
        navigator.navigate(ScrollScreenDestination(selected.value))
    }

    fun topBarState(heightRange: IntRange): TopBarState {
        return ScrollState(heightRange)
    }

}