package com.example.bookmarker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
        Row {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkPreview() {
    BookmarkerTheme {
        BookmarkCard(Bookmark(pageTitle = "Google", pageUrl = "https://www.google.com", iconUrl = "http://hello", date = ""), {})
    }
}


