/*
 * Copyright 2018, The Android Open Source Project
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
 *
 */

package com.example.android.devbyteviewer.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.viewmodels.DevByteViewModel
import com.example.android.devbyteviewer.databinding.FragmentDevByteBinding
import com.example.android.devbyteviewer.databinding.DevbyteItemBinding


/**
 * Show a list of DevBytes on screen.
 * Показать список DevBytes на экране.
 * Контроллер пользовательского интерфейса DevByteFragment
 * содержит элемент RecyclerView для отображения списка видео и наблюдателей для LiveData объектов.
 */
class DevByteFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use lazy.
     * This requires that viewModel not be referenced before onActivityCreated, which we do in this Fragment.
     * Один из способов отложить создание viewModel до подходящего метода жизненного цикла - это использовать lazy.
     * Это требует, чтобы на viewModel не ссылались до onActivityCreated, что мы и делаем в этом фрагменте.
     */
    private val viewModel: DevByteViewModel by viewModels {
        DevByteViewModel.Factory(requireNotNull(this.activity).application)
    }
   /* private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        // DevByteViewModel расширяется AndroidViewModel(application)
        // класс DevByteViewModel.Factory расположена внутри класса DevByteViewModel
        ViewModelProvider(this, DevByteViewModel.Factory(activity.application))
                .get(DevByteViewModel::class.java)
    }*/

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     * RecyclerView адаптер для преобразования списка видео на карты памяти.
     */
    private var viewModelAdapter: DevByteAdapter? = null

    /**
     * Called when the fragment's activity has been created
     * and this fragment's view hierarchy instantiated.
     * It can be used to do final initialization once these pieces
     * are in place, such as retrieving views or restoring state.
     * Вызывается при создании активности фрагмента
     * и была создана иерархия представлений этого фрагмента.
     * Он может быть использован для окончательной инициализации после того, как эти части
     * находятся на месте, например, получение представлений или восстановление состояния.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.playlist.observe(viewLifecycleOwner) { videos ->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Вызывается для того, чтобы фрагмент создал экземпляр своего представления пользовательского интерфейса.
     *
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     * <p>если вы вернете вид отсюда, то позже вас вызовут
     * {@link #onDestroyView}, когда представление освобождается.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentDevByteBinding.inflate(inflater)
        /*val binding: FragmentDevByteBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dev_byte,
                container,
                false)
         */

        // Set the lifecycleOwner so DataBinding can observe LiveData
        // Установите привязку данных владельцев жизненного цикла, чтобы они могли наблюдать живые данные
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        // Класс DevByteAdapter в этом же файле внизу
        viewModelAdapter = DevByteAdapter(VideoClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter
            // При нажатии на видео этот блок или лямбда-код будет вызван DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no longer on the screen
            // контекста вокруг нет, мы можем смело отбросить этот щелчок,
            // так как фрагмент больше не находится на экране
            val packageManager = context?.packageManager ?: return@VideoClick

            // Try to generate a direct intent to the YouTube app
            // Попробуйте создать прямое намерение для приложения YouTube
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if (intent.resolveActivity(packageManager) == null)
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                // YouTube app isn't found, use the web url
                // Приложение YouTube не найдено, используйте веб-url
            startActivity(intent)
        })

        /*binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }*/
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        // Observer for the network error.
        // Наблюдатель для ошибки сети.
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

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
     * Используется выше при создании Intent
     */
    private val Video.launchUri: Uri
        get() = Uri.parse("vnd.youtube:" +  Uri.parse(url).getQueryParameter("v"))
        /*{
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }*/
}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 * Нажмите кнопку прослушиватель для видео. Давая блоку имя, он помогает читателю понять, что он делает.
 *
 */
class VideoClick(val block: (Video) -> Unit) {
    /**
     * Called when a video is clicked
     * Вызывается при нажатии на видео
     *
     * @param video the video that was clicked
     */
    fun onClick(video: Video) = block(video)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 * Адаптер RecyclerView для настройки привязки данных к элементам списка.
 */
class DevByteAdapter(private val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    /**
     * The videos that our Adapter will show
     * Видео, которые покажет наш адаптер
     */
    var videos: List<Video> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.
            // Для дополнительной задачи обновите его, чтобы использовать библиотеку подкачки.

            // Notify any registered observers that the data set has changed.
            // This will cause every element in our RecyclerView to be invalidated.
            // Уведомить всех зарегистрированных наблюдателей об изменении набора данных.
            // Это приведет к тому, что каждый элемент в нашем RecyclerView будет признан недействительным.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent an item.
     * Вызывается, когда RecyclerView нуждается в новом {@link ViewHolder} данного типа для представления элемента.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder
       //  = DevByteViewHolder(DevbyteItemBinding.inflate(LayoutInflater.from(parent.context)))
    = DevByteViewHolder( DevbyteItemBinding.inflate(
        LayoutInflater.from(parent.context), parent,false))

      /* { val withDataBinding: DevbyteItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.devbyte_item,
                parent,
                false)
        return DevByteViewHolder(withDataBinding)
        }*/

    override fun getItemCount() = videos.size

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the {@link ViewHolder#itemView}
     * to reflect the item at the given position.
     * Вызывается RecyclerView для отображения данных в указанном положении.
     * Этот метод должен обновить содержимое {@link ViewHolder#itemView}
     * отразить предмет в заданном положении.
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
        RecyclerView.ViewHolder(viewDataBinding.root)
/*{
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.devbyte_item

    }
}*/
