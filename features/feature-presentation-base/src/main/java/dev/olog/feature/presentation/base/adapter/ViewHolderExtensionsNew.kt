package dev.olog.feature.presentation.base.adapter

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import dev.olog.feature.presentation.base.CustomListAdapter
import dev.olog.feature.presentation.base.model.BaseModelNew

fun <T : BaseModelNew> RecyclerView.ViewHolder.setOnClickListener(
    @IdRes resId: Int,
    data: CustomListAdapter<T, *>,
    func: (item: T, position: Int, view: View) -> Unit
) {

    this.itemView.findViewById<View>(resId)?.setOnClickListener {
        if (adapterPosition != RecyclerView.NO_POSITION) {
            func(data.getItem(adapterPosition), adapterPosition, it)
        }
    }
}

fun <T : BaseModelNew> RecyclerView.ViewHolder.setOnLongClickListener(
    data: CustomListAdapter<T, *>,
    func: (item: T, position: Int, view: View) -> Unit
) {

    itemView.setOnLongClickListener inner@{
        if (adapterPosition != RecyclerView.NO_POSITION) {
            func(data.getItem(adapterPosition), adapterPosition, it)
            return@inner true
        }
        false
    }
}