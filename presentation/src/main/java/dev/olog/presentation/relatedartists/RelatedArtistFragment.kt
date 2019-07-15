package dev.olog.presentation.relatedartists

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.olog.core.MediaId
import dev.olog.presentation.R
import dev.olog.presentation.base.BaseFragment
import dev.olog.presentation.navigator.Navigator
import dev.olog.shared.extensions.*
import kotlinx.android.synthetic.main.fragment_related_artist.*
import javax.inject.Inject

class RelatedArtistFragment : BaseFragment() {

    companion object {
        const val TAG = "RelatedArtistFragment"
        const val ARGUMENTS_MEDIA_ID = "$TAG.arguments.media_id"


        fun newInstance(mediaId: MediaId): RelatedArtistFragment {
            return RelatedArtistFragment().withArguments(
                ARGUMENTS_MEDIA_ID to mediaId.toString()
            )
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator
    private val adapter by lazyFast { RelatedArtistFragmentAdapter(lifecycle, navigator) }

    private val viewModel by lazyFast {
        viewModelProvider<RelatedArtistFragmentViewModel>(
            viewModelFactory
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = GridLayoutManager(context!!, 2)
        list.adapter = adapter
        list.setHasFixedSize(true)

        viewModel.data.subscribe(viewLifecycleOwner, adapter::updateDataSet)

        viewModel.itemTitle.subscribe(viewLifecycleOwner) { itemTitle ->
            val headersArray = resources.getStringArray(R.array.related_artists_header)
            val header = String.format(headersArray[viewModel.itemOrdinal], itemTitle)
            this.header.text = header
        }
    }

    override fun onResume() {
        super.onResume()
        back.setOnClickListener { act.onBackPressed() }
    }

    override fun onPause() {
        super.onPause()
        back.setOnClickListener(null)
    }

    override fun provideLayoutId(): Int = R.layout.fragment_related_artist
}