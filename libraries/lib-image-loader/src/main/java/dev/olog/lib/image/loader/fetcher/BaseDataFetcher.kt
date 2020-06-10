package dev.olog.lib.image.loader.fetcher

import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.data.HttpUrlFetcher
import com.bumptech.glide.load.model.GlideUrl
import dev.olog.shared.coroutines.DispatcherScope
import dev.olog.domain.schedulers.Schedulers
import dev.olog.lib.image.loader.R
import dev.olog.shared.android.utils.NetworkUtils
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.InputStream
import java.util.concurrent.atomic.AtomicLong

/**
 * Class that uses a 'global' counter to delay the image request for an image.
 * Because LastFm allows 5 request per second for user
 */
// TODO check is only one fetcher is used or multiple
abstract class BaseDataFetcher(
    private val context: Context,
    private val prefs: SharedPreferences,
    schedulers: Schedulers
) : DataFetcher<InputStream> {

    companion object {
        private const val TIMEOUT = 5000
        @JvmStatic
        private var requestCounter = AtomicLong(1)
    }

    private val scope by DispatcherScope(schedulers.io)

    private var hasIncremented = false
    private var hasDecremented = false

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun getDataSource(): DataSource = DataSource.REMOTE

    override fun cleanup() {

    }

    override fun cancel() {
        scope.cancel()
        if (hasIncremented && !hasDecremented) {
            requestCounter.decrementAndGet()
        }
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        scope.launch {
            try {
                if (!mustFetch() && tryLocal(priority, callback)) {
                    return@launch
                }
                if (tryRemote(priority, callback)) {
                    return@launch
                }

                throw NoSuchElementException()
            } catch (ex: NoNetworkAllowedException) {
                // don't log
                callback.onLoadFailed(RuntimeException(ex))
            } catch (ex: Exception) {
                callback.onLoadFailed(RuntimeException(ex))
            }
        }
    }

    private suspend fun tryLocal(
        priority: Priority,
        callback: DataFetcher.DataCallback<in InputStream>
    ): Boolean {
        if (networkSafeAction()) {
            // load local
            val image = execute()
            yield()
            loadUrl(image, priority, callback)
            return true
        } else {
            throw NoNetworkAllowedException()
        }
    }

    private suspend fun tryRemote(
        priority: Priority,
        callback: DataFetcher.DataCallback<in InputStream>
    ): Boolean {
        if (!networkSafeAction()) {
            throw NoNetworkAllowedException()
        }
        // delay
        delayRequest()
        yield()

        // rest call to last fm
        val image = execute()
        yield()

        if (image.isNotBlank()) {
            loadUrl(image, priority, callback)
            return true
        }
        return false
    }

    private fun loadUrl(
        url: String,
        priority: Priority,
        callback: DataFetcher.DataCallback<in InputStream>
    ) {
        val urlFetcher = HttpUrlFetcher(
            GlideUrl(url),
            TIMEOUT
        )
        urlFetcher.loadData(priority, callback)
    }

    private suspend fun delayRequest() {
        val current = requestCounter.incrementAndGet()
        hasIncremented = true
        delay(current * threshold)
        requestCounter.decrementAndGet()
        hasDecremented = true
    }

    private fun networkSafeAction(): Boolean {

        val downloadMode = prefs.getString(
            context.getString(R.string.prefs_auto_download_images_key),
            context.getString(R.string.prefs_auto_download_images_entry_value_wifi)
        )

        val isWifiActive = NetworkUtils.isOnWiFi(context)

        return when (downloadMode) {
            context.getString(R.string.prefs_auto_download_images_entry_value_never) -> false
            context.getString(R.string.prefs_auto_download_images_entry_value_wifi) -> isWifiActive
            context.getString(R.string.prefs_auto_download_images_entry_value_always) -> true
            else -> throw IllegalArgumentException("not supposed to happen, key not valid=$downloadMode")
        }
    }

    internal abstract suspend fun execute(): String

    internal abstract suspend fun mustFetch(): Boolean

    protected abstract val threshold: Long

}