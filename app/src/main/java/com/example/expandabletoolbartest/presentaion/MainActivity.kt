package com.example.expandabletoolbartest.presentaion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.example.expandabletoolbartest.presentaion.screens.NavGraphs
import com.example.expandabletoolbartest.presentaion.screens.destinations.ChooseScrollBehaviourScreenDestination
import com.example.expandabletoolbartest.ui.theme.ExpandableToolbarTestTheme
import com.example.expandabletoolbartest.viewmodels.MainViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExpandableToolbarTestTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    DestinationsNavHost(
                        navGraph =  NavGraphs.root,
                        engine = rememberAnimatedNavHostEngine(),
                        dependenciesContainerBuilder = {
                            dependency(viewModel)

                            when(destination) {
                                ChooseScrollBehaviourScreenDestination -> {
                                    dependency(destinationsNavigator)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpandableToolbarTestTheme {
        Greeting("Android")
    }
}