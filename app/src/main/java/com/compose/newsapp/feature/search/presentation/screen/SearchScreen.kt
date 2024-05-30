package com.compose.newsapp.feature.search.presentation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.feature.home.presentation.FetchImageFromUrl
import com.compose.newsapp.feature.search.presentation.viewmodel.SearchNewsState
import com.compose.newsapp.feature.search.presentation.viewmodel.SearchViewModel
import com.compose.newsapp.utils.Utils

@Composable
fun SearchScreen(onUpButtonClick: () -> Unit) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val searchNewsState by viewModel.searchNewsState.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Rounded.ArrowBack, contentDescription = "", modifier = Modifier
                .clickable {
                    onUpButtonClick()
                }
                .padding(10.dp))
            Spacer(modifier = Modifier.width(10.dp))

            SearchBar(searchViewModel = viewModel)
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (!searchNewsState.isLoading && searchNewsState.searchData != null) {
            Text(
                text = "About ${searchNewsState.searchData?.totalResults} results found.",
                modifier = Modifier.padding(horizontal = 15.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        LoadSearchResult(searchNewsState)
    }
}

@Composable
fun LoadSearchResult(searchNewsState: SearchNewsState) {

    if (searchNewsState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }


    if (searchNewsState.searchData != null && searchNewsState.searchData.articles.isNotEmpty() && !searchNewsState.isLoading) {
        LazyColumn {
            items(searchNewsState.searchData.articles) { article ->
                DisplayNewsSearched(article)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SearchBar(searchViewModel: SearchViewModel) {
    val textState = rememberTextFieldState()
    BasicTextField2(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 3.dp, shape = RoundedCornerShape(50.dp))
        .padding(horizontal = 0.dp)
        .clip(RoundedCornerShape(50.dp))
        .background(Color.White)
        .padding(15.dp),
        state = textState,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        lineLimits = TextFieldLineLimits.SingleLine, textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground, fontSize = 18.sp
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onSearchClicked(
                searchViewModel,
                textState.text.toString()
            )
        }),
        decorator = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1f)) {
                    if (textState.text.isEmpty()) {
                        Text(
                            text = "Type",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()

                }

                Spacer(modifier = Modifier.width(10.dp))

                if (textState.text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            textState.edit {
                                this.replace(0, textState.text.length, "")
                            }
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }

            }

        })
}

fun onSearchClicked(searchViewModel: SearchViewModel, data: String) {
    searchViewModel.searchNews(data)
}


@Composable
fun DisplayNewsSearched(article: Article) {
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
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
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
