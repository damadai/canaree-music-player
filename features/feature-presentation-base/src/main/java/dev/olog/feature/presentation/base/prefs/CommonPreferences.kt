package dev.olog.feature.presentation.base.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dev.olog.navigation.screens.BottomNavigationPage
import dev.olog.navigation.screens.LibraryPage
import javax.inject.Inject

private const val TAG = "AppPreferencesDataStoreImpl"

private const val BOTTOM_VIEW_LAST_PAGE = "$TAG.BOTTOM_VIEW_3"

private const val LIBRARY_LAST_PAGE = "$TAG.LIBRARY_PAGE"
private const val FIRST_ACCESS = "$TAG.FIRST_ACCESS"

class CommonPreferences @Inject constructor(
    private val context: Context,
    private val preferences: SharedPreferences
) {

    fun isFirstAccess(): Boolean {
        // TODO separate
        val isFirstAccess = preferences.getBoolean(FIRST_ACCESS, true)

        if (isFirstAccess) {
            preferences.edit { putBoolean(FIRST_ACCESS, false) }
        }

        return isFirstAccess
    }

    fun getLastBottomViewPage(): BottomNavigationPage {
        val page = preferences.getString(BOTTOM_VIEW_LAST_PAGE, BottomNavigationPage.LIBRARY.toString())!!
        return BottomNavigationPage.valueOf(page)
    }

    fun setLastBottomViewPage(page: BottomNavigationPage) {
        preferences.edit { putString(BOTTOM_VIEW_LAST_PAGE, page.toString()) }
    }

    fun getLastLibraryPage(): LibraryPage {
        val page = preferences.getString(LIBRARY_LAST_PAGE, LibraryPage.TRACKS.toString())!!
        return LibraryPage.valueOf(page)
    }

    fun setLibraryPage(page: LibraryPage) {
        preferences.edit {
            putString(LIBRARY_LAST_PAGE, page.toString())
        }
    }

}