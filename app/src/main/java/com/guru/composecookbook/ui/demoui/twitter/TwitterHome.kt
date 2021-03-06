package com.guru.composecookbook.ui.demoui.twitter

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.ColumnScope.gravity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.guru.composecookbook.R
import com.guru.composecookbook.data.DemoDataProvider
import com.guru.composecookbook.theme.twitterColor
import com.guru.composecookbook.ui.Animations.FloatMultiStateAnimationExplode

@Composable
fun TwitterHome() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_twitter),
                        tint = twitterColor,
                        modifier = Modifier.fillMaxWidth().gravity(Alignment.CenterHorizontally)
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 8.dp,
                navigationIcon = {
                    Image(
                        asset = imageResource(id = R.drawable.p6),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                            .preferredSize(32.dp).clip(CircleShape)
                    )
                },
                actions = {
                    Icon(
                        asset = Icons.Default.StarBorder,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            val rippleExplode = remember { mutableStateOf(false) }
            FloatingActionButton(rippleExplode)
            if (rippleExplode.value) {
                FloatMultiStateAnimationExplode(duration = 300)
            }
        },
        bodyContent = {
            TwitterHomeContent()
        }
    )
}

@Composable
fun TwitterHomeContent() {
    val tweets = remember { DemoDataProvider.tweetList }
    LazyColumnFor(items = tweets) {
        TwitterListItem(tweet = it)
    }
}

@Composable
fun FloatingActionButton(rippleExplode: MutableState<Boolean>) {
    ExtendedFloatingActionButton(
        text = { Text(text = "Tweet") },
        icon = { Icon(asset = vectorResource(id = R.drawable.ic_twitter)) },
        onClick = { rippleExplode.value = !rippleExplode.value },
        backgroundColor = twitterColor
    )
}

@Preview
@Composable
fun ShowTwitterScreen() {
    TwitterHome()
}