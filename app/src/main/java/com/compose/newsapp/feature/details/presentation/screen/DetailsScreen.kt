package com.compose.newsapp.feature.details.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.utils.Utils


@Composable
fun DetailsScreen(article: Article?) {
    NewsDetails(article = article)
}

@Composable
fun NewsDetails(article: Article?) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = article?.urlToImage,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.FillWidth,
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val (backBtn, topSpace, summary, newsContent) = createRefs()

            Spacer(modifier = Modifier
                .height(350.dp)
                .constrainAs(topSpace) {
                    top.linkTo(parent.top)
                }
            )
            Image(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)

                    }
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .background(Color.Gray)
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
                    .constrainAs(newsContent) {
                        top.linkTo(topSpace.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(topSpace.bottom)
                    }
            ) {
                Text(text = Utils.getFormattedDate(article?.publishedAt.toString()))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article?.title.toString())
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Published by " + article?.source?.name.toString())
            }

            Box(modifier = Modifier
                .constrainAs(newsContent) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .padding(top = 16.dp)
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .padding(16.dp)) {
                Text(text = article?.content.toString(), modifier = Modifier.fillMaxSize())
            }
        }
    }
}