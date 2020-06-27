/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.navigation

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder

/**
 * App Widget that deep links you to the [DeepLinkFragment].
 * Виджет приложения, который глубоко связывает вас с [фрагментом глубокой ссылки].
 */
class DeepLinkAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.deep_link_appwidget
        )

        val args = Bundle()
        args.putString("myarg", "From Widget")
        // TODO STEP 10 - construct and set a PendingIntent using DeepLinkBuilder
        // Чтобы сделать шаг 10-построить и установить PendingIntent с помощью Deep LinkBuilder
        val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.mobile_navigation)   // включает в себя график навигации.
                .setDestination(R.id.deeplink_dest)         //  указывает, куда идет ссылка.
                .setArguments(args)                         // ключает в себя любые аргументы,
                .createPendingIntent()                      // которые вы хотите передать в вашу глубокую ссылку.
// As a convenience, you can also call NavController's createDeepLink() method
// to use the Context and current navigation graph from the NavController.
// Для удобства вы также можете вызвать метод create Deep Link() Nav Controller
// чтобы использовать контекст и текущий навигационный график из навигационного контроллера.
        remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)
        // TODO END STEP 10
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}
