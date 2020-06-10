package dev.olog.feature.library.tab.adapter

import android.view.View
import androidx.core.view.isVisible
import dev.olog.core.extensions.findActivity
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.adapter.*
import dev.olog.feature.presentation.base.loadAlbumImage
import dev.olog.feature.presentation.base.model.*
import dev.olog.navigation.Navigator
import dev.olog.shared.exhaustive
import kotlinx.android.synthetic.main.item_tab_album.view.*
import kotlinx.android.synthetic.main.item_tab_album.view.firstText
import kotlinx.android.synthetic.main.item_tab_album.view.secondText
import kotlinx.android.synthetic.main.item_tab_header.view.*
import kotlinx.android.synthetic.main.item_tab_song.view.*

internal class TabFragmentAdapter(
    private val navigator: Navigator

) : ObservableAdapter<DisplayableItem>(DiffCallbackDisplayableItem) {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
        when (viewType) {
            R.layout.item_tab_album,
            R.layout.item_tab_podcast_playlist,
            R.layout.item_tab_artist,
            R.layout.item_tab_auto_playlist,
            R.layout.item_tab_podcast_auto_playlist-> {
                viewHolder.setOnClickListener(this) { item, _, view ->
                    onItemClick(view, item)
                }
                viewHolder.setOnLongClickListener(this) { item, _, _ ->
                    navigator.toDialog(item.mediaId.toDomain(), viewHolder.itemView, viewHolder.itemView)
                }
                viewHolder.elevateAlbumOnTouch()
            }

        }
    }

    private fun onItemClick(view: View, item: DisplayableItem){
        if (item is DisplayableAlbum){
            navigator.toDetailFragment(view.findActivity(), item.mediaId.toDomain(), view)
        }
    }

    override fun bind(holder: DataBoundViewHolder, item: DisplayableItem, position: Int) {
        holder.itemView.transitionName = "tab ${item.mediaId}"

        when (item) {
            is DisplayableTrack -> {
            }
            is DisplayableAlbum -> bindAlbum(holder, item)
            is DisplayableHeader -> bindHeader(holder, item)
            is DisplayableNestedListPlaceholder -> {
            }
        }.exhaustive
    }

    private fun bindAlbum(holder: DataBoundViewHolder, item: DisplayableAlbum){
        holder.itemView.apply {
            holder.imageView!!.loadAlbumImage(item.mediaId.toDomain())
            quickAction?.setId(item.mediaId)
            firstText.text = item.title
            secondText?.text = item.subtitle
            explicit?.isVisible = false
        }
    }

    private fun bindHeader(holder: DataBoundViewHolder, item: DisplayableHeader) {
        if (holder.itemViewType == R.layout.item_tab_header) {
            holder.itemView.title.text = item.title
        }
    }

}
