
https://codelabs.developers.google.com/codelabs/android-room-with-a-view-kotlin/#0

1. Прежде чем начать
Цель компонентов архитектуры - предоставить руководство по архитектуре приложения с библиотеками для общих задач, таких как управление жизненным циклом и сохранение данных. Компоненты архитектуры помогают структурировать ваше приложение таким образом, чтобы оно было надежным, тестируемым и обслуживаемым с меньшим количеством стандартного кода. Библиотеки компонентов архитектуры являются частью Android Jetpack .

Это Kotlin-версия кодовой метки. Версию на языке программирования Java можно найти здесь .

Если вы столкнетесь с какими-либо проблемами (ошибками в коде, грамматическими ошибками, нечеткой формулировкой и т. Д.) Во время работы с этой кодовой меткой, сообщите о проблеме через ссылку Сообщить об ошибке в левом нижнем углу кодовой метки.

Предпосылки
Вы должны быть знакомы с Kotlin, концепциями объектно-ориентированного проектирования и основами разработки Android, в частности:

RecyclerView и адаптеры
База данных SQLite и язык запросов SQLite
Базовые сопрограммы (Если вы не знакомы с сопрограммами, вы можете перейти к использованию сопрограмм Kotlin в своем приложении для Android .)
Это также помогает ознакомиться с архитектурными шаблонами программного обеспечения, которые отделяют данные от пользовательского интерфейса, такого как MVP или MVC. Эта кодовая метка реализует архитектуру, определенную в Руководстве по архитектуре приложения .

Эта кодовая метка ориентирована на компоненты архитектуры Android. Вне темы концепции и код предоставлены для вас просто скопировать и вставить.

Если вы не знакомы с Kotlin, версия этой кодовой метки предоставляется на языке программирования Java здесь .

Что ты будешь делать
В этом фрагменте кода вы узнаете, как проектировать и конструировать приложение, используя Комнату компонентов архитектуры, ViewModel и LiveData, а также создать приложение, которое выполняет следующие действия:

Реализует нашу рекомендуемую архитектуру, используя компоненты архитектуры Android.
Работает с базой данных, чтобы получать и сохранять данные, и предварительно заполняет базу данных несколькими словами.
Отображает все слова RecyclerViewв MainActivity.
Открывает второе действие, когда пользователь нажимает кнопку +. Когда пользователь вводит слово, добавляет слово в базу данных и список.
Приложение без излишеств, но достаточно сложное, чтобы вы могли использовать его в качестве шаблона для построения. Вот предварительный просмотр:







Что вам нужно
Android Studio 3.0 или более поздней версии и знание того, как ее использовать. Убедитесь, что Android Studio обновлена, а также ваш SDK и Gradle.
Android-устройство или эмулятор.
Эта кодовая метка содержит весь код, необходимый для создания законченного приложения.

Обратите внимание, что код решения доступен в виде zip-файла или github-репо . Мы рекомендуем вам создавать приложение с нуля и смотреть на этот код, только если вы застряли.

2. Использование компонентов архитектуры
Существует много шагов для использования компонентов архитектуры и реализации рекомендуемой архитектуры. Самое главное - создать мысленную модель того, что происходит, понять, как части сочетаются друг с другом и как потоки данных. Работая с этим кодовым ярлыком, не просто копируйте и вставляйте код, но постарайтесь начать строить это внутреннее понимание.

Каковы рекомендуемые компоненты архитектуры?
Чтобы ввести терминологию, вот краткое введение в Компоненты Архитектуры и как они работают вместе. Обратите внимание, что эта кодовая метка фокусируется на подмножестве компонентов, а именно LiveData, ViewModel и Room. Каждый компонент объясняется больше, как вы его используете.

Эта схема показывает базовую форму архитектуры:



Entity : Аннотированный класс, который описывает таблицу базы данных при работе с Room .

База данных SQLite: на устройстве хранения. Библиотека постоянных комнат создает и поддерживает эту базу данных для вас.

DAO : объект доступа к данным. Отображение запросов SQL на функции. Когда вы используете DAO, вы вызываете методы, а Room позаботится об остальном.

База данных Room : упрощает работу базы данных и служит точкой доступа к базовой базе данных SQLite (скрываетсяSQLiteOpenHelper). База данных Room использует DAO для выдачи запросов к базе данных SQLite.

Репозиторий: созданный вами класс, который в основном используется для управления несколькими источниками данных.

ViewModel : действует как центр связи между репозиторием (данными) и пользовательским интерфейсом. Пользовательский интерфейс больше не должен беспокоиться о происхождении данных. Экземпляры ViewModel выживают Активность / Фрагмент.

LiveData : класс держателя данных, который можно наблюдать . Всегда хранит / кэширует последнюю версию данных и уведомляет своих наблюдателей об изменении данных. LiveDataосведомлен о жизненном цикле. Компоненты пользовательского интерфейса просто наблюдают соответствующие данные и не останавливают и не возобновляют наблюдение. LiveData автоматически управляет всем этим, поскольку во время наблюдения он знает об изменениях состояния жизненного цикла.

