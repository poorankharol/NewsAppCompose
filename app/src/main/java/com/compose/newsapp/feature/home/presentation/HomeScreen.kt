package com.compose.newsapp.feature.home.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.compose.newsapp.MainActivity
import com.compose.newsapp.R
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.presentation.viewmodel.HomeViewModel
import com.compose.newsapp.utils.Utils

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val newsTopicState by viewModel.newsTopicState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {

        item { TopBar() }
        item {
            Column {
                NewsLabel()
                Spacer(modifier = Modifier.height(15.dp))
                LoadLatestNews(viewModel)
            }
        }

        item {
            Column {
                Spacer(modifier = Modifier.height(15.dp))
                NewsTopics(viewModel)
            }
        }

        if (newsTopicState.isLoading) {
            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (newsTopicState.articles.isNotEmpty() && !newsTopicState.isLoading) {
            items(newsTopicState.articles) { article ->
                DisplayNewsTopic(article)
            }
        }

    }
}

@Composable
private fun NewsLabel() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
    ) {
        Text(
            text = "Latest News",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "View All", style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = "",
            )
        }
    }
}

@Composable
private fun TopBar() {
    var searchText by remember {
        mutableStateOf("")
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        OutlinedTextField(value = searchText, onValueChange = {
            searchText = it
        }, shape = RoundedCornerShape(30.dp), trailingIcon = {
            Icon(imageVector = Icons.Rounded.Search, "", tint = Color.Gray)
        }, placeholder = {
            Text(text = "Search Here")
        }, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
        )
        )
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(30.dp)
                )
                .background(Color.Red)
                .padding(14.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "",
                tint = Color.White
            )
        }

    }
}

@Composable
fun LoadNewsTopic(viewModel: HomeViewModel) {

    val newsTopicState by viewModel.newsTopicState.collectAsState()

    when {
        newsTopicState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        newsTopicState.error != null -> {
            Text(text = newsTopicState.error ?: "An unknown error occurred")
        }

        newsTopicState.articles.isNotEmpty() -> {
            LazyColumn(
                Modifier
                    .fillMaxWidth()

                    .padding(12.dp),
                userScrollEnabled = false
            ) {
                items(newsTopicState.articles) { article ->
                    DisplayNewsTopic(article)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        else -> {
            Text(text = "No articles available")
        }
    }
}

@Composable
fun DisplayNewsTopic(article: Article) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        FetchImageFromUrl(imageUrl = article.urlToImage.toString())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            Text(
                article.title.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    article.source?.name.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    Utils.getFormattedDate(article.publishedAt.toString()),
                    color = Color.White,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun NewsTopics(viewModel: HomeViewModel) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .selectableGroup()
    ) {
        itemsIndexed(MainActivity.topicList) { index, item ->
            Text(
                text = item,
                color = if (selectedIndex == index) Color.White else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .selectable(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            viewModel.getNewsTopic(item)
                        }
                    )
                    .background(
                        if (selectedIndex == index) Color.Red
                        else Color.Transparent
                    )
                    .padding(vertical = 8.dp, horizontal = 20.dp)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadLatestNews(viewModel: HomeViewModel) {

    val latestNewsState by viewModel.latestNewsState.collectAsState()

    when {
        latestNewsState.isLoading -> {
            CircularProgressIndicator()
        }

        latestNewsState.error != null -> {
            Text(text = latestNewsState.error ?: "An unknown error occurred")
        }

        latestNewsState.articles.isNotEmpty() -> {
            val pagerState = rememberPagerState(pageCount = {
                latestNewsState.articles.size
            })
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 20.dp),
                pageSpacing = 10.dp
            ) { page ->
                // Our page content
                DisplayArticle(article = latestNewsState.articles[page])
            }
        }

        else -> {
            Text(text = "No articles available")
        }
    }


}

@Composable
fun DisplayArticle(article: Article) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.Center,
    ) {
        FetchImageFromUrl(imageUrl = article.urlToImage.toString())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                article.title.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                article.description.toString(),
                color = Color.White,
                fontSize = 10.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

}

@Composable
fun FetchImageFromUrl(imageUrl: String) {
    val model = ImageRequest.Builder(LocalContext.current).data(imageUrl).size(250).build()
    val imageState = rememberAsyncImagePainter(model = model).state
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
    ) {
        when (imageState) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = imageState.painter,
                    contentDescription = "",
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "",
                    Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
