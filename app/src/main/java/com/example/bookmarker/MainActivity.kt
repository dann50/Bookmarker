package com.example.bookmarker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bookmarker.ui.theme.BookmarkerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookmarkerTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookmarkerApp()
                saveBookmark(intent)
                //}
            }
        }
    }

    private fun saveBookmark(intent: Intent) {
        if (Intent.ACTION_SEND == intent.action) {
            val pageUrl = getIntent().getStringExtra(Intent.EXTRA_STREAM)
                ?: getIntent().getStringExtra(Intent.EXTRA_TEXT)

            if (pageUrl != null && Uri.parse(pageUrl).scheme!!.startsWith("http")) {
                //motor.save(pageUrl)
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