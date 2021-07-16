package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Card
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.material.icons.filled.LastPage
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.DefaultStrokeLineWidth
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AccompanistSampleTheme {
                Surface {
                    Sample()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun Sample() {
    Column(Modifier.fillMaxSize()) {
        // Display 10 items
        val pagerState = rememberPagerState(
            pageCount = 10,
            // We increase the offscreen limit, to allow pre-loading of images
            initialOffscreenLimit = 2,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) { page ->
                Card(
                    Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue


                            val scale = if (pageOffset < 0.0f) {
                                pageOffset + 1.0f
                            } else {
                                abs(1.0F - pageOffset)
                            }
                            scaleX = scale
                            scaleY = scale
                            // We animate the scaleX + scaleY, between 85% and 100%
//                                lerp(
//                                    start = 0.7f,
//                                    stop = 1.2f,
//                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                ).also { scale ->
//                                    scaleX = scale
//                                    scaleY = scale
//                                }

                            // We animate the alpha, between 50% and 100%
//                                alpha = lerp(
//                                    start = 0.0f,
//                                    stop = 1f,
//                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                                )
                            alpha = if (pageOffset >= 1.0f && pageOffset <= 1.0f) {
                                1.0f - (scale - 1.0f)
                            } else {
                                0.0f
                            }
                        }
                        .fillMaxWidth(0.8f)
                        .aspectRatio(1f)
                ) {
                    Box {
                        Image(
                            painter = rememberImagePainter(
                                data = rememberRandomSampleImageUrl(width = 600),
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }

        ActionsRow(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

}

@Composable
internal fun PagerSampleItem(
    page: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        // Our page content, displaying a random image
        Image(
            painter = rememberImagePainter(
                data = rememberRandomSampleImageUrl(width = 600),
            ),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        // Displays the page index
        Text(
            text = page.toString(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(MaterialTheme.colors.surface, RoundedCornerShape(4.dp))
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun ActionsRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    infiniteLoop: Boolean = false
) {
    Row(modifier) {
        val scope = rememberCoroutineScope()

        IconButton(
            enabled = infiniteLoop.not() && pagerState.currentPage > 0,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(0)
                }
            }
        ) {
            Icon(Icons.Default.FirstPage, null)
        }

        IconButton(
            enabled = infiniteLoop || pagerState.currentPage > 0,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }
        ) {
            Icon(Icons.Default.NavigateBefore, null)
        }

        IconButton(
            enabled = infiniteLoop || pagerState.currentPage < pagerState.pageCount - 1,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        ) {
            Icon(Icons.Default.NavigateNext, null)
        }

        IconButton(
            enabled = infiniteLoop.not() && pagerState.currentPage < pagerState.pageCount - 1,
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.pageCount - 1)
                }
            }
        ) {
            Icon(Icons.Default.LastPage, null)
        }
    }
}

@Composable
private fun ProfilePicture(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(4.dp, MaterialTheme.colors.surface)
    ) {
        Image(
            painter = rememberImagePainter(rememberRandomSampleImageUrl()),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
        )
    }
}

