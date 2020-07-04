/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.trackmysleepquality.database.SleepNight
import java.text.SimpleDateFormat

// Файл содержит функции , чтобы помочь отображению данных о качестве сна.
/**
 * These functions create a formatted string that can be set in a TextView.
 * Эти функции создают форматированную строку,которую можно задать в текстовом представлении.
 */

/**
 * Returns a string representing the numeric quality rating.
 * Возвращает строку, представляющую числовую оценку качества.
 */
fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.three_ok)
    when (quality) {
        -1 -> qualityString = "--"
        0 -> qualityString = resources.getString(R.string.zero_very_bad)
        1 -> qualityString = resources.getString(R.string.one_poor)
        2 -> qualityString = resources.getString(R.string.two_soso)
        4 -> qualityString = resources.getString(R.string.four_pretty_good)
        5 -> qualityString = resources.getString(R.string.five_excellent)
    }
    return qualityString
}


/**
 * Take the Long milliseconds returned by the system and stored in Room,
 * and convert it to a nicely formatted string for display.
 * Возьмите длинные миллисекунды, возвращенные системой и хранящиеся в комнате,
 * и преобразуйте его в красиво отформатированную строку для отображения.
 *
 * EEEE - Display the long letter version of the weekday Отображение длиннобуквенной версии буднего дня
 * MMM - Display the letter abbreviation of the nmotny - Выведите на экран буквенную аббревиатуру нмотны
 * dd-yyyy - day in month and full year numerically - день в месяце и полный год численно
 * HH:mm - Hours and minutes in 24hr format - Часы и минуты в формате 24 часа
 */
@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
            .format(systemTime).toString()
}

/**
 * Takes a list of SleepNights and converts and formats it into one string for display.
 * Берет список ночей сна и преобразует и форматирует его в одну строку для отображения.
 *
 * For display in a TextView, we have to supply one string, and styles are per TextView, not
 * applicable per word. So, we build a formatted string using HTML. This is handy, but we will
 * learn a better way of displaying this data in a future lesson.
 * Для отображения в текстовом представлении мы должны предоставить одну строку,
 * а стили-для каждого текстового представления, а не для каждого текста.
 * применимо для каждого слова. Итак, мы строим форматированную строку с использованием HTML.
 * Это очень удобно, но мы сделаем это
 * изучите лучший способ отображения этих данных на следующем уроке.
 *
 * @param   nights - List of all SleepNights in the database. - Список всех ночей сна в базе данных.
 * @param   resources - Resources object for all the resources defined for our app.
 *          Объект Resources для всех ресурсов, определенных для нашего приложения.
 *
 * @return  Spanned - An interface for text that has formatting attached to it.
 *          Spanned-интерфейс для текста, к которому прикреплено форматирование.
 *           See: https://developer.android.com/reference/android/text/Spanned
 */
// тип Spanned, который является строкой в​формате HTML. он поступает через res/string/start_time...
fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTimeMilli)}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTimeMilli)}<br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.sleepQuality, resources)}<br>")
                append(resources.getString(R.string.hours_slept))
                // Hours
                append("\t ${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000 / 60}:")
                // Seconds
                append("${it.endTimeMilli.minus(it.startTimeMilli) / 1000}<br><br>")
            }
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}
