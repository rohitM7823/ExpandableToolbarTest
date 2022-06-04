package com.example.expandabletoolbartest.presentaion.screens

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollDispatcher
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.example.expandabletoolbartest.presentaion.FixedScrollFlagState
import com.example.expandabletoolbartest.presentaion.TopBarState
import com.example.expandabletoolbartest.presentaion.screentransitions.PrimaryTransitions
import com.example.expandabletoolbartest.presentaion.scrollflags.ExitUntilCollapsedState
import com.example.expandabletoolbartest.viewmodels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Composable
internal fun rememberTopBarState(heightRange: IntRange): TopBarState {
    return rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(heightRange)
    }
}

@RootNavGraph
@Destination(style = PrimaryTransitions::class)
@Composable
fun ScrollScreen(
    viewModel: MainViewModel,
    selectedScrollType: String
) {
    val topBarHeightRange = with(LocalDensity.current) {
        viewModel.minToolbarHeight.roundToPx()..viewModel.maxToolbarHeight.roundToPx()
    }
    val listState = rememberLazyListState()
    val topBarState = rememberTopBarState(heightRange = topBarHeightRange)
    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                topBarState.scrollTopLimitReached =
                    listState.firstVisibleItemIndex == 0 || listState.firstVisibleItemScrollOffset == 0
                topBarState.scrollOffset = topBarState.scrollOffset - available.y
                return Offset(0f, topBarState.consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = topBarState.height + topBarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, velocity ->
                            topBarState.scrollTopLimitReached =
                                listState.firstVisibleItemIndex == 0 || listState.firstVisibleItemScrollOffset == 0
                            topBarState.scrollOffset =
                                topBarState.scrollOffset - (value - (topBarState.height + topBarState.offset))
                            if (topBarState.scrollOffset == 0f) {
                                scope.coroutineContext.cancelChildren()
                            }
                        }
                    }
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    Box(
        modifier = Modifier.nestedScroll(connection = nestedScrollConnection)
    ) {
        LazyItems(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = topBarState.height }
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { scope.coroutineContext.cancelChildren() })
                },
            contentPaddingValues = PaddingValues(bottom = if (topBarState is FixedScrollFlagState) viewModel.minToolbarHeight else 0.dp)
        )
        ExpandableToolBar(
            modifier = Modifier
                .fillMaxWidth()

                .height(with(LocalDensity.current) { topBarState.height.toDp() }),
            progress = topBarState.progress
        )
    }
}

@Composable
fun ExpandableToolBar(modifier: Modifier, progress: Float) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Expanding Toolbar")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyItems(
    state: LazyListState,
    modifier: Modifier,
    contentPaddingValues: PaddingValues
) {
    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPaddingValues
    ) {
        items(30) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(6.dp),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Item ${it + 1}")
                }
            }
        }
    }
}
