package dev.olog.presentation.about

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.olog.core.MediaId
import dev.olog.presentation.BuildConfig
import dev.olog.presentation.R
import dev.olog.presentation.model.DisplayableHeader
import dev.olog.presentation.model.DisplayableItem
import dev.olog.presentation.pro.BillingState
import dev.olog.presentation.pro.IBilling
import dev.olog.shared.extensions.asLiveData
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class AboutActivityPresenter(
    context: Context,
    private val billing: IBilling
) {

    companion object {
        val AUTHOR_ID = MediaId.headerId("author id")
        val THIRD_SW_ID = MediaId.headerId("third sw")
        val COMMUNITY = MediaId.headerId("community")
        val BETA = MediaId.headerId("beta")
        val SPECIAL_THANKS_ID = MediaId.headerId("special thanks to")
        val RATE_ID = MediaId.headerId("rate")
        val PRIVACY_POLICY = MediaId.headerId("privacy policy")
        val BUY_PRO = MediaId.headerId("pro")
    }


    private val data = listOf(
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = AUTHOR_ID,
            title = context.getString(R.string.about_author),
            subtitle = "Eugeniu Olog"
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = MediaId.headerId("version id"),
            title = context.getString(R.string.about_version),
            subtitle = BuildConfig.VERSION_NAME
        ),

        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = COMMUNITY,
            title = context.getString(R.string.about_join_community),
            subtitle = context.getString(R.string.about_join_community_description)
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = BETA,
            title = context.getString(R.string.about_beta),
            subtitle = context.getString(R.string.about_beta_description)
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = RATE_ID,
            title = context.getString(R.string.about_support_rate),
            subtitle = context.getString(R.string.about_support_rate_description)
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = PRIVACY_POLICY,
            title = context.getString(R.string.about_privacy_policy),
            subtitle = context.getString(R.string.about_privacy_policy_description)
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = THIRD_SW_ID,
            title = context.getString(R.string.about_third_sw),
            subtitle = context.getString(R.string.about_third_sw_description)
        ),
        DisplayableHeader(
            type = R.layout.item_about,
            mediaId = SPECIAL_THANKS_ID,
            title = context.getString(R.string.about_special_thanks_to),
            subtitle = context.getString(R.string.about_special_thanks_to_description)
        )
    )

    private val trial = DisplayableHeader(
        type = R.layout.item_about,
        mediaId = BUY_PRO,
        title = context.getString(R.string.about_buy_pro),
        subtitle = context.getString(R.string.about_buy_pro_description_trial)
    )
    private val noPro = DisplayableHeader(
        type = R.layout.item_about,
        mediaId = BUY_PRO,
        title = context.getString(R.string.about_buy_pro),
        subtitle = context.getString(R.string.about_buy_pro_description)
    )
    private val alreadyPro = DisplayableHeader(
        type = R.layout.item_about,
        mediaId = BUY_PRO,
        title = context.getString(R.string.about_buy_pro),
        subtitle = context.getString(R.string.premium_already_premium)
    )

    private val dataLiveData = MutableLiveData<List<DisplayableItem>>()

    init {
        dataLiveData.postValue(data)
    }

    fun observeData(): LiveData<List<DisplayableItem>> {
        return billing.observeBillingsState().withLatestFrom(Observable.just(data),
            BiFunction { state: BillingState, data: List<DisplayableHeader> ->
                when {
                    state.isBought -> listOf(alreadyPro).plus(data)
                    state.isTrial -> listOf(trial).plus(data)
                    else -> listOf(noPro).plus(data)
                }
            }).map { it.map { it as DisplayableItem } } // stupid compiler
            .asLiveData()
    }

    fun buyPro() {
        if (!billing.getBillingsState().isPremiumStrict()) {
            billing.purchasePremium()
        } else {
            // TODO show toast with already purchased
        }
    }

}