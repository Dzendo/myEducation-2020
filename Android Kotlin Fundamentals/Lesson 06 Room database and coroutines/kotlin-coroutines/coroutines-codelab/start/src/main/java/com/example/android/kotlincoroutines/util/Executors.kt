/*
 * Copyright (C) 2019 Google LLC
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

package com.example.android.kotlincoroutines.util

import java.util.concurrent.Executors

/**
 * An executor service that can run [Runnable]s off the main thread.
 * Executorservice, который может запускать [Runnable]s вне основного потока.
 * BACKGROUND ExecutorService(определенный в util/Executor.kt) для запуска в фоновом потоке.
 */
val BACKGROUND = Executors.newFixedThreadPool(2)

/*
Объект, который выполняет отправлено Runnable задачи.
Этот интерфейс предоставляет способ отсоединения отправки задачи от механика того, как будет выполняться каждая задача,
включая детали потока использование, планирование и т.д.
Один Executor обычно используется вместо явного создания потоков.
Например, вместо того, чтобы вызывающий new Thread(new RunnableTask()).start() для каждого из набора задач,
которые вы можете использовать:
Executor executor = anExecutor();
 executor.execute(new RunnableTask1());
 executor.execute(new RunnableTask2());
 ...

 */