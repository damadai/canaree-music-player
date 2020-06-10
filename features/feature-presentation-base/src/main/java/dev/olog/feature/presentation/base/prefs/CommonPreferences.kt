package dev.olog.feature.presentation.base.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import dev.olog.navigation.screens.BottomNavigationPage
import javax.inject.Inject

private const val TAG = "AppPreferencesDataStoreImpl"

private const val BOTTOM_VIEW_LAST_PAGE = "$TAG.BOTTOM_VIEW_3"

private const val FIRST_ACCESS = "$TAG.FIRST_ACCESS"

class CommonPreferences @Inject constructor(
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

}