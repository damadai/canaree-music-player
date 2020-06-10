package dev.olog.feature.library.home

import androidx.recyclerview.widget.DiffUtil
import dev.olog.feature.presentation.base.adapter.DataBoundViewHolder
import dev.olog.feature.presentation.base.adapter.ObservableAdapterNew
import dev.olog.navigation.Navigator

internal class HomeFragmentNestedAdapter(
    private val navigator: Navigator
) : ObservableAdapterNew<HomeItemModel>(HomeItemModelItemDiff) {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
//        viewHolder.setOnClickListener(this) { item, _, view ->
//            navigator.toDetailFragment(view.findActivity(), item.mediaId.toDomain(), view)
//        }
//        viewHolder.setOnLongClickListener(this) { item, _, _ ->
//            navigator.toDialog(item.mediaId.toDomain(), viewHolder.itemView, viewHolder.itemView)
//        }
//        viewHolder.elevateAlbumOnTouch()
    }

    override fun bind(holder: DataBoundViewHolder, item: HomeItemModel, position: Int) {

//        holder.itemView.apply {
//            transitionName = "tab nested ${item.mediaId}"
//            holder.imageView!!.loadAlbumImage(item.mediaId.toDomain())
//            quickAction.setId(item.mediaId)
//            firstText.text = item.title
//            secondText.text = item.subtitle
//        }
    }

}

private object HomeItemModelItemDiff: DiffUtil.ItemCallback<HomeItemModel>() {

    override fun areItemsTheSame(oldItem: HomeItemModel, newItem: HomeItemModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeItemModel, newItem: HomeItemModel): Boolean {
        return oldItem == newItem
    }
}