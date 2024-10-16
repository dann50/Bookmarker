package com.example.bookmarker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
fun BookmarkerApp(viewModel: BookmarkViewModel = viewModel()) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle(lifecycleOwner)
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.statusBarsPadding().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Box {
                TopSearchBar(
                    updateUiStateSearch = viewModel::updateUiStateSearch,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(
                    imageVector =  Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            BookmarkList(
                bookmarksList = uiState.value,
                onCardClick = {},
                modifier = Modifier,
                contentPadding = it
            )
        }
    }

    if (showDialog) {
        NewLinkDialog(
            addLink = { s ->
                viewModel.saveBookmark(if (s.startsWith("https")) s else "https://${s}")
                showDialog = false
            },
            dismiss = { showDialog = false }
        )
    }
}

@Composable
fun TopSearchBar(
    updateUiStateSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var search by remember { mutableStateOf("") }
    TextField(
        value = search,
        onValueChange = {
            search = it
            updateUiStateSearch(it)
        },
        placeholder = { Text(text = stringResource(R.string.search_bar_placeholder))},
        leadingIcon = { Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null
        )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(50.dp),
        modifier = modifier
    )
}

@Composable
private fun NewLinkDialog(
    addLink: (String) -> Unit,
    dismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
        },
        title = { Text(text = stringResource(R.string.new_link_placeholder)) },
        text = {
            TextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text(text = stringResource(R.string.new_link_placeholder)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { addLink(content) })
            )
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = { dismiss() }
            ) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                addLink(content)
            }) {
                Text(text = stringResource(R.string.ok_button))
            }
        }
    )
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
        modifier = modifier.fillMaxSize().padding(horizontal = 8.dp),
        contentPadding = contentPadding
    ) {
        items(items = bookmarksList, key = { it.id }) { bookmark ->
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
        Row(modifier = Modifier.padding(8.dp)) {
            Column(modifier = Modifier.weight(1f)) {
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


