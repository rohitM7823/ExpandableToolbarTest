package com.example.expandabletoolbartest.presentaion.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expandabletoolbartest.presentaion.screentransitions.PrimaryTransitions
import com.example.expandabletoolbartest.viewmodels.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination(style = PrimaryTransitions::class)
@Composable
fun ChooseScrollBehaviourScreen(
    viewModel: MainViewModel,
    navigator: DestinationsNavigator
) {
    SideEffect { viewModel.initNavigator(navigator) }
    val types = viewModel.scrollTypes.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(.9f), contentAlignment = Alignment.Center) {

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.isExpanded.value = true },
                shape = RoundedCornerShape(4.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = viewModel.selected.value)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "DropDown")
                }
            }

            DropdownMenu(
                expanded = viewModel.isExpanded.value,
                onDismissRequest = { viewModel.isExpanded.value = false },
                modifier = Modifier.fillMaxWidth(.9f)
            ) {
                types.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(text = type) },
                        onClick = {
                            viewModel.selected.value = type
                            viewModel.isExpanded.value = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = { viewModel.navigateToCheck() }) {
            Text(text = "Check Effect")
        }
    }
}