package dev.olog.feature.library.home

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.olog.feature.library.R
import dev.olog.feature.presentation.base.SetupNestedList
import dev.olog.feature.presentation.base.adapter.DataBoundViewHolder
import dev.olog.feature.presentation.base.adapter.ObservableAdapterNew

internal class HomeFragmentAdapter(
    private val setupNestedList: SetupNestedList
): ObservableAdapterNew<HomeModel>(HomeModelItemDiff) {

    override fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int) {
        when (viewType) {
            R.layout.item_tab_last_played_album_horizontal_list,
            R.layout.item_tab_last_played_artist_horizontal_list,
            R.layout.item_tab_new_album_horizontal_list,
            R.layout.item_tab_new_artist_horizontal_list -> {
                val view = viewHolder.itemView as RecyclerView
                setupNestedList.setupNestedList(viewType, view)
            }
        }
    }

    override fun bind(holder: DataBoundViewHolder, item: HomeModel, position: Int) {
        if (item is HomeModel.Header) {
            // TODO bind text
        }
    }
}

private object HomeModelItemDiff: DiffUtil.ItemCallback<HomeModel>() {

    override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
        return oldItem == newItem
    }
}