Ищете больше? Ознакомьтесь с полным руководством по архитектуре приложений .

RoomWordSample обзор архитектуры
Следующая диаграмма показывает все части приложения. Каждый из вмещающих блоков (кроме базы данных SQLite) представляет класс, который вы создадите.

3. Создайте свое приложение
Откройте Android Studio и нажмите « Начать новый проект Android Studio».
В окне «Создать новый проект» выберите « Пустое действие» и нажмите « Далее».
На следующем экране назовите приложение RoomWordSample и нажмите « Готово» .

4. Обновите файлы Gradle
Затем вам нужно добавить библиотеки компонентов в ваши файлы Gradle.

В Android Studio откройте вкладку «Проекты» и раскройте папку «Скрипты Gradle».
Открыть build.gradle( Модуль: приложение ).

Примените плагин Kotlin kapt процессора аннотаций , добавив его после других плагинов, определенных в верхней части вашего файла build.gradle( Module: app ).
apply plugin: 'kotlin-kapt'
Добавьте packagingOptionsблок внутри androidблока, чтобы исключить модуль атомарных функций из пакета и предотвратить предупреждения.
android {
    // other configuration (buildTypes, defaultConfig, etc.)

    packagingOptions {
        exclude 'META-INF/atomicfu.kotlin_module'
    }
}
Добавьте следующий код в конце dependenciesблока.
// Room components
implementation "androidx.room:room-runtime:$rootProject.roomVersion"
kapt "androidx.room:room-compiler:$rootProject.roomVersion"
implementation "androidx.room:room-ktx:$rootProject.roomVersion"
androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"

// Lifecycle components
implementation "androidx.lifecycle:lifecycle-extensions:$rootProject.archLifecycleVersion"
kapt "androidx.lifecycle:lifecycle-compiler:$rootProject.archLifecycleVersion"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.archLifecycleVersion"

// Kotlin components
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
api "org.jetbrains.kotlinx:kotlinx-coroutines-core:$rootProject.coroutines"
api "org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines"

// Material design
implementation "com.google.android.material:material:$rootProject.materialVersion"

// Testing
testImplementation 'junit:junit:4.12'
androidTestImplementation "androidx.arch.core:core-testing:$rootProject.coreTestingVersion"
В своем файле build.gradle( Project: RoomWordsSample ) добавьте номера версий в конец файла, как показано в приведенном ниже коде.
Получите самые последние номера версий из раздела Добавление компонентов на страницу вашего проекта .

ext {
    roomVersion = '2.2.5'
    archLifecycleVersion = '2.2.0'
    coreTestingVersion = '2.1.0'
    materialVersion = '1.1.0'
    coroutines = '1.3.4'
}

5. Создайте сущность
Данные для этого приложения - слова, и вам понадобится простая таблица для хранения этих значений:



Комната позволяет создавать таблицы через сущность . Давайте сделаем это сейчас.

Создайте новый файл класса Kotlin с именем, Wordсодержащим Word класс данных .
Этот класс будет описывать сущность (которая представляет таблицу SQLite) для ваших слов. Каждое свойство в классе представляет столбец в таблице. В конечном итоге Room будет использовать эти свойства как для создания таблицы, так и для создания объектов из строк в базе данных.
Вот код:

data class Word(val word: String)
Чтобы сделать Wordкласс значимым для базы данных Room, вам нужно его аннотировать. Аннотации определяют, как каждая часть этого класса относится к записи в базе данных. Комната использует эту информацию для генерации кода.

Если вы вводите аннотации самостоятельно (вместо вставки), Android Studio автоматически импортирует классы аннотаций.

Обновите ваш Wordкласс аннотациями, как показано в этом коде:
@Entity(tableName = "word_table")
class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String)
Если вы вставили код, вы можете переместить курсор на каждую выделенную ошибку и использовать сочетание клавиш «Быстрое исправление проекта» (Alt + Enter в Windows / Linux, Option + Enter на Mac) для быстрого импорта классов.

Давайте посмотрим, что делают эти аннотации:

@Entity(tableName = "word_table")
Каждый @Entityкласс представляет таблицу SQLite. Аннотируйте объявление вашего класса, чтобы указать, что это объект. Вы можете указать имя таблицы, если хотите, чтобы оно отличалось от имени класса. Это называет таблицу "word_table".
@PrimaryKey
Каждой сущности нужен первичный ключ. Для простоты каждое слово действует как собственный первичный ключ.
@ColumnInfo(name = "word")
Указывает имя столбца в таблице, если вы хотите, чтобы оно отличалось от имени переменной-члена. Это название столбца «слово».
Каждое свойство, хранящееся в базе данных, должно иметь публичную видимость, что является значением по умолчанию Kotlin.
Вы можете найти полный список аннотаций в кратком справочнике пакета Room .

См. Определение данных с использованием сущностей Room .

