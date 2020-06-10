package dev.olog.feature.library.tracks

import androidx.lifecycle.ViewModel
import dev.olog.domain.entity.sort.SortEntity
import dev.olog.domain.entity.track.Song
import dev.olog.domain.gateway.track.TrackGateway
import dev.olog.domain.prefs.SortPreferences
import dev.olog.domain.schedulers.Schedulers
import dev.olog.feature.presentation.base.model.presentationId
import dev.olog.feature.presentation.base.widget.fastscroller.WaveSideBarView.LETTERS
import dev.olog.shared.TextUtils
import dev.olog.shared.startWithIfNotEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TracksFragmentViewModel @Inject constructor(
    isPodcast: Boolean,
    schedulers: Schedulers,
    trackGateway: TrackGateway,
    private val appPreferencesUseCase: SortPreferences
) : ViewModel() {

    val data: Flow<List<TracksModel>> = if (isPodcast) {
        trackGateway.observeAllPodcasts()
    } else {
        trackGateway.observeAllTracks()
    }.map {
        it.map { it.toTabDisplayableItem() }
            .startWithIfNotEmpty(TracksModel.Shuffle)
    }

    val sidebarLetters: Flow<List<String>> = data.map {
        generateLetters(it)
    }.flowOn(schedulers.cpu)

    private fun Song.toTabDisplayableItem(): TracksModel {
        return TracksModel.Item(
            mediaId = this.presentationId,
            title = this.title,
            artist = this.artist,
            album = this.album,
            duration = this.duration,
            isPodcast = this.isPodcast
        )
    }

    private fun generateLetters(
        data: List<TracksModel>
    ): List<String> {

        val list = data.asSequence()
            .filterIsInstance<TracksModel.Item>()
            .mapNotNull { it.title.firstOrNull()?.toUpperCase() }
            .distinctBy { it }
            .map { it.toString() }
            .toList()

        val letters = LETTERS.map { letter ->
            list.firstOrNull { it == letter } ?: TextUtils.MIDDLE_DOT
        }.toMutableList()
        list.firstOrNull { it < "A" }?.let { letters[0] = "#" }
        list.firstOrNull { it > "Z" }?.let { letters[letters.lastIndex] = "?" }

        return letters
    }

    fun getAllTracksSortOrder(): SortEntity {
        return appPreferencesUseCase.getAllTracksSort()
    }

}