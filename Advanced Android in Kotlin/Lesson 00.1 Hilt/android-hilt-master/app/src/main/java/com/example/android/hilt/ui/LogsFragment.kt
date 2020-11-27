/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.hilt.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.hilt.LogApplication
import com.example.android.hilt.R
import com.example.android.hilt.data.Log
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays the database logs.
 * Фрагмент, отображающий журналы базы данных.
 */
/**
 * Аннотирование классов Android с помощью @AndroidEntryPoint создает контейнер зависимостей,
 * который следует жизненному циклу класса Android.
 * С помощью @AndroidEntryPoint Hilt будет создан контейнер зависимостей,
 * который присоединен к LogsFragment жизненному циклу и сможет внедрять экземпляры в LogsFragment
 */
// Чтобы LogsFragment использовать Hilt, мы должны аннотировать его @AndroidEntryPoint:
@AndroidEntryPoint
class LogsFragment : Fragment() {

    //Мы можем заставить Hilt вводить экземпляры разных типов с @Inject аннотацией к полям,
    // которые мы хотим внедрить (т.е. logger и dateFormatter):
    // Предупреждение: поля, введенные Hilt, не могут быть частными .
    // Это то, что называется полевой инъекцией .

    @Inject lateinit var logger: LoggerLocalDataSource
    @Inject lateinit var dateFormatter: DateFormatter

    // Чтобы выполнить внедрение поля, Hilt необходимо знать, как предоставить экземпляры этих зависимостей!
    // В этом случае Hilt необходимо знать, как предоставить экземпляры LoggerLocalDataSource и DateFormatter.
    // Однако Hilt еще не знает, как предоставить эти экземпляры.
    // Информация Hilt о том, как предоставлять экземпляры разных типов, также называется привязками
    // На данный момент у Hilt есть две привязки: как предоставить экземпляры
    // 1) DateFormatter и 2) LoggerLocalDataSource.


    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
    }

// Поскольку Hilt будет отвечать за заполнение этих полей за нас, этот populateFields метод нам больше не нужен.
// Удалим метод из класса:
// LogsFragment заполняет свои поля в onAttach
/*    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        logger = (context.applicationContext as LogApplication).serviceLocator.loggerLocalDataSource
        dateFormatter =
            (context.applicationContext as LogApplication).serviceLocator.provideDateFormatter()
    }
*/
    override fun onResume() {
        super.onResume()

        logger.getAllLogs { logs ->
            recyclerView.adapter =
                LogsViewAdapter(
                    logs,
                    dateFormatter
                )
        }
    }
}

/**
 * RecyclerView adapter for the logs list.
 * Адаптер RecyclerView для списка журналов.
 */
private class LogsViewAdapter(
    private val logsDataSet: List<Log>,
    private val daterFormatter: DateFormatter
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.text_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.textView.text = "${log.msg}\n\t${daterFormatter.formatDate(log.timestamp)}"
    }
}
