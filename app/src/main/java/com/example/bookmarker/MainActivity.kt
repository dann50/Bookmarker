package com.example.bookmarker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookmarker.ui.theme.BookmarkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: BookmarkViewModel = viewModel(factory = BookmarkViewModel.factory)
            saveBookmark(intent, viewModel::saveBookmark)
            BookmarkerTheme {
                BookmarkerApp(viewModel)
            }
        }
    }

    private fun saveBookmark(intent: Intent, save: (String) -> Unit) {
        if (Intent.ACTION_SEND == intent.action) {
            val pageUrl = getIntent().getStringExtra(Intent.EXTRA_STREAM)
                ?: getIntent().getStringExtra(Intent.EXTRA_TEXT)

            if (pageUrl != null && Uri.parse(pageUrl).scheme!!.startsWith("http")) {
                save(pageUrl)
            } else {
                Toast.makeText(this, R.string.msg_invalid_url, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookmarkerTheme {

    }
}