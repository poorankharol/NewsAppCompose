package com.compose.newsapp.feature.details.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.compose.newsapp.feature.home.domain.model.Article
import com.compose.newsapp.utils.Utils


@Composable
fun DetailsScreen(article: Article?, onUpButtonClick: () -> Unit) {
    NewsDetails(article = article, onUpButtonClick)
}

@Composable
fun NewsDetails(article: Article?, onUpButtonClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            model = article?.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            val (backBtn, topSpace, summary, newContent) = createRefs()
            Spacer(modifier = Modifier
                .height(350.dp)
                .constrainAs(topSpace) {
                    top.linkTo(parent.top)
                })
            Image(imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(2.dp)
                    .clickable {
                        onUpButtonClick()
                    }
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    })
            Box(modifier = Modifier
                .constrainAs(newContent) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.wrapContent
                }
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(vertical = 80.dp, horizontal = 16.dp)) {
                Text(
                    text = article?.description.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(16.dp)
                .constrainAs(summary) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topSpace.bottom)
                }) {
                Text(
                    text = Utils.getFormattedDate(article?.publishedAt.toString()),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article?.title.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = article?.source?.name.toString(), fontSize = 10.sp)
            }
        }
    }
}