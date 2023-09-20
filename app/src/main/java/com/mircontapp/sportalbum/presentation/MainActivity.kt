package com.mircontapp.sportalbum.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.presentation.album.AlbumChooseViewModel
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

            SportAlbumTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(getString(R.string.app_name))
                            }
                        )
                    },
                    bottomBar = {
                        BottomBarNavigation(navController = navController)
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
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomBarNavigation(navController: NavController) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = {
                mainViewModel.routeSelected.value = NavigationItem.Album
            }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                mainViewModel.routeSelected.value = NavigationItem.Dashboard
            }) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null
                )
            }
            IconButton(onClick = {
                mainViewModel.routeSelected.value = NavigationItem.Games
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null
                )
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
        SportAlbumTheme {
            HomeButtons("Android")
        }
    }
}

