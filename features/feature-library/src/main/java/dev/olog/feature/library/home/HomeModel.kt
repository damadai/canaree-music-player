package dev.olog.feature.library.home

import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.model.BaseModelNew
import dev.olog.feature.presentation.base.model.PresentationId

sealed class HomeModel(
    override val type: Int
) : BaseModelNew {

    data class HorizontalList(
        override val type: Int
    ) : HomeModel(type)

    data class Header(
        val title: String
    ) : HomeModel(R.layout.item_home_header)

}

sealed class HomeItemModel(
    override val type: Int
) : BaseModelNew {

    data class Album(
        val mediaId: PresentationId.Category,
        val title: String,
        val artist: String
    ) : HomeItemModel(R.layout.item_last_played)

    data class Artist(
        val mediaId: PresentationId.Category,
        val name: String
    ) : HomeItemModel(R.layout.item_last_played)

}