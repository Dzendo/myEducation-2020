/*
 * Copyright (C) 2019 Google Inc.
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

package com.example.android.devbyteviewerw.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.devbyteviewerw.R
import com.example.android.devbyteviewerw.databinding.DevbyteItemBinding
import com.example.android.devbyteviewerw.databinding.FragmentDevByteBinding
import com.example.android.devbyteviewerw.domain.DevByteVideo
import com.example.android.devbyteviewerw.viewmodels.DevByteViewModel

/**
 * Show a list of DevBytes on screen.
 */
class DevByteFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     * Один из способов отсрочить создание viewModel до тех пор, пока не будет использован соответствующий метод жизненного цикла
     * ленивый. Это требует, чтобы viewModel не ссылался перед onActivityCreated, который мы
     * сделайте это в этом фрагменте.
     */
    private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, DevByteViewModel.Factory(activity.application))
                .get(DevByteViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     * адаптер recyclerview для преобразования списка видео на карты памяти.
     */
    private var viewModelAdapter: DevByteAdapter? = null

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     *
     * Вызывается, когда активность фрагмента была создана и это
     * создан экземпляр иерархии представлений фрагмента. Его можно использовать для того чтобы сделать окончательное
     * инициализация после того, как эти части находятся на месте, например извлечение
     * просмотр или восстановление состояния.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<DevByteVideo>> { videos ->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }
        })
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Вызывается для того, чтобы фрагмент создавал экземпляр своего представления пользовательского интерфейса.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     * <p>если вы вернете вид отсюда, то позже вас вызовут
     * {@link #onDestroyView} при освобождении представления.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @ param inflater объект LayoutInflater, который можно использовать для надувания
     * любые представления во фрагменте,
     * @ param container если значение не равно null, то это родительское представление, в котором находится фрагмент.
     * Пользовательский интерфейс должен быть прикреплен к. Фрагмент не должен добавлять само представление,
     * но это может быть использовано для создания LayoutParams представления.
     * @ param savedInstanceState если значение не равно null, то этот фрагмент создается заново
     * из предыдущего сохраненного состояния, как указано здесь.
     *
     * @return Return the View for the fragment's UI.
     * return возвращает представление для пользовательского интерфейса фрагмента.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentDevByteBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dev_byte,
                container,
                false)
        // Set the lifecycleOwner so DataBinding can observe LiveData
        // Установите привязку данных владельцев жизненного цикла, чтобы они могли наблюдать живые данные
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = DevByteAdapter(VideoClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter
            // При нажатии на видео этот блок или лямбда-код будет вызван DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            // контекста нет рядом, мы можем смело отбросить этот клик, так как фрагмента нет
            // дольше на экране
            val packageManager = context?.packageManager ?: return@VideoClick

            // Try to generate a direct intent to the YouTube app
            // Попробуйте создать прямое намерение для приложения YouTube
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if(intent.resolveActivity(packageManager) == null) {
                // YouTube app isn't found, use the web url
                // Приложение YouTube не найдено, используйте веб-адрес
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }

            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Observer for the network error.
        // Наблюдатель для ошибки сети.
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     * Метод отображения всплывающего сообщения об ошибке для сетевых ошибок.
     */

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    /**
     * Helper method to generate YouTube app links
     * Вспомогательный метод для создания ссылок на приложения YouTube
     */
    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }
}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 * Нажмите кнопку прослушиватель для видео. Давая блоку имя, он помогает читателю понять, что он делает.
 *
 */
class VideoClick(val block: (DevByteVideo) -> Unit) {
    /**
     * Called when a video is clicked
     * Вызывается при нажатии на видео.
     *
     * @param video the video that was clicked
     * @ param video видео, которое было нажато
     */
    fun onClick(video: DevByteVideo) = block(video)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    /**
     * The videos that our Adapter will show
     * Видео, которые покажет наш адаптер
     */
    var videos: List<DevByteVideo> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.
            // Для дополнительной задачи обновите этот параметр, чтобы использовать библиотеку подкачки.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            // Уведомить всех зарегистрированных наблюдателей об изменении набора данных. Это приведет к тому, что каждый
            // элемент в нашем RecyclerView должен быть признан недействительным.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
     * Вызывается, когда RecyclerView нуждается в новом {@link ViewHolder} данного типа для представления элемента.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: DevbyteItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                DevByteViewHolder.LAYOUT,
                parent,
                false)
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount() = videos.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * Вызывается RecyclerView для отображения данных в указанной позиции. Этот метод должен
     * обновите содержимое {@link ViewHolder#itemView}, чтобы отразить элемент в данном окне.
     * позиция.
     */
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 * ViewHolder для элементов DevByte. Вся работа выполняется путем привязки данных.
 */
class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.devbyte_item
    }
}