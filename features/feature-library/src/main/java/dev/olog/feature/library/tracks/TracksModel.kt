package dev.olog.feature.library.tracks

import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.model.BaseModelNew
import dev.olog.feature.presentation.base.model.PresentationId
import dev.olog.shared.TextUtils

sealed class TracksModel(
    override val type: Int
) : BaseModelNew {

    data class Item(
        val mediaId: PresentationId.Track,
        val title: String,
        val artist: String,
        val album: String,
        val duration: Long,
        val isPodcast: Boolean
    ) : TracksModel(if (isPodcast) R.layout.item_podcast else R.layout.item_track) {

        val subtitle = "$artist${TextUtils.MIDDLE_DOT_SPACED}$album"

    }

    object Shuffle : TracksModel(R.layout.item_tab_shuffle)

}