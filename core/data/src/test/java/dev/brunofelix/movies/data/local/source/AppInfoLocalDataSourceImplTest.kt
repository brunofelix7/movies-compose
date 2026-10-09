package dev.brunofelix.movies.data.local.source

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class AppInfoLocalDataSourceImplTest : DescribeSpec({

    fun dataSource(versionName: String?): AppInfoLocalDataSourceImpl {
        val packageInfo = mockk<PackageInfo>().apply { this.versionName = versionName }
        val packageManager = mockk<PackageManager> {
            @Suppress("DEPRECATION")
            every { getPackageInfo("dev.brunofelix.movies", 0) } returns packageInfo
        }
        val context = mockk<Context> {
            every { this@mockk.packageManager } returns packageManager
            every { packageName } returns "dev.brunofelix.movies"
        }
        return AppInfoLocalDataSourceImpl(context)
    }

    describe("getVersionName") {
        it("should return the version name of the installed package") {
            dataSource(versionName = "1.0.1").getVersionName() shouldBe "1.0.1"
        }

        it("should return an empty string when the package has no version name") {
            dataSource(versionName = null).getVersionName() shouldBe ""
        }
    }
})
