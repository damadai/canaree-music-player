package dev.olog.feature.library.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dev.olog.feature.library.R
import dev.olog.feature.library.SpanCountController
import dev.olog.feature.library.model.TabCategory
import dev.olog.navigation.screens.LibraryPage
import dev.olog.shared.android.extensions.observeKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "AppPreferencesDataStoreImpl"

private const val LIBRARY_PAGE = "$TAG.LIBRARY_LAST_PAGE"

internal class LibraryPreferences @Inject constructor(
    private val context: Context,
    private val preferences: SharedPreferences
) {

    fun observeCurrentLibraryPage(): Flow<LibraryPage> {
        return preferences.observeKey(LIBRARY_PAGE, LibraryPage.LIBRARY_TRACKS.toString())
            .map { LibraryPage.valueOf(it) }
    }

    fun observeLibraryNewVisibility(): Flow<Boolean> {
        return preferences.observeKey(
            context.getString(R.string.prefs_show_new_albums_artists_key),
            true
        )
    }

    fun observeLibraryRecentPlayedVisibility(): Flow<Boolean> {
        return preferences.observeKey(
            (context.getString(R.string.prefs_show_recent_albums_artists_key)),
            true
        )
    }

    fun getSpanCount(category: TabCategory): Int {
        return preferences.getInt("${category}_span", SpanCountController.getDefaultSpan(category))
    }

    fun observeSpanCount(category: TabCategory): Flow<Int> {
        return preferences.observeKey("${category}_span", SpanCountController.getDefaultSpan(category))
    }

    fun setSpanCount(category: TabCategory, spanCount: Int) {
        preferences.edit {
            putInt("${category}_span", spanCount)
        }
    }
}