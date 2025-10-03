package com.chaeny.moviechart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar()
        PeriodTabs()
        MovieList()
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 25.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.movie),
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search)
            )
        }
    }
}

@Composable
private fun PeriodTabs() {
    var selectedTab by rememberSaveable { mutableStateOf(TabType.DAILY) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp)
    ) {
        TabItem(
            text = stringResource(R.string.daily),
            isSelected = selectedTab == TabType.DAILY,
            onClick = { selectedTab = TabType.DAILY }
        )
        Spacer(modifier = Modifier.width(20.dp))
        TabItem(
            text = stringResource(R.string.weekly),
            isSelected = selectedTab == TabType.WEEKLY,
            onClick = { selectedTab = TabType.WEEKLY }
        )
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(2.dp)
                .background(if (isSelected) Color.Black else Color.Transparent)
        )
    }
}

@Composable
private fun MovieList() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(horizontal = 50.dp)
    ) {
        items(DummyMovieData.posterUrls) { imageUrl ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.movie_poster),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image),
                modifier = Modifier
                    .width(300.dp)
                    .height(450.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
    }
}

private object DummyMovieData {
    val posterUrls = listOf(
        "https://image.tmdb.org/t/p/w500/pf7vZxoLYtLQ366VNlGrjBxwL7A.jpg",
        "https://image.tmdb.org/t/p/w500/Amu0HNWfpxo2ZaulueNVxDLADz8.jpg",
        "https://image.tmdb.org/t/p/w500/m6Dho6hDCcL5KI8mOQNemZAedFI.jpg",
        "https://image.tmdb.org/t/p/w500/9RsHtbUMXMfHjkL74BhM7KFEozT.jpg",
        "https://image.tmdb.org/t/p/w500/cwvehMf8bnWwUhKOFR6qHTxg1VO.jpg",
        "https://image.tmdb.org/t/p/w500/gEVSN7rzQsypG4YfYObsPmMtYpP.jpg",
        "https://image.tmdb.org/t/p/w500/bvVoP1t2gNvmE9ccSrqR1zcGHGM.jpg",
        "https://image.tmdb.org/t/p/w500/eSNprN73xK9LKN8f5y5Ee446QzK.jpg",
        "https://image.tmdb.org/t/p/w500/kI9ffyOEwj0bdpttdPEtAVDFHxC.jpg",
        "https://image.tmdb.org/t/p/w500/rXyniH1Xyp3xksHzZ0wSU6IqDjh.jpg"
    )
}

private enum class TabType {
    DAILY,
    WEEKLY
}
