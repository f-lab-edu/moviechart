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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val movies by viewModel.movies.collectAsStateWithLifecycle()
    val selectedType by viewModel.selectedType.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            TopBar()
            PeriodTypes(
                selectedType = selectedType,
                onTypeSelected = viewModel::onTypeSelected
            )
            MovieList(movies)
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black
            )
        }
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
private fun PeriodTypes(
    selectedType: PeriodType,
    onTypeSelected: (PeriodType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PeriodType.entries.forEach { type ->
            TypeItem(
                periodType = type,
                isSelected = selectedType == type,
                onClick = { onTypeSelected(type) }
            )
        }
    }
}

@Composable
private fun TypeItem(
    periodType: PeriodType,
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
            text = when (periodType) {
                PeriodType.DAILY -> stringResource(R.string.daily)
                PeriodType.WEEKLY -> stringResource(R.string.weekly)
            },
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
private fun MovieList(movies: List<Movie>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(horizontal = 50.dp)
    ) {
        items(
            items = movies,
            key = { movie -> movie.id }
        ) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
private fun MovieItem(movie: Movie) {
    Column {
        Box(
            modifier = Modifier
                .width(POSTER_WIDTH.dp)
                .height(POSTER_HEIGHT.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            MoviePoster(posterUrl = movie.posterUrl)
            MovieRank(rank = movie.rank)
        }
        MovieTitle(
            title = movie.name,
            width = POSTER_WIDTH.dp
        )
        MovieInfo(
            salesShareRate = movie.salesShareRate,
            accumulatedAudience = movie.accumulatedAudience,
            width = POSTER_WIDTH.dp
        )
    }
}

@Composable
private fun MovieTitle(
    title: String,
    width: Dp
) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .width(width)
            .padding(top = 20.dp)
    )
}

@Composable
private fun MovieInfo(
    salesShareRate: String,
    accumulatedAudience: String,
    width: Dp
) {
    Text(
        text = stringResource(R.string.movie_info, salesShareRate, formatAudience(accumulatedAudience)),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = Color.Gray,
        modifier = Modifier
            .width(width)
            .padding(top = 8.dp)
    )
}

@Composable
private fun formatAudience(audience: String): String {
    val audienceInt = audience.toIntOrNull() ?: 0
    return if (audienceInt >= 10000) {
        stringResource(R.string.audience_acc, audienceInt / 10000)
    } else {
        audience
    }
}

@Composable
private fun MoviePoster(posterUrl: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.movie_poster),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.loading_img),
        error = painterResource(R.drawable.ic_broken_image),
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun MovieRank(rank: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        Text(
            text = rank,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

private const val POSTER_WIDTH = 300
private const val POSTER_HEIGHT = 450

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    TopBar()
}

@Preview(showBackground = true)
@Composable
private fun PeriodTypesPreview() {
    PeriodTypes(
        selectedType = PeriodType.WEEKLY,
        onTypeSelected = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun TypeItemPreview() {
    TypeItem(
        periodType = PeriodType.DAILY,
        isSelected = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(
        movie = Movie(
            rank = "1",
            id = "1",
            name = "어쩔수가없다",
            salesShareRate = "45.3",
            accumulatedAudience = "833401",
            posterUrl = "https://image.tmdb.org/t/p/w500/pf7vZxoLYtLQ366VNlGrjBxwL7A.jpg"
        )
    )
}
