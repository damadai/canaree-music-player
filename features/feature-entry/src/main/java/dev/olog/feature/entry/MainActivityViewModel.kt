package dev.olog.feature.entry

import android.content.Context
import androidx.lifecycle.ViewModel
import dev.olog.feature.presentation.base.prefs.CommonPreferences
import dev.olog.shared.android.Permissions
import javax.inject.Inject

internal class MainActivityViewModel @Inject constructor(
    private val context: Context,
    private val presentationPrefs: CommonPreferences
) : ViewModel() {

    fun isFirstAccess(): Boolean {
        val canReadStorage = Permissions.canReadStorage(context)
        val isFirstAccess = presentationPrefs.isFirstAccess()
        return !canReadStorage || isFirstAccess
    }

}