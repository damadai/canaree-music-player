package dev.olog.feature.library.tracks

import android.content.res.ColorStateList
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import dev.olog.domain.MediaId
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.adapter.*
import dev.olog.feature.presentation.base.loadSongImage
import dev.olog.feature.presentation.base.model.PresentationId
import dev.olog.feature.presentation.base.model.toDomain
import dev.olog.lib.media.MediaProvider
import dev.olog.navigation.Navigator
import dev.olog.shared.android.extensions.colorAccent
import dev.olog.shared.android.extensions.textColorPrimary
import kotlinx.android.synthetic.main.item_podcast.view.*
import kotlinx.android.synthetic.main.item_track.view.*
import kotlinx.android.synthetic.main.item_track.view.firstText
import kotlinx.android.synthetic.main.item_track.view.isPlaying
import kotlinx.android.synthetic.main.item_track.view.secondText

internal class TrackFragmentAdapter(
    private val viewModel: TracksFragmentViewModel,
    private val navigator: Navigator,
    private val mediaProvider: MediaProvider
) : ObservableAdapterNew<TracksModel>(TrackModelItemDiff) {

    var playingMediaId: PresentationId.Track? = null
        private set

    private var podcastPositions = emptyMap<Long, Int>()

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
        when (viewType) {
            R.layout.item_tab_shuffle -> {
                viewHolder.setOnClickListener(this) { _, _, _ ->
                    mediaProvider.shuffle(MediaId.SHUFFLE_ID, null)
                }
            }
            R.layout.item_track -> {
                viewHolder.setOnClickListener(this) { item, _, _ ->
                    require(item is TracksModel.Item)
                    val sort = viewModel.getAllTracksSortOrder()
                    mediaProvider.playFromMediaId(item.mediaId.toDomain(), null, sort)
                }
                viewHolder.setOnLongClickListener(this) { item, _, _ ->
                    require(item is TracksModel.Item)
                    navigator.toDialog(item.mediaId.toDomain(), viewHolder.itemView, viewHolder.itemView)
                }
                viewHolder.elevateSongOnTouch()
            }
            R.layout.item_podcast -> {
                viewHolder.setOnClickListener(this) { item, _, _ ->
                    require(item is TracksModel.Item)
                    mediaProvider.playFromMediaId(item.mediaId.toDomain(), null, null)
                }
                viewHolder.setOnLongClickListener(this) { item, _, _ ->
                    require(item is TracksModel.Item)
                    navigator.toDialog(item.mediaId.toDomain(), viewHolder.itemView, viewHolder.itemView)
                }
                viewHolder.elevateSongOnTouch()
            }
        }
    }

    override fun bind(holder: DataBoundViewHolder, item: TracksModel, position: Int) {
        if (item is TracksModel.Item) {
            val view = holder.itemView
            holder.imageView!!.loadSongImage(item.mediaId.toDomain())
            view.firstText.text = item.title
            view.secondText.text = item.subtitle
            view.explicit?.onItemChanged(item.title)
            view.isPlaying.toggleVisibility(item.mediaId == playingMediaId)

            if (item.isPodcast) {
                bindPodcastProgress(view, item)
            }
        }
    }

    private fun bindPodcastProgress(view: View, item: TracksModel.Item) {
        val duration = item.duration.toInt()
        val progress = podcastPositions[item.mediaId.id.toLong()] ?: 0
        view.progressBar.max = duration
        view.progressBar.progress = progress

        val percentage = (progress.toFloat() / duration.toFloat() * 100f).toInt()
        view.percentage.text = "$percentage%"

        val color = if (item.mediaId == playingMediaId) {
            view.context.colorAccent()
        } else {
            view.context.textColorPrimary()
        }
        view.progressBar.progressTintList = ColorStateList.valueOf(color)
    }

    override fun onBindViewHolder(
        holder: DataBoundViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        val payload = payloads.filterIsInstance<Boolean>().firstOrNull()
        if (payload != null) {
            holder.itemView.isPlaying.animateVisibility(payload)
            if (item is TracksModel.Item && item.isPodcast) {
                bindPodcastProgress(holder.itemView, item)
            }
        }

        val updatePodcastPosition = payloads.filterIsInstance<Unit>().firstOrNull()
        if (updatePodcastPosition != null && item is TracksModel.Item && item.isPodcast) {
            bindPodcastProgress(holder.itemView, item)
        }

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun updatePodcastPositions(positions: Map<Long, Int>) {
        this.podcastPositions = positions
        for (index in currentList.indices) {
            notifyItemChanged(index, Unit)
        }
    }

    fun onCurrentPlayingChanged(item: PresentationId.Track) {
        val filter: (TracksModel) -> Boolean = {
            when (it) {
                is TracksModel.Item -> it.mediaId == playingMediaId
                is TracksModel.Shuffle -> false
            }
        }
        val before = indexOf(filter)
        val after = indexOf(filter)
        this.playingMediaId = item
        notifyItemChanged(before, false)
        notifyItemChanged(after, true)
    }

}

private object TrackModelItemDiff : DiffUtil.ItemCallback<TracksModel>() {

    override fun areItemsTheSame(oldItem: TracksModel, newItem: TracksModel): Boolean {
        if (oldItem is TracksModel.Item && newItem is TracksModel.Item) {
            return oldItem.mediaId == newItem.mediaId
        }
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TracksModel, newItem: TracksModel): Boolean {
        return oldItem == newItem
    }
}