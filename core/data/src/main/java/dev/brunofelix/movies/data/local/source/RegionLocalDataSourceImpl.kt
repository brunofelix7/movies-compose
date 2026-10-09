package dev.brunofelix.movies.data.local.source

import android.content.Context
import android.content.res.Resources
import android.telephony.TelephonyManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

private const val REGION_CODE_LENGTH = 2
private const val DEFAULT_REGION = "US"

/**
 * Reads the region from the SIM card, then the mobile network, then the system locale.
 *
 * The app language can't be used: it is picked in Settings and only has three options, while
 * the streaming catalog depends on where the user lives. The system locale is read from
 * [Resources.getSystem] because the per-app locale replaces the default [Locale].
 */
class RegionLocalDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : RegionLocalDataSource {

    override fun getRegion(): String {
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        return sequenceOf(
            { telephony?.simCountryIso },
            { telephony?.networkCountryIso },
            { Resources.getSystem().configuration.locales[0]?.country }
        )
            .mapNotNull { it() }
            .firstOrNull { it.length == REGION_CODE_LENGTH }
            ?.uppercase(Locale.ROOT)
            ?: DEFAULT_REGION
    }
}
