package com.mircontapp.sportalbum.presentation.commons

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mircontapp.sportalbum.R
import com.mircontapp.sportalbum.SportAlbumApplication
import com.mircontapp.sportalbum.presentation.ui.theme.OrangeYellowD
import com.mircontapp.sportalbum.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.flow.forEach

@Composable
fun VideosScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column {
        val videosViewModel: VideosViewModel = hiltViewModel();
        Text(text = SportAlbumApplication.instance.getString(R.string.media), textAlign = TextAlign.Center, color = Color.White)
        mainViewModel.playerModel?.let {player->
            Text(text = player.name, textAlign = TextAlign.Center, fontSize = 16.sp, color = OrangeYellowD)
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(1.dp),
            ) {
                videosViewModel.links.value.forEach {
                    item {
                        AndroidView(
                            factory = { context ->
                                WebView(context).apply {
                                    settings.javaScriptEnabled = true
                                    webViewClient = WebViewClient()

                                    settings.loadWithOverviewMode = true
                                    settings.useWideViewPort = true
                                    settings.setSupportZoom(true)
                                }
                            },
                            update = { webView ->
                                webView.loadUrl(it)
                            }
                        )
                    }
                }

            }
        }

    }


}