package dev.olog.presentation.relatedartists

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import dev.olog.presentation.BR
import dev.olog.presentation.base.adapter.*
import dev.olog.presentation.model.DisplayableItem
import dev.olog.presentation.navigator.Navigator

class RelatedArtistFragmentAdapter(
    lifecycle: Lifecycle,
    private val navigator: Navigator

) : ObservableAdapter<DisplayableItem>(
    lifecycle,
    DiffCallbackDisplayableItem
) {


    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
        viewHolder.setOnClickListener(this) { item, _, _ ->
            navigator.toDetailFragment(item.mediaId)
        }
        viewHolder.setOnLongClickListener(this) { item, _, _ ->
            navigator.toDialog(item.mediaId, viewHolder.itemView)
        }
        viewHolder.elevateAlbumOnTouch()
    }

    override fun bind(binding: ViewDataBinding, item: DisplayableItem, position: Int) {
        binding.setVariable(BR.item, item)
    }


}