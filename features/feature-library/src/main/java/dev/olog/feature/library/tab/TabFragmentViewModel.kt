package dev.olog.feature.library.tab

import androidx.lifecycle.ViewModel
import dev.olog.domain.entity.sort.SortEntity
import dev.olog.domain.prefs.SortPreferences
import dev.olog.domain.schedulers.Schedulers
import dev.olog.feature.library.model.TabCategory
import dev.olog.feature.library.prefs.LibraryPreferences
import dev.olog.feature.presentation.base.model.DisplayableItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class TabFragmentViewModel @Inject constructor(
    private val schedulers: Schedulers,
    private val dataProvider: TabDataProvider,
    private val appPreferencesUseCase: SortPreferences,
    private val preferences: LibraryPreferences

) : ViewModel() {

    fun observeData(category: TabCategory): Flow<List<DisplayableItem>> {
        return dataProvider.get(category)
            .flowOn(schedulers.io)
    }

    fun getAllTracksSortOrder(): SortEntity {
        return appPreferencesUseCase.getAllTracksSort()
    }

    fun getAllAlbumsSortOrder(): SortEntity {
        return appPreferencesUseCase.getAllAlbumsSort()
    }

    fun getSpanCount(category: TabCategory) = preferences.getSpanCount(category)

    fun observeSpanCount(category: TabCategory) = preferences
        .observeSpanCount(category)
        .drop(1) // drop initial value, already used

}