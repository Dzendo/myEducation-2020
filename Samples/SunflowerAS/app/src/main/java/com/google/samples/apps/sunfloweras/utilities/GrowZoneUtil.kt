/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunfloweras.utilities

import kotlin.math.abs

/**
 * A helper function to determine a Plant's growing zone for a given latitude.
 * * Вспомогательная функция для определения зоны произрастания растения для данной широты.
 *
 * The numbers listed here are roughly based on the United States Department of Agriculture's
 * Plant Hardiness Zone Map (http://planthardiness.ars.usda.gov/), which helps determine which
 * plants are most likely to thrive at a location.
 * Приведенные здесь цифры примерно основаны на данных Министерства сельского хозяйства Соединенных Штатов
 * Карта зоны зимостойкости растений (http://planthardiness.ars.usda.gov/), что помогает определить, какие именно
 * растения, скорее всего, будут процветать в определенном месте.
 *
 * If a given latitude falls on the border between two zone ranges, the larger zone range is chosen
 * (e.g. latitude 14.0 => zone 12).
 * Если заданная широта приходится на границу между двумя зональными диапазонами, то выбирается больший зональный диапазон
 * (например, широта 14.0 = > зона 12).
 *
 * Negative latitude values are converted to positive with [Math.abs].
 * Отрицательные значения широты преобразуются в положительные с помощью [Math.брюшной пресс].
 *
 * For latitude values greater than max (90.0), zone 1 is returned.
 * Для значений широты, превышающих max (90,0), возвращается зона 1.
 */
fun getZoneForLatitude(latitude: Double) = when (abs(latitude)) {
    in 0.0..7.0 -> 13
    in 7.0..14.0 -> 12
    in 14.0..21.0 -> 11
    in 21.0..28.0 -> 10
    in 28.0..35.0 -> 9
    in 35.0..42.0 -> 8
    in 42.0..49.0 -> 7
    in 49.0..56.0 -> 6
    in 56.0..63.0 -> 5
    in 63.0..70.0 -> 4
    in 70.0..77.0 -> 3
    in 77.0..84.0 -> 2
    else -> 1 // Remaining latitudes are assigned to zone 1.
    // Остальные широты отнесены к зоне 1.
}