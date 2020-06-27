View Binding Sample
=============================================

This sample requires Android Studio 3.6+ and showcases the following Architecture Components:

* [View Binding](https://developer.android.com/topic/libraries/view-binding)

This project shows how to use view bindings in an activity and in fragments. `InflateFragment`
uses the `inflate()` API and `BindFragment` shows the less common `bind()` API.
https://android.googlesource.com/platform/frameworks/data-binding/+/refs/heads/studio-master-dev/extensions/baseAdapters/src/main/java/androidx/databinding/adapters
AbsListViewBindingAdapter.java
AbsSeekBarBindingAdapter.java
AbsSpinnerBindingAdapter.java
ActionMenuViewBindingAdapter.java
AdapterViewBindingAdapter.java
AutoCompleteTextViewBindingAdapter.java
CalendarViewBindingAdapter.java
CardViewBindingAdapter.java
CheckedTextViewBindingAdapter.java
ChronometerBindingAdapter.java
CompoundButtonBindingAdapter.java
Converters.java
DatePickerBindingAdapter.java
ExpandableListViewBindingAdapter.java
FrameLayoutBindingAdapter.java
ImageViewBindingAdapter.java
LinearLayoutBindingAdapter.java
ListenerUtil.java
NumberPickerBindingAdapter.java
ObservableListAdapter.java
ProgressBarBindingAdapter.java
RadioGroupBindingAdapter.java
RatingBarBindingAdapter.java
SearchViewBindingAdapter.java
SeekBarBindingAdapter.java
SpinnerBindingAdapter.java
SwitchBindingAdapter.java
SwitchCompatBindingAdapter.java
TabHostBindingAdapter.java
TabWidgetBindingAdapter.java
TableLayoutBindingAdapter.java
TextViewBindingAdapter.java
TimePickerBindingAdapter.java
ToolbarBindingAdapter.java
VideoViewBindingAdapter.java
ViewBindingAdapter.java
ViewGroupBindingAdapter.java
ViewStubBindingAdapter.java
ZoomControlsBindingAdapter.java

Mathematical + - / * %
String concatenation +
Logical && ||
Binary & | ^
Unary + - ! ~
Shift >> >>> <<
Comparison == > < >= <= (Note that < needs to be escaped as &lt;)
instanceof
Grouping ()
Literals - character, String, numeric, null
Cast
Method calls
Field access
Array access []
Ternary operator ?:
Examples:

android:text="@{String.valueOf(index + 1)}"
android:visibility="@{age > 13 ? View.GONE : View.VISIBLE}"
android:transitionName='@{"image_" + id}'
android:text="@{user.displayName ?? user.lastName}"

<data>
    <import type="android.util.SparseArray"/>
    <import type="java.util.Map"/>
    <import type="java.util.List"/>
    <variable name="list" type="List&lt;String>"/>
    <variable name="sparse" type="SparseArray&lt;String>"/>
    <variable name="map" type="Map&lt;String, String>"/>
    <variable name="index" type="int"/>
    <variable name="key" type="String"/>
</data>
…
android:text="@{list[index]}"
…
android:text="@{sparse[index]}"
…
android:text="@{map[key]}"

вместо List<String>тебя надо писать List&lt;String>.
android:padding="@{large? @dimen/largePadding : @dimen/smallPadding}"

android:onClick="@{handlers::onClickFriend}"/
 fun onClickFriend (view: View) {...}

 fun onSaveClick (task: Task) {}
  android:onClick="@{() -> presenter.onSaveClick(task)}" />
  android:onClick="@{(view) -> presenter.onSaveClick(task)}"

   fun onSaveClick (view: View, task: Task) {}
   android:onClick="@{(theView) -> presenter.onSaveClick(theView, task)}"

    fun onCompletedChanged(task: Task, completed: Boolean){}
    android:onCheckedChanged="@{(cb, isChecked) -> presenter.completeChanged(task, isChecked)}"

    class Presenter { fun onLongClick ( view : View , task : Task ): Boolean { } }
    android:onLongClick="@{(theView) -> presenter.onLongClick(theView, task)}"

    android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"

    <import type="android.view.View"/>
    android:visibility="@{user.isAdult ? View.VISIBLE : View.GONE}"/>

    import type="com.example.real.estate.View"
            alias="Vista"/>

<data>
    <import type="com.example.User"/>
    <import type="java.util.List"/>
    <variable name="user" type="User"/>
    <variable name="userList" type="List&lt;User>"/>
</data>
 android:text="@{((User)(user.connection)).lastName}"

 <data>
     <import type="com.example.MyStringUtils"/>
     <variable name="user" type="com.example.User"/>
 </data>
 …
 <TextView
    android:text="@{MyStringUtils.capitalize(user.lastName)}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>

    Как и в управляемом коде, java.lang.*импортируется автоматически.

    Работа с наблюдаемыми объектами данных  объекты , поля и коллекции .
    ObservableBoolean
    ObservableByte
    ObservableChar
    ObservableShort
    ObservableInt
    ObservableLong
    ObservableFloat
    ObservableDouble
    ObservableParcelable
    заменять наблюдаемые поля объектами LiveData

    Чтобы упростить разработку, библиотека привязок данных предоставляет BaseObservable класс,
    который реализует механизм регистрации слушателя.
    Реализующий класс данных BaseObservable отвечает за уведомление об изменении свойств.
    Это делается путем назначения Bindableаннотации получателю
    и вызова notifyPropertyChanged() метода в установщике, как показано в следующем примере:
    class User : BaseObservable() {

        @get:Bindable
        var firstName: String = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.firstName)
            }

        @get:Bindable
        var lastName: String = ""
            set(value) {
                field = value
                notifyPropertyChanged(BR.lastName)
            }
    }