Совет: Вы можете автоматически генерировать уникальные ключи, пометив первичный ключ следующим образом:

@Entity(tableName = "word_table")

class Word(

@PrimaryKey(autoGenerate = true) val id: Int,

@ColumnInfo(name = "word") val word: String

)

6. Создайте DAO
Что такое DAO?
В DAO (объект доступа к данным) вы задаете запросы SQL и связываете их с вызовами методов. Компилятор проверяет SQL и генерирует запросы из удобных аннотаций для распространенных запросов, таких как @Insert. Room использует DAO для создания чистого API для вашего кода.

DAO должен быть интерфейсом или абстрактным классом.

По умолчанию все запросы должны выполняться в отдельном потоке.

Комната имеет поддержку сопрограмм, что позволяет аннотировать ваши запросы suspendи затем вызывать их из сопрограмм или из другой функции приостановки.

Внедрить DAO
Давайте напишем DAO, который предоставляет запросы для:

Получение всех слов в алфавитном порядке
Вставка слова
Удаление всех слов
Создайте новый файл класса Kotlin с именем WordDao.
Скопируйте и вставьте следующий код WordDaoи исправьте импорт, необходимый для его компиляции.
@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}
Давайте пройдемся по нему:

WordDaoэто интерфейс; DAO должны быть интерфейсами или абстрактными классами.
@DaoАннотацию идентифицирует его как класс DAO для комнаты.
suspend fun insert(word: Word): Объявляет функцию приостановки для вставки одного слова.
@InsertАннотаций это специальный метод DAO аннотация , где вы не должны предоставлять какой - либо SQL! (Есть также @Deleteи @Updateаннотации для удаления и обновления строк, но вы не используете их в этом приложении.)
onConflict = OnConflictStrategy.IGNORE: Выбранная стратегия onConflict игнорирует новое слово, если оно в точности совпадает с тем, которое уже есть в списке. Чтобы узнать больше о доступных конфликтных стратегиях, ознакомьтесь с документацией .
suspend fun deleteAll(): Объявляет функцию приостановки для удаления всех слов.
Нет удобной аннотации для удаления нескольких сущностей, поэтому она помечена универсальным @Query.
@Query("DELETE FROM word_table"): @Queryтребует, чтобы вы предоставили SQL-запрос в виде строкового параметра для аннотации, что позволяет выполнять сложные запросы чтения и другие операции.
fun getAlphabetizedWords(): List<Word>: Метод, позволяющий получить все слова и вернуть его Listиз Words.
@Query("SELECT * from word_table ORDER BY word ASC"): Запрос, возвращающий список слов, отсортированных по возрастанию.
Узнайте больше о Room DAO 

7. Класс LiveData
При изменении данных обычно требуется выполнить какое-либо действие, например, отобразить обновленные данные в пользовательском интерфейсе. Это означает, что вы должны наблюдать за данными, чтобы при их изменении вы могли реагировать.

В зависимости от того, как хранятся данные, это может быть сложно. Наблюдение за изменениями данных в нескольких компонентах вашего приложения может создать явные жесткие пути зависимости между компонентами. Это затрудняет тестирование и отладку, среди прочего.

LiveData, Библиотека Жизненный цикл класс для наблюдения данных, решает эту проблему. Используйте возвращаемое значение типа LiveDataв описании метода, и Room генерирует весь необходимый код для обновления LiveDataпри обновлении базы данных.

Примечание: если вы используете LiveDataнезависимо от Room, вам придется управлять обновлением данных. LiveDataне имеет общедоступных методов обновления хранимых данных.

Если вы хотите обновить данные, хранящиеся в LiveData, вы должны использовать MutableLiveDataвместо LiveData. MutableLiveDataКласс имеет два открытых методов , которые позволяют установить значение LiveDataобъекта, setValue(T)и postValue(T). Обычно MutableLiveDataиспользуется внутри ViewModel, а затем ViewModelтолько выставляет неизменные LiveDataобъекты наблюдателям.

В WordDao, измените getAlphabetizedWords()сигнатуру метода так, чтобы возвращаемый List<Word>был обернут LiveData.

   @Query("SELECT * from word_table ORDER BY word ASC")
   fun getAlphabetizedWords(): LiveData<List<Word>>
Далее в этой кодовой метке вы отслеживаете изменения данных с помощью Observerin MainActivity.

См. LiveDataДокументацию, чтобы узнать больше о других способах использования LiveData, или посмотрите следующие компоненты архитектуры: LiveData и Lifecycle .

8. Добавить базу данных номеров
Что такое база данных комнат ?
Room - это слой базы данных поверх базы данных SQLite.
Комната заботится о мирских задачах, которые вы использовали для решения SQLiteOpenHelper.
Room использует DAO для выдачи запросов в свою базу данных.
По умолчанию, чтобы избежать плохой производительности пользовательского интерфейса, Room не позволяет создавать запросы в главном потоке. Когда возвращаются запросы Room LiveData, запросы автоматически выполняются асинхронно в фоновом потоке.
Room предоставляет проверки во время компиляции операторов SQLite.
Реализовать базу данных комнат
Ваш класс базы данных Room должен быть абстрактным и расширенным RoomDatabase. Обычно вам нужен только один экземпляр базы данных Room для всего приложения.

