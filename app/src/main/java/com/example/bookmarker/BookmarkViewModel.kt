package com.example.bookmarker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookmarker.data.Bookmark
import com.example.bookmarker.data.BookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarkViewModel (private val bookmarkRepository: BookmarkRepository) : ViewModel() {

    private val _uiState by lazy { MutableStateFlow(emptyList<Bookmark>()) }
    val uiState: StateFlow<List<Bookmark>> = _uiState

    init {
        viewModelScope.launch {
            getBookmarks()
        }
    }

    private suspend fun getBookmarks() {
        bookmarkRepository.getBookmarks().collect { bookmarks ->
            _uiState.value = bookmarks
        }
    }

    suspend fun saveBookmark(bookmark: Bookmark) {
        bookmarkRepository.insert(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkRepository.delete(bookmark)
    }

    fun updateUiStateSearch(s: String) {
        if (s.isNotBlank()) {
            viewModelScope.launch {
                bookmarkRepository.searchBookmarks(s).collect { bookmarks ->
                    _uiState.update { bookmarks }
                }
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application: BookmarkerApplication = this[APPLICATION_KEY] as BookmarkerApplication
                BookmarkViewModel(application.bookmarkRepository)
            }
        }
    }
}