package dev.olog.feature.presentation.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import dev.olog.feature.presentation.base.CustomListAdapter
import dev.olog.feature.presentation.base.model.BaseModel
import dev.olog.feature.presentation.base.model.BaseModelNew
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

abstract class ObservableAdapterNew<T : BaseModelNew>(
    itemCallback: DiffUtil.ItemCallback<T>
) : CustomListAdapter<T, DataBoundViewHolder>(itemCallback){

    private val _observeData = ConflatedBroadcastChannel(currentList)
    val observeData: Flow<List<T>> = _observeData.asFlow()

    @CallSuper
    override fun onViewAttachedToWindow(holder: DataBoundViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    @CallSuper
    override fun onViewDetachedFromWindow(holder: DataBoundViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }

    fun indexOf(predicate: (T) -> Boolean): Int {
        return currentList.indexOfFirst(predicate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        val viewHolder =
            DataBoundViewHolder(view)
        initViewHolderListeners(viewHolder, viewType)
        return viewHolder
    }

    protected abstract fun initViewHolderListeners(viewHolder: DataBoundViewHolder, viewType: Int)

    fun lastIndex(): Int = currentList.lastIndex

    override fun getItemViewType(position: Int): Int = getItem(position).type

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        val item = getItem(position)
        bind(holder, item, position)
    }

    override fun onCurrentListChanged(previousList: List<T>, currentList: List<T>) {
        _observeData.offer(currentList)
    }

    protected abstract fun bind(holder: DataBoundViewHolder, item: T, position: Int)

}