Давайте сделаем один сейчас.

Создайте файл класса Kotlin с именем WordRoomDatabaseи добавьте в него следующий код:
// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

   abstract fun wordDao(): WordDao

   companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time. 
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java, 
                        "word_database"
                    ).build()
                INSTANCE = instance
                return instance
            }
        }
   }
}
Давайте пройдемся по коду:

Класс базы данных для комнаты должен быть abstractи расширятьсяRoomDatabase
Вы аннотируете класс как базу данных Room @Databaseи используете параметры аннотации для объявления сущностей, принадлежащих базе данных, и установки номера версии. Каждый объект соответствует таблице, которая будет создана в базе данных. Миграция базы данных выходит за рамки этой кодовой метки, поэтому мы установили exportSchemaздесь значение false, чтобы избежать предупреждения о сборке. В реальном приложении вы должны установить каталог для Room, который будет использоваться для экспорта схемы, чтобы вы могли проверить текущую схему в вашей системе контроля версий.
База данных предоставляет DAO через абстрактный метод "getter" для каждого @Dao.
Мы определили синглтон , WordRoomDatabase,чтобы предотвратить одновременное открытие нескольких экземпляров базы данных.
getDatabaseвозвращает синглтон. Он создаст базу данных при первом обращении к ней, используя построитель базы данных Room, чтобы создать RoomDatabaseобъект в контексте приложения из WordRoomDatabaseкласса и присвоить ему имя "word_database".
Примечание. При изменении схемы базы данных вам необходимо обновить номер версии и определить стратегию миграции.

Для примера может быть достаточно стратегии уничтожения и повторного создания. Но для реального приложения вы должны реализовать стратегию миграции. См. Понимание миграций с Room .

В Android Studio, если вы получаете ошибки при вставке кода или в процессе сборки, выберите « Сборка»> «Очистить проект» . Затем выберите « Построить»> «Перестроить проект» , а затем снова выполните сборку. Если вы используете предоставленный код, не должно быть ошибок в тех местах, где вы получили указание создать приложение, но между ними могут быть ошибки.

9. Создайте репозиторий
Что такое репозиторий?
Класс репозитория абстрагирует доступ к нескольким источникам данных. Хранилище не является частью библиотек компонентов архитектуры, но является рекомендуемой наилучшей практикой для разделения кода и архитектуры. Класс Repository предоставляет чистый API для доступа к данным для остальной части приложения.



Зачем использовать репозиторий?
Репозиторий управляет запросами и позволяет использовать несколько бэкэндов. В наиболее распространенном примере репозиторий реализует логику для принятия решения о том, получать ли данные из сети или использовать результаты, кэшированные в локальной базе данных.

Реализация репозитория
Создайте файл класса Kotlin с именем WordRepositoryи вставьте в него следующий код:

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()
 
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}
Основные вынос:

DAO передается в конструктор хранилища в отличие от всей базы данных. Это потому, что ему нужен только доступ к DAO, поскольку DAO содержит все методы чтения / записи для базы данных. Нет необходимости выставлять всю базу данных в хранилище.
Список слов является публичной собственностью. Инициализируется путем получения LiveDataсписка слов из комнаты; мы можем сделать это из-за того, как мы определили getAlphabetizedWordsметод для возврата LiveDataна шаге «Класс LiveData». Комната выполняет все запросы в отдельном потоке. Тогда наблюдаемый LiveDataуведомит наблюдателя в главном потоке, когда данные изменились.
suspendМодификатор указывает компилятору , что это должен быть вызван из сопрограммы или другой функции подвешенной.
Репозитории предназначены для посредничества между различными источниками данных. В этом простом примере у вас есть только один источник данных, поэтому репозиторий мало что делает. Посмотрите BasicSample для более сложной реализации.

10. Создайте ViewModel
Что такое ViewModel?
ViewModelРоль»является предоставление данных в пользовательский интерфейс и выжить изменения конфигурации. A ViewModelдействует как центр связи между хранилищем и пользовательским интерфейсом. Вы также можете использовать ViewModelдля обмена данными между фрагментами. ViewModel является частью библиотеки жизненного цикла .



Для вводного руководства по этой теме см ViewModel Overviewили в ViewModels: Простой пример блога.

Зачем использовать ViewModel?
A ViewModelхранит данные пользовательского интерфейса вашего приложения с учетом жизненного цикла, который выдерживает изменения конфигурации. Разделение данных пользовательского интерфейса своего приложения из ваших Activityи Fragmentклассов позволяет лучше следовать единому принципу ответственности: Ваша деятельность и фрагменты отвечают за составление данных на экран, в то время как ваши ViewModelможет позаботиться о проведении и обработки всех данных , необходимых для пользовательского интерфейса.

