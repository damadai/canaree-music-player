package dev.olog.presentation.pro

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.android.billingclient.api.*
import dev.olog.core.interactor.ResetPreferencesUseCase
import dev.olog.core.prefs.AppPreferencesGateway
import dev.olog.presentation.BuildConfig
import dev.olog.presentation.model.PresentationPreferencesGateway
import dev.olog.shared.flowInterval
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.properties.Delegates

class BillingImpl @Inject constructor(
    activity: AppCompatActivity,
    private val resetPreferencesUseCase: ResetPreferencesUseCase,
    private val presentationPreferences: PresentationPreferencesGateway,
    private val prefsGateway: AppPreferencesGateway

) : BillingConnection(activity), IBilling, CoroutineScope by MainScope() {

    companion object {
        private const val PRO_VERSION_ID = "pro_version"
        @JvmStatic
        private val DEFAULT_PREMIUM = false
        private const val DEFAULT_TRIAL = false
        private const val DEFAULT_SHOW_AD = false
        @JvmStatic
        private val TRIAL_TIME = TimeUnit.HOURS.toMillis(1L)
    }

    private val premiumPublisher = ConflatedBroadcastChannel(DEFAULT_PREMIUM)
    private val trialPublisher = ConflatedBroadcastChannel(DEFAULT_TRIAL)
    private val showAdPublisher = ConflatedBroadcastChannel(DEFAULT_SHOW_AD)

    private var isTrialState by Delegates.observable(DEFAULT_TRIAL) { _, _, new ->
        trialPublisher.offer(new)
        if (!getBillingsState().isPremiumEnabled()) {
            setDefault()
        }
    }

    private var isPremiumState by Delegates.observable(DEFAULT_PREMIUM) { _, _, new ->
        premiumPublisher.offer(new)
        if (!getBillingsState().isPremiumEnabled()) {
            setDefault()
        }
    }

    private var isShowAdState by Delegates.observable(DEFAULT_SHOW_AD) { _, _, new ->
        showAdPublisher.offer(new)
        if (!getBillingsState().isPremiumEnabled()) {
            setDefault()
        }
    }

    init {
        doOnConnected { checkPurchases() }

        if (isStillTrial()) {
            isTrialState = true
            launch(Dispatchers.IO) {
                flowInterval(5, TimeUnit.MINUTES)
                    .map { isStillTrial() }
                    .onEach { isTrialState = it }
                    .takeWhile { it }
                    .collect { }
            }
        }
        launch {
            prefsGateway.observeCanShowAds()
                .flowOn(Dispatchers.IO)
                .collect {
                    isShowAdState = it
                    showAdPublisher.offer(it)
                }
        }
    }

    private fun isStillTrial(): Boolean {
        val packageInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
        val firstInstallTime = packageInfo.firstInstallTime
        return System.currentTimeMillis() - firstInstallTime < TRIAL_TIME
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel()
    }

    private fun checkPurchases() {
        val purchases = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
        if (purchases.responseCode == BillingClient.BillingResponseCode.OK) {
            isPremiumState = isProBought(purchases.purchasesList)
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                isPremiumState = isProBought(purchases)
            }
            // TODO add missing
            else -> Log.w("Billing", "billing response code=${billingResult.responseCode}, " +
                    "error=${billingResult.debugMessage}")
        }
    }

    private fun isProBought(purchases: MutableList<Purchase>?): Boolean {
        return purchases?.firstOrNull { it.sku == PRO_VERSION_ID } != null || DEFAULT_PREMIUM
//        return true
    }

    override fun observeBillingsState(): Flow<BillingState> {
        return premiumPublisher.asFlow().combineLatest(trialPublisher.asFlow(), showAdPublisher.asFlow())
        { premium, trial, showAds ->
            BillingState(trial, premium, showAds)
        }.distinctUntilChanged()
    }

    override fun getBillingsState(): BillingState {
        return BillingState(
            isBought = premiumPublisher.value,
            isTrial = trialPublisher.value,
            canShowAd = showAdPublisher.value
        )
    }

    override fun purchasePremium() {
        doOnConnected {
            val params = SkuDetailsParams.newBuilder()
            params.setSkusList(listOf(PRO_VERSION_ID))
                .setType(BillingClient.SkuType.INAPP)

            billingClient.querySkuDetailsAsync(params.build()) { result, skuDetailsList ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK && skuDetailsList?.isNotEmpty() == true) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList[0])
                        .build()
                    billingClient.launchBillingFlow(activity, flowParams)
                }
            }
        }
    }

    private fun setDefault() = launch(Dispatchers.Default) {
        resetPreferencesUseCase.execute()
        presentationPreferences.setDefault()
    }
}