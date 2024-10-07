package com.example.bookmarker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookmarker.data.Bookmark
import com.example.bookmarker.ui.theme.BookmarkerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkerApp(modifier: Modifier = Modifier) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.factory)

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner)

    Column(
        modifier = Modifier.fillMaxSize().statusBarsPadding()
    ) {

    }
}

@Composable
fun BookmarkList(
    bookmarksList: List<Bookmark>,
    onCardClick: (Bookmark) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding
    ) {
        items(bookmarksList) { bookmark ->
            BookmarkCard(
                bookmark = bookmark,
                onCardClick = onCardClick,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BookmarkCard(
    bookmark: Bookmark,
    onCardClick: (Bookmark) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .clickable { onCardClick(bookmark) }
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = bookmark.pageTitle,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bookmark.pageUrl,
                    overflow = TextOverflow.Ellipsis
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookmark.iconUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                modifier = Modifier.size(60.dp).aspectRatio(1f).padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkPreview() {
    BookmarkerTheme {
        BookmarkList(
            listOf(Bookmark(pageTitle = "Google", pageUrl = "https://www.google.com", iconUrl = "http://hello", date = "2022-04-05"), Bookmark(pageTitle = "Facebook", pageUrl = "https://www.facebook.com", iconUrl = "http://facebook", date = "2023-04-05")), {}, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(horizontal = 8.dp)
        )
    }
}