Сгенерированные классы привязки
 val binding: MyLayoutBinding = MyLayoutBinding.inflate(layoutInflater)
 val binding: MyLayoutBinding = MyLayoutBinding.inflate(getLayoutInflater(), viewGroup, false)
 val binding: MyLayoutBinding = MyLayoutBinding.bind(viewRoot)
 val viewRoot = LayoutInflater.from(this).inflate(layoutId, parent, attachToParent)
 val binding: ViewDataBinding? = DataBindingUtil.bind(viewRoot)

 val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
 val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)

 <data class="ContactItem">
     …
 </data>
 <data class=".ContactItem">
 <data class="com.example.ContactItem">

 Связующие адаптеры Binding adapters
 BindingMethods annotation
 @BindingMethods (value = [
     BindingMethod (
         type = android.widget.ImageView :: class,
         attribute = "android: tint",
         method = "setImageTintList")])

Пользовательские преобразования Custom conversions
@BindingConversion
fun convertColorToDrawable (color: Int) = ColorDrawable (color)

Bind layout views to Architecture Components
Используйте LiveData для уведомления пользовательского интерфейса об изменениях данных
В отличие от объектов, которые реализуют Observable- такие как наблюдаемые поля
- LiveData объекты знают о жизненном цикле наблюдателей, подписавшихся на изменения данных.
Это знание дает много преимуществ, которые описаны в разделе Преимущества использования LiveData .
class ViewModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Inflate view and obtain an instance of the binding class.
        val binding: UserBinding = DataBindingUtil.setContentView(this, R.layout.user)

        // Specify the current activity as the lifecycle owner.
        binding.setLifecycleOwner(this)
    }
}
Вы можете использовать ViewModel компонент
class ScheduleViewModel : ViewModel() {
    val userName: LiveData

    init {
        val result = Repository.userName
        userName = Transformations.map(result) { result -> result.value }
    }
}
Используйте ViewModel для управления данными, связанными с пользовательским интерфейсом
class ViewModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtain the ViewModel component.
        val userModel: UserModel by viewModels()

        // Inflate view and obtain an instance of the binding class.
        val binding: UserBinding = DataBindingUtil.setContentView(this, R.layout.user)

        // Assign the component to a property in the binding class.
        binding.viewmodel = userModel
    }
}
<CheckBox
    android:id="@+id/rememberMeCheckBox"
    android:checked="@{viewmodel.rememberMe}"
    android:onCheckedChanged="@{() -> viewmodel.rememberMeChanged()}" />

