package dev.brunofelix.movies.data.local.source

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Reads the version of the installed app from the [PackageManager], since library modules
 * don't see the app module's `BuildConfig`.
 */
class AppInfoLocalDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AppInfoLocalDataSource {

    override fun getVersionName(): String {
        val packageManager = context.packageManager
        val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(context.packageName, 0)
        }
        return packageInfo.versionName.orEmpty()
    }
}
