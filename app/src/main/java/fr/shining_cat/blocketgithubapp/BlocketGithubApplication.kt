/*
 * Copyright (c) 2021. Shining-cat - Shiva Bernhard
 * This file is part of Everyday.
 *
 *     Everyday is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, version 3 of the License.
 *
 *     Everyday is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Everyday.  If not, see <https://www.gnu.org/licenses/>.
 */

package fr.shining_cat.blocketgithubapp

import android.app.Application
import fr.shining_cat.blocketgithubapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BlocketGithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    private fun configureDi() {
        startKoin {

            androidContext(this@BlocketGithubApplication)

            modules(
                listOf(
                    appModule
                )
            )
        }
    }

}