Use an Observable ViewModel for more control over binding adapters
Используйте Observable ViewModel для большего контроля над адаптерами привязки
Чтобы реализовать наблюдаемый ViewModelкомпонент, вы должны создать класс,
который наследуется от ViewModelкласса и реализует Observable интерфейс.

/**
 * A ViewModel that is also an Observable,
 * to be used with the Data Binding Library.
 */
open class ObservableViewModel : ViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(
            callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
            callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}
Двусторонняя привязка данных Two-way data binding
Using one-way data binding, you can set a value on an attribute and set a listener that reacts to a change in that attribute:

<CheckBox
    android:id="@+id/rememberMeCheckBox"
    android:checked="@{viewmodel.rememberMe}"
    android:onCheckedChanged="@{viewmodel.rememberMeChanged}"
/>
Two-way data binding provides a shortcut to this process:

<CheckBox
    android:id="@+id/rememberMeCheckBox"
    android:checked="@={viewmodel.rememberMe}"
/>

class LoginViewModel : BaseObservable {
    // val data = ...

    @Bindable
    fun getRememberMe(): Boolean {
        return data.rememberMe
    }

    fun setRememberMe(value: Boolean) {
        // Avoids infinite loops.
        if (data.rememberMe != value) {
            data.rememberMe = value

            // React to the change.
            saveData()

            // Notify observers of a new value.
            notifyPropertyChanged(BR.remember_me)
        }
    }
}
Двусторонняя привязка данных с использованием пользовательских атрибутов
@InverseBindingAdapter и @InverseBindingMethod аннотаций.
@BindingAdapter ("time")
@JvmStatic fun setTime (view: MyView, newValue: Time) {
    // Важно прерывать потенциальные бесконечные циклы.
    if (view.time! = newValue) {
        view.time = newValue
    }
}

Annotate the method that reads the value from the view using @InverseBindingAdapter:

KOTLIN
JAVA
@InverseBindingAdapter("time")
@JvmStatic fun getTime(view: MyView) : Time {
    return view.getTime()
}
@BindingAdapter("app:timeAttrChanged")
@JvmStatic fun setListeners(
        view: MyView,
        attrChange: InverseBindingListener
) {
    // Set a listener for click, focus, touch, etc.
}

Converters
<EditText
    android:id="@+id/birth_date"
    android:text="@={Converter.dateToString(viewmodel.birthDate)}"
/>
отформатирован с помощью преобразователя.

Поскольку используется двустороннее выражение, также необходим обратный преобразователь,
чтобы библиотека знала, как преобразовать предоставленную пользователем строку обратно в тип данных поддержки, в этом случае Long.
Этот процесс выполняется путем добавления @InverseMethod аннотации к одному из преобразователей,
и эта аннотация ссылается на обратный преобразователь.
Пример этой конфигурации показан в следующем фрагменте кода:
object Converter {
    @InverseMethod("stringToDate")
    @JvmStatic fun dateToString(
        view: EditText, oldValue: Long,
        value: Long
    ): String {
        // Converts long to String.
    }

    @JvmStatic fun stringToDate(
        view: EditText, oldValue: String,
        value: String
    ): Long {
        // Converts String to long.
    }
}

Бесконечные циклы с использованием двусторонней привязки данных

Двусторонние атрибуты
AdapterView	android:selectedItemPosition android:selection	AdapterViewBindingAdapter
CalendarView	android:date	CalendarViewBindingAdapter
CompoundButton	android:checked	CompoundButtonBindingAdapter
DatePicker	android:year android:month android:day	DatePickerBindingAdapter
NumberPicker	android:value	NumberPickerBindingAdapter
RadioButton	android:checkedButton	RadioGroupBindingAdapter
RatingBar	android:rating	RatingBarBindingAdapter
SeekBar	android:progress	SeekBarBindingAdapter
TabHost	android:currentTab	TabHostBindingAdapter
TextView	android:text	TextViewBindingAdapter
TimePicker	android:hour android:minute	TimePickerBindingAdapter






















License
--------

Copyright 2020 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.