В ViewModel, используйте LiveDataдля изменяемых данных, которые пользовательский интерфейс будет использовать или отображать. Использование LiveDataимеет несколько преимуществ:

Вы можете поместить наблюдателя в данные (вместо опроса изменений) и обновлять
пользовательский интерфейс только тогда, когда данные действительно изменяются.
Репозиторий и пользовательский интерфейс полностью разделены ViewModel.
Нет вызовов базы данных из ViewModel(это все обрабатывается в репозитории), что делает код более тестируемым.
viewModelScope
В Котлине все сопрограммы работают внутри CoroutineScope. Область действия контролирует время жизни сопрограмм через свою работу. При отмене задания области действия отменяются все сопрограммы, запущенные в этой области.

Библиотека AndroidX lifecycle-viewmodel-ktxдобавляет viewModelScopeв качестве функции расширения ViewModelкласса, что позволяет вам работать с областями действия.

Чтобы узнать больше о работе с сопрограммами в ViewModel, ознакомьтесь с Шагом 5 из Использование сопрограмм Kotlin в кодовой метке приложения Android или Easy Coroutines в Android: пост поста ViewModelScope .

Реализуйте ViewModel
Создайте файл класса Kotlin для WordViewModelи добавьте в него этот код:

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}
Здесь мы имеем:

Создан класс с именем, WordViewModelкоторый получает в Applicationкачестве параметра и расширяет AndroidViewModel.
Добавлена ​​закрытая переменная-член для хранения ссылки на хранилище.
Добавлена ​​открытая LiveDataпеременная-член для кэширования списка слов.
Создан initблок, который получает ссылку на WordDaoиз WordRoomDatabase.
В initблоке, построенном на WordRepositoryоснове WordRoomDatabase.
В initблоке инициализировали allWordsLiveData с помощью репозитория.
Создан insert()метод- обертка, который вызывает метод репозитория insert(). Таким образом, реализация insert()инкапсулируется из пользовательского интерфейса. Мы не хотим, чтобы вставка блокировала основной поток, поэтому мы запускаем новую сопрограмму и вызываем вставку репозитория, которая является функцией приостановки. Как уже упоминалось, ViewModels имеют сопрограммированную область видимости, основанную на их жизненном цикле viewModelScope, который мы здесь используем.
Предупреждение : не сохраняйте ссылку на контекст, который имеет более короткий жизненный цикл, чем ваша ViewModel! Примеры:

Деятельность
Фрагмент
Посмотреть
Сохранение ссылки может привести к утечке памяти, например, ViewModel имеет ссылку на уничтоженную активность! Все эти объекты могут быть уничтожены операционной системой и воссозданы при изменении конфигурации, и это может происходить много раз в течение жизненного цикла модели представления.

Если вам нужен контекст приложения (жизненный цикл которого длится столько же, сколько и приложение), используйте AndroidViewModel, как показано в этом коде.

Важно:ViewModel не переживайте процесс приложения, убиваемый в фоновом режиме, когда ОС требуется больше ресурсов. Для данных пользовательского интерфейса, которые должны пережить смерть процесса из-за нехватки ресурсов, вы можете использовать модуль « Сохраненное состояние» для ViewModels . Узнайте больше здесь .

Чтобы узнать больше о ViewModelклассах, посмотрите видео Android Jetpack: ViewModel .

Чтобы узнать больше о сопрограммах, посмотрите кодовую метку Coroutines .

11. Add XML layout
Next, you need to add the XML layout for the list and items.

This codelab assumes that you are familiar with creating layouts in XML, so we are just providing you with the code.

Make your application theme material by setting the AppTheme parent to Theme.MaterialComponents.Light.DarkActionBar. Add a style for list items in values/styles.xml:

<resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.MaterialComponents.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>

    <!-- The default font for RecyclerView items is too small.
    The margin is a simple delimiter between the words. -->
    <style name="word_title">
        <item name="android:layout_width">match_parent</item>
        <item name="android:layout_marginBottom">8dp</item>
        <item name="android:paddingLeft">8dp</item>
        <item name="android:background">@android:color/holo_orange_light</item>
        <item name="android:textAppearance">@android:style/TextAppearance.Large</item>
    </style>
</resources>
Create a new dimension resource file:

Click the app module in the Project window.
Select File > New > Android Resource File
From the Available Qualifiers, select Dimension
Set the file name: dimens


Add this dimension resources in values/dimens.xml:

<dimen name="big_padding">16dp</dimen>
Add a layout/recyclerview_item.xml layout:

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" 
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/textView"
        style="@style/word_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/holo_orange_light" />
</LinearLayout>
In layout/activity_main.xml, replace the TextView with a RecyclerView and add a floating action button (FAB). Now your layout should look like this:

<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        tools:listitem="@layout/recyclerview_item"
        android:padding="@dimen/big_padding"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:contentDescription="@string/add_word"/>

</androidx.constraintlayout.widget.ConstraintLayout>
Your FAB's appearance should correspond to the available action, so we will want to replace the icon with a '+' symbol.

First, we need to add a new Vector Asset:

Select File > New > Vector Asset.
Click the Android robot icon in the Clip Art: field.

Search for "add" and select the '+' asset. Click OK.

After that, click Next.

Confirm the icon path as main > drawable and click Finish to add the asset. 
Still in layout/activity_main.xml, update the FAB to include the new drawable:
<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:contentDescription="@string/add_word"
        android:src="@drawable/ic_add_black_24dp"/>
        

12. Добавьте RecyclerView
Вы собираетесь отобразить данные в a RecyclerView, что немного лучше, чем просто выбросить данные в a TextView. Это codelab предполагает , что вы знаете , как RecyclerView, RecyclerView.LayoutManager, RecyclerView.ViewHolderи RecyclerView.Adapterработу.

Обратите внимание, что wordsпеременная в адаптере кэширует данные. В следующей задаче вы добавите код, который автоматически обновляет данные.

Создайте файл класса Kotlin для WordListAdapterэтого расширяет RecyclerView.Adapter. Вот код:

class WordListAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Word>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.word
    }

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}
Добавьте RecyclerViewв onCreate()метод MainActivity.

В onCreate()методе после setContentView:

   val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
   val adapter = WordListAdapter(this)
   recyclerView.adapter = adapter
   recyclerView.layoutManager = LinearLayoutManager(this)
Запустите приложение, чтобы убедиться, что все работает. Там нет элементов, потому что вы еще не подключили данные.

13. Заполните базу данных
В базе данных нет данных. Вы добавите данные двумя способами: добавьте некоторые данные при открытии базы данных и добавьте Activityдля добавления слов.

Для удаления всего содержимого и повторного заполнения базы данных при каждом запуске приложения вы создаете RoomDatabase.Callbackи переопределяете onOpen(). Поскольку вы не можете выполнять операции с базой данных Room в потоке пользовательского интерфейса, onOpen()запускается сопрограмма в диспетчере ввода-вывода.

Примечание. Если вы хотите заполнить базу данных только при первом запуске приложения, вы можете переопределить onCreate()метод в RoomDatabase.Callback.

Для запуска сопрограммы нам нужен CoroutineScope. Обновите getDatabaseметод WordRoomDatabaseкласса, чтобы также получить область сопрограммы в качестве параметра:

fun getDatabase(
       context: Context,
       scope: CoroutineScope
  ): WordRoomDatabase {
...
}
Обновите инициализатор поиска базы данных в initблоке, WordViewModelчтобы также передать область:

val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
В WordRoomDatabase, мы создаем пользовательскую реализацию RoomDatabase.Callback(), которая также получает CoroutineScopeпараметр конструктора. Затем мы переопределяем onOpenметод для заполнения базы данных.

Вот код для создания обратного вызова в пределах от WordRoomDatabaseкласса:

private class WordDatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        INSTANCE?.let { database ->
            scope.launch {
                populateDatabase(database.wordDao())
            }
        }
    }

    suspend fun populateDatabase(wordDao: WordDao) {
        // Delete all content here.
        wordDao.deleteAll()

        // Add sample words.
        var word = Word("Hello")
        wordDao.insert(word)
        word = Word("World!")
        wordDao.insert(word)

        // TODO: Add your own words!
    }
}
Наконец, добавьте обратный вызов к последовательности построения базы данных перед вызовом .build()на Room.databaseBuilder():

.addCallback(WordDatabaseCallback(scope))
Вот как должен выглядеть окончательный код:

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

   abstract fun wordDao(): WordDao

   private class WordDatabaseCallback(
       private val scope: CoroutineScope
   ) : RoomDatabase.Callback() {

       override fun onOpen(db: SupportSQLiteDatabase) {
           super.onOpen(db)
           INSTANCE?.let { database ->
               scope.launch {
                   var wordDao = database.wordDao()

                   // Delete all content here.
                   wordDao.deleteAll()

                   // Add sample words.
                   var word = Word("Hello")
                   wordDao.insert(word)
                   word = Word("World!")
                   wordDao.insert(word)

                   // TODO: Add your own words!
                   word = Word("TODO!")
                   wordDao.insert(word)
               }
           }
       }
   }

   companion object {
       @Volatile
       private var INSTANCE: WordRoomDatabase? = null

       fun getDatabase(
           context: Context,
           scope: CoroutineScope
       ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        "word_database"
                )
                 .addCallback(WordDatabaseCallback(scope))
                 .build()
                INSTANCE = instance
                // return instance
                instance
        }
     }
   }
}

14. Добавить NewWordActivity
Добавьте эти строковые ресурсы в values/strings.xml:

<string name="hint_word">Word...</string>
<string name="button_save">Save</string>
<string name="empty_not_saved">Word not saved because it is empty.</string>
Добавьте этот цветовой ресурс в value/colors.xml:

<color name="buttonLabel">#FFFFFF</color>
Добавьте min_heightресурс измерения в values/dimens.xml:

<dimen name="min_height">48dp</dimen>
Создайте новый пустой Android Activityс шаблоном Empty Activity:

Выберите « Файл»> «Создать»> «Активность»> «Пустая активность».
Введите NewWordActivityдля имени деятельности.
Убедитесь, что новое действие было добавлено в манифест Android.
<activity android:name=".NewWordActivity"></activity>
Обновите activity_new_word.xmlфайл в папке макета с помощью следующего кода:

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <EditText
        android:id="@+id/edit_word"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:minHeight="@dimen/min_height"
        android:fontFamily="sans-serif-light"
        android:hint="@string/hint_word"
        android:inputType="textAutoComplete"
        android:layout_margin="@dimen/big_padding"
        android:textSize="18sp" />

    <Button
        android:id="@+id/button_save"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/colorPrimary"
        android:text="@string/button_save"
        android:layout_margin="@dimen/big_padding"
        android:textColor="@color/buttonLabel" />

</LinearLayout>
Обновите код для действия:

class NewWordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}

15. Связаться с данными
Последний шаг - подключение интерфейса пользователя к базе данных путем сохранения новых слов, которые вводит пользователь, и отображения текущего содержимого базы данных слов в RecyclerView.

Чтобы отобразить текущее содержимое базы данных, добавьте наблюдателя, который наблюдает за LiveDataв ViewModel.

Всякий раз, когда данные изменяются, onChanged()вызывается обратный вызов, который вызывает метод адаптера setWords()для обновления кэшированных данных адаптера и обновления отображаемого списка.

В MainActivity, создайте переменную-член для ViewModel:

private lateinit var wordViewModel: WordViewModel
Используйте, ViewModelProviderчтобы связать ваш ViewModelс вашим Activity.

Когда ваш Activityпервый запуск, ViewModelProvidersсоздаст ViewModel. Когда действие уничтожается, например, из-за изменения конфигурации, оно ViewModelсохраняется. Когда деятельность воссоздается, ViewModelProvidersвозвращаются существующие ViewModel. Для получения дополнительной информации см ViewModel.

В onCreate()ниже RecyclerViewблока кода, получить ViewModelот ViewModelProvider:

wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
Также onCreate()добавьте наблюдателя для LiveDataсвойства allWords из WordViewModel.

onChanged()Метод (метод по умолчанию для нашего Lambda) срабатывает , когда наблюдаемые изменения данных и активность находится на переднем плане:

wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setWords(it) }
})
Мы хотим открыть NewWordActivityпри нажатии на FAB и, как только мы вернемся в MainActivity, чтобы вставить новое слово в базу данных или показать Toast. Для этого давайте начнем с определения кода запроса:

private val newWordActivityRequestCode = 1
В MainActivity, добавьте onActivityResult()код для NewWordActivity.

Если действие возвращается с RESULT_OK, вставьте возвращенное слово в базу данных, вызвав insert()метод WordViewModel:

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
        data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
            val word = Word(it)
            wordViewModel.insert(word)
        }
    } else {
        Toast.makeText(
            applicationContext,
            R.string.empty_not_saved,
            Toast.LENGTH_LONG).show()
    }
}
В MainActivity,начале, NewWordActivityкогда пользователь нажимает FAB. В MainActivity onCreate, найдите FAB и добавьте onClickListenerс этим кодом:

val fab = findViewById<FloatingActionButton>(R.id.fab)
fab.setOnClickListener {
  val intent = Intent(this@MainActivity, NewWordActivity::class.java)
  startActivityForResult(intent, newWordActivityRequestCode)
}
Ваш готовый код должен выглядеть так:

class MainActivity : AppCompatActivity() {

   private val newWordActivityRequestCode = 1
   private lateinit var wordViewModel: WordViewModel

   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

       val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
       val adapter = WordListAdapter(this)
       recyclerView.adapter = adapter
       recyclerView.layoutManager = LinearLayoutManager(this)

       wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
       wordViewModel.allWords.observe(this, Observer { words ->
           // Update the cached copy of the words in the adapter.
           words?.let { adapter.setWords(it) }
       })

       val fab = findViewById<FloatingActionButton>(R.id.fab)
       fab.setOnClickListener {
           val intent = Intent(this@MainActivity, NewWordActivity::class.java)
           startActivityForResult(intent, newWordActivityRequestCode)
       }
   }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)

       if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
           data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
               val word = Word(it)
               wordViewModel.insert(word)
           }
       } else {
           Toast.makeText(
               applicationContext,
               R.string.empty_not_saved,
               Toast.LENGTH_LONG).show()
       }
   }
}
Теперь запустите ваше приложение! Когда вы добавляете слово в базу данных NewWordActivity, пользовательский интерфейс будет автоматически обновляться.

16. Резюме
Теперь, когда у вас есть работающее приложение, давайте вспомним, что вы создали. Вот снова структура приложения:



Компоненты приложения:

MainActivity: отображает слова в списке, используя a RecyclerViewи WordListAdapter. В MainActivity, есть, Observerкоторый наблюдает слова LiveData из базы данных и уведомляется, когда они изменяются.
NewWordActivity: добавляет новое слово в список.
WordViewModel: предоставляет методы для доступа к слою данных и возвращает LiveData, чтобы MainActivity могла установить отношение наблюдателя. *
LiveData<List<Word>>: Делает возможным автоматическое обновление компонентов UI. В MainActivity, есть, Observerкоторый наблюдает слова LiveData из базы данных и уведомляется, когда они изменяются.
Repository:управляет одним или несколькими источниками данных. Эти Repositoryметоды выставляют на модель представления для взаимодействия с основным поставщиком данных. В этом приложении этот бэкэнд представляет собой базу данных Room.
Room: является оберткой и реализует базу данных SQLite. Комната делает для вас много работы, которую вы должны были делать сами.
DAO: сопоставляет вызовы метода с запросами к базе данных, так что когда хранилище вызывает метод, такой как getAlphabetizedWords(), Room может выполняться .SELECT * from word_table ORDER BY word ASC
Word: класс сущности, содержащий одно слово.
* Viewsи Activities(и Fragments) взаимодействуют только с данными через ViewModel. Таким образом, не имеет значения, откуда поступают данные.

Поток данных для автоматического обновления пользовательского интерфейса (Reactive UI)
Автоматическое обновление возможно, потому что мы используем LiveData. В MainActivity, есть, Observerкоторый наблюдает слова LiveData из базы данных и уведомляется, когда они изменяются. Когда есть изменение, метод наблюдателя onChange()выполняется и обновляется mWordsв WordListAdapter.

Данные можно наблюдать, потому что это так LiveData. И то , что наблюдается это , LiveData<List<Word>>что возвращается в WordViewModelвиде llWordsсобственности.

WordViewModelСкрывает все о внутреннем интерфейсе из слоя пользовательского интерфейса. Он предоставляет методы для доступа к уровню данных и возвращает его LiveDataтак, чтобы MainActivityможно было установить отношение наблюдателя. Viewsи Activities(и Fragments) взаимодействовать только с данными через ViewModel. Таким образом, не имеет значения, откуда поступают данные.

В этом случае данные поступают из Repository. ViewModelНе нужно знать , что это Repository взаимодействует с. Нужно просто знать, как взаимодействовать с ним Repository, используя методы, предоставляемые Repository.

Репозиторий управляет одним или несколькими источниками данных. В WordListSampleприложении этот бэкэнд представляет собой базу данных Room. Room представляет собой оболочку и реализует базу данных SQLite. Комната делает для вас много работы, которую вы должны были делать сами. Например, Room делает все, что вы делали с SQLiteOpenHelperклассом.

DAO сопоставляет вызовы метода с запросами к базе данных, так что когда хранилище вызывает метод, такой как getAllWords(), Room может выполняться .SELECT * from word_table ORDER BY word ASC

Поскольку наблюдаются результат возвращается из запроса LiveData, каждый раз , когда данные в изменении комнаты, Observerинтерфейс onChanged()метод выполняется и пользовательский интерфейс обновляется.


17. Поздравляем!
[Необязательно] Загрузите код решения
Если вы еще этого не сделали, вы можете взглянуть на код решения для кодовой метки. Вы можете посмотреть репозиторий github или скачать код здесь:


Распакуйте загруженный почтовый файл. Это позволит распаковать корневую папку android-room-with-a-view-kotlin, в которой содержится полное приложение.

Код решения включает в себя модульные тесты для базы данных Room. Тестирование выходит за рамки этой кодовой метки. Посмотрите на код, если вы заинтересованы.

Если вам нужно перенести приложение, см. 7 шагов в комнату после успешного завершения этой кодовой метки. Обратите внимание, что невероятно приятно удалить ваш SQLiteOpenHelperкласс и много другого кода.

Если у вас много данных, подумайте об использовании библиотеки подкачки . Пейджинговая кодовая метка здесь .

Больше на Архитектуре, Комнате, LiveData и ViewModel

Руководство по архитектуре приложения
Обзор архитектуры Android (видео)
Android Жизненный цикл-зависимые компоненты codelab ( ViewModel, LiveData, LifecycleOwner, LifecycleRegistryOwner)
Примеры кода компонента архитектуры
Разработка приложений для Android с помощью бесплатного онлайн-обучения Kotlin - включает уроки по ViewModel, LiveData, Room, классу репозитория и многим другим с примерами кода.
Кодовые метки других компонентов архитектуры

Связывание с данными Codelab - Удалите еще больше кода из ваших действий и фрагментов; прекрасно работает с ViewModel и LiveData
Paging Codelab - просматривайте огромные списки данных из комнаты
Навигационная кодовая метка - управление навигацией в приложении с использованием компонента навигации и инструментов
WorkManager Codelab - эффективно выполняйте работу, когда ваше приложение работает в фоновом режиме



        