package com.mircontapp.sportalbum.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.presentation.navigation.NavGraph
import com.mircontapp.sportalbum.presentation.navigation.NavigationItem
import com.mircontapp.sportalbum.presentation.ui.theme.SportAlbumTheme
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val mainViewModel: MainViewModel by viewModels()

    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            mainViewModel.routeSelected.observe(this) {
                navController.navigate(it.route)
            }



            SportAlbumTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    bottomBar = {
                        BottomBarNavigation(arrayListOf(
                            NavigationItem.Album,
                            NavigationItem.Dashboard,
                            NavigationItem.Games
                        ))
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NavGraph(navController = navController, mainViewModel = mainViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomBarNavigation(items: List<NavigationItem>) {
        Box (
            Modifier.background(MaterialTheme.colorScheme.primary)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {

                items.forEach {
                    IconButton(onClick = {
                        mainViewModel.routeSelected.value = it
                    }) {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = null
                        )
                    }
                }

            }
        }


    }

    @Composable
    fun HomeButtons(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        BottomBarNavigation(arrayListOf(
            NavigationItem.Album,
            NavigationItem.Dashboard,
            NavigationItem.Games
        ))
    }
}

