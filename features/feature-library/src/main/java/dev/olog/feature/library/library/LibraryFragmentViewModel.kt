package dev.olog.feature.library.library

import androidx.lifecycle.ViewModel
import dev.olog.feature.library.prefs.LibraryPreferences
import dev.olog.navigation.screens.LibraryPage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class LibraryFragmentViewModel @Inject constructor(
    preferences: LibraryPreferences
): ViewModel() {

    val currentLibraryPage: Flow<LibraryPage> = preferences.observeCurrentLibraryPage()

}