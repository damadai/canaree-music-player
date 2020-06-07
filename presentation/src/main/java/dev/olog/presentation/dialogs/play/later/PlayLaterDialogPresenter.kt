package dev.olog.presentation.dialogs.play.later

import android.support.v4.media.session.MediaControllerCompat
import androidx.core.os.bundleOf
import dev.olog.domain.interactor.songlist.GetSongListByParamUseCase
import dev.olog.domain.schedulers.Schedulers
import dev.olog.core.MusicServiceCustomAction
import dev.olog.feature.presentation.base.model.PresentationId
import dev.olog.feature.presentation.base.model.toDomain
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayLaterDialogPresenter @Inject constructor(
    private val getSongListByParamUseCase: GetSongListByParamUseCase,
    private val schedulers: Schedulers
) {

    suspend fun execute(
        mediaController: MediaControllerCompat,
        mediaId: PresentationId
    ) = withContext(schedulers.io) {
        val items = when (mediaId) {
            is PresentationId.Track -> listOf(mediaId.id.toLong())
            is PresentationId.Category -> getSongListByParamUseCase(mediaId.toDomain()).map { it.id }
        }

        val bundle = bundleOf(
            MusicServiceCustomAction.ARGUMENT_MEDIA_ID_LIST to items.toLongArray()
        )

        mediaController.transportControls.sendCustomAction(
            MusicServiceCustomAction.ADD_TO_PLAY_LATER.name,
            bundle
        )
    }

}