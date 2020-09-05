/*
 * Copyright (C) 2017 The Android Open Source Project
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

package paging.android.example.com.pagingsample

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.*
import android.content.Context

/**
 * Singleton database object. Note that for a real app, you should probably use a Dependency
 * Injection framework or Service Locator to create the singleton database.
 * Одноэлементный объект базы данных. Обратите внимание, что для реального приложения вы, вероятно, должны использовать зависимость
 * Платформа инъекций или локатор служб для создания одноэлементной базы данных.
 *  * ОК Все стандартно
 */
@Database(entities = [Cheese::class], version = 1)
abstract class CheeseDb : RoomDatabase() {
    abstract fun cheeseDao(): CheeseDao

    companion object {
        private var instance: CheeseDb? = null
        @Synchronized
        fun get(context: Context): CheeseDb {
            if (instance == null) {
                instance = Room.inMemoryDatabaseBuilder(context.applicationContext,
                        CheeseDb::class.java)  //, "CheeseDatabase")
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                fillInDb(context.applicationContext)
                            }
                        }).build()
            }
            return instance!!
        }

        /**
         * fill database with list of cheeses
         * заполните базу данных списком сыров
         */
        private fun fillInDb(context: Context) {
            // inserts in Room are executed on the current thread, so we insert in the background
            // вставки в комнате выполняются на текущем потоке, поэтому мы вставляем в фоновом режиме
            ioThread {
                get(context).cheeseDao().clear()
                get(context).cheeseDao().insert(
                        CHEESE_DATA.map { Cheese(id = 0, name = it) })
            }
        }
    }
}


private val CHEESE_DATA = arrayListOf(
        "Аббатство де Беллок", "Аббатство дю-Мон-де-кошки", "Abertam", "Абонданс", "Ackawi",
        "Желудь", "Adelost", "Affidelice АУ Шабли", "Afuega международный Питу", "айран", "эрдельтерьер",
        "Aisy Cendre", "Око Эмменталер", "Алверка", "Амбер", "Американский Сыр",
        "АМИ дю шамбертен", "Аньехо Enchilado", "номера-дю-Вик-Bilh", "Anthoriro", "Аппенцелль",
        "Арагон", "Арди Gasna", "Ардрахан", "армянский струнный", "аром ген АУ-де-Марк",
        "Asadero", "Азиаго", "Обиск Пиренеях", "Отон", "Avaxtskyr", "Детские Швейцарские",
        "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
        "Барри Бей Чеддер", "основывать", "Сырная корзинка", "творожная ванна", "Баварский Bergkase",
        "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
        "Bergader", "Достопримечательности Города", "Берксвэлл", "Отель Beyaz Пейнир", "Bierkase", "Епископ Кеннеди",
        "Блэрни", "Бле д'Овернь", "Бле-де-Жекс", "Bleu де Laqueuille",
        "Bleu де Septmoncel", "Бле де Косс", "синий", "голубой замок", "синий Rathgore",
        "Голубые Вены (Австралийская)", "Синяя Вена Сыры", "Bocconcini", "Bocconcini (Австралии)",
        "Boeren Leidenkaas", "Бончестер", "Босворт", "Некрополя", "Буль-Дю Ровниц",
        "Boulette d'Avesnes", "Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
        "Завтрак сыр", "Brebis дю Lavort", "Brebis дю Lochois", "Brebis дю Puyfaucon",
        "Bresse Bleu", "Brick", "Brie", "Brie de Meaux", "Brie de Melun", "Brillat-Savarin",
        "Брин", "Брин д'Амур", "Брин д'Амур", "Бринза (Бурдуф Бринза)",
        "Брикет-де-Brebis", "брикет-дю-Форе", "Broccio", "Broccio Деми-аффинных",
        "Brousse-дю-Рув", "брат Василий", "Brusselae Каас (Фромаж де Брюссель)", "брынза",
        "Отверстий д'Анжу", "Баффало", "Бургос", "бьют", "Butterkase", "кнопка (Иннес)",
        "Бакстон Блю", "Кабеку", "Кабок", "Кабралес", "Кашай", "Качокавалло", "Качотта",
        "Кэрфилли", "Кэрнсмор", "Калензана", "Камбазола", "Камамбер де Нормандия",
        "Канадский Чеддер", "Канестрато", "Канталь", "каприз де Дье", "Козерог козел",
        "Каприоль Банон", "каре де л'Эст", "Casciotta Урбино", "Кашел синий", "Кастеллано",
        "Кастельено", "Кастельманьо", "Кастело Бранко", "Кастильяно", "Кателайн",
        "Кельтское обещание", "Cendre d'Olivet", "Cerney", "Chabichou", "Chabichou du Poitou",
        "Chabis де Гатинэ", "шаурс", "Шароле", "Шом", "Чеддер",
        "Cheddar Clothbound", "Cheshire", "Chevres", "Chevrotin des Aravis", "Chontaleno",
        "Городке сивре", "Кер де Камамбер АУ кальвадос", "Кер де шевр", "Колби", "холодный пакет",
        "Comte", "Coolea", "Cooleney", "Coquetdale", "Corleggy", "Cornish Pepper",
        "Cotherstone", "Контиха -", "Творог", "Творог (Австралии)",
        "Пантера золото", "Куломье", "Ковердейл", "Crayeux-де-Ронк", "сливочный сыр ",
        "Хаварти Сливки", "Пенки Агрия", "Крема Мексикана", "Сметана", "Crescenza",
        "Кроган", "Кроттен де Шавиньоль", "Кроттен дю Шавиньоль", "Крауди", "Кроули",
        "Cuajada", "Curd", "Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
        "Кипарис Шевре Роща", "Danablu (Данаблю)", "Danbo", "Датский Фонтина",
        "Даралагъязский", "Дофин", "Delice des Fiouves", "Denhany Dorset Drum", "дерби",
        "Десертный Белый", "Девон Блю", "Девон Гарленд", "Долселатт", "Дулин",
        "Doppelrhamstufel", "Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
        "Дре Ла Фейе", "Драй Джек", "Duddleswell", "Dunbarra", "Данлоп", "Dunsyre синий",
        "Duroblando", "Баллинскеллигс", "Голландский Mimolette (Commissiekaas)", "Эдам", "Edelpilz",
        "Эменталь Гран Крю", "Emlett", "Эмменталь", "Epoisses де Бургонь", "Esbareich",
        "Эсром", "Эторки", "Эвансдейл Фарм Бри", "Эвора Де Л'Алентежу", "Эксмур Блю",
        "Эксплоратор", "брынза", "фета (австралийская)", "Фигуй", "Витербо", "Фин-де-Сьекль",
        "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis", "Flor de Guia",
        "Цветочная Мари", "сложенный", "сложенный сыр с мятой", "помадка де Бребис",
        "Фонтенбло", "Фонталь", "Фонтина Валь д'Аоста", "Формаджо Ди Капра", "фужер",
        "Четыре герба Гауда", "четыре амбры", "четыре Луары", "четыре Монбризона",
        "Свежий Джек", "Свежая Моцарелла", "Свежая Рикотта", "Свежие Трюфели", "Фрибуржуа",
        "Friesekaas", "фризе", "Friesla", "Frinault", "Фромаж Раклет", "Фромаж Корс",
        "Fromage de Montagne de Savoie", "Fromage Frais", "фруктовый сливочный сыр",
        "Жарки Сыр", "Финбо", "Габриэль", "Галетт дю Paludier", "Лионез Галетт ",
        "Драгоценные камни Галлоуэй молоко козье", "Gammelost", "Gaperon а л Ай", "Гарроча", "Gastanberra",
        "Гейтост", "Гипсленд Блю", "Джетост", "Глостер", "Золотой Крест", "Горгонзола",
        "Gornyaltajski", "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
        "Графтон Виллидж Чеддер", "Грана", "Грана Падано", "Гранд Ватель",
        "Городе ареше Grataron д'", "район небоскребов Пайе", "Гравьера", "Greuilh", "Греве",
        "Гри де Лилль", "Грюйер", "Губбен", "Гербиньи", "халлуми",
        "Halloumy (Australian)", "Haloumi-Style Cheese", "Harbourne Blue", "Havarti",
        "Хайди Грюйер", "Герефорд-Хоп", "Herrgardsost", "Хэрриот Дом", "Эрве",
        "Хипи-Ити", "Hubbardston Синяя Корова", "Hushallsost", "Иберико", "Айдахо Goatster",
        "Стрелками", "Иль Боскетто Аль трюфелями", "Йе", "остров Малл", "сыр",
        "Торты Jermi", "Jibneh Arabieh", "Джинди Бри", "Юбилейный-Синий", "Juustoleipa",
        "Kadchgall", "Kaseri", "Kashta", "Kefalotyri", "Kenafa", "Kernhem", "Kervella Affine",
        "Kikorangi", "Кинг-Айленд-Кейп-Уикхем Бри", "Король Золотой Реки", "Klosterkaese",
        "Knockalara", "Kugelkase", "L'Aveyronnais", "L'ECIR de l'Aubrac", "La Taupiniere",
        "Ла Ваче Квай Рит", "Дополнительная Информация", "Lairobell", "Лайта", "Ланарк Синий", "Ланкашир",
        "Лангр", "Лаппи", "Ларенс", "Lavistown", "Ле Брин", "Ле Ulim-Е Орбо", "Ле Lacandou",
        "Ле Руль", "Лифилд", "Леббене", "Леердаммер", "Лестер", "Лейден", "Лимбургер",
        "Линкольнширский браконьер", "Лингот Санкт-Буске-д'Рокфор и виадука Мийо", "Liptauer", "маленькая Rydings",
        "Ливаро", "Llanboidy", "Llanglofan Дом", "Лох-Артур Хутор",
        "Loddiswell, Так Авондейл", "Дровосеки", "Палоу Лу", "Лу Pevre", "Лион", "Маасдам",
        "Macconais", "Махо В Возрасте Гауда", "Махон", "Малверн", "Mamirolle", "Манчего",
        "Манури", "Манур", "Мраморный Чеддер", "Мраморные Сыры", "Маредсус", "Марготин",
        "Maribo", "Maroilles", "Mascares", "Mascarpone", "Mascarpone (Австралийский)",
        "Маскарпоне Торта", "Маток", "Майтаг Блю", "Мейра", "Меналлак Фарм",
        "Menonita", "Meredith Blue", "Mesost", "Metton (Cancoillotte)", "Meyer Vintage Gouda",
        "Mihalic Пейнир", "Milleens", "Mimolette", "Шахта-Gabhar", "Мини Детские Колокольчики", "Микст",
        "Мольбо", "монастырские сыры", "Мондсир", "Мон Д'ор Лион", "монтазио",
        "Монтерей Джек", "Монтерей Джек сухой", "Морбьер", "Морбьер Крю-де-Монтань",
        "Mothais a la Feuille", "Моцарелла", "Моцарелла (австралийская)",
        "Моцарелла ди буфала", "Моцарелла свежая, в воде", "булочки с моцареллой", "Мюнстер",
        "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais", "Neufchatel",
        "Neufchatel (Australian)", "Niolo", "Nokkelost", "Northumberland", "Oaxaca",
        "Олд-Йорк", "Оливе АУ Фуэн", "Оливе Блю", "Оливе Cendre",
        "Оркнейский Экстра Зрелый Чеддер", "Орла", "Ощтепка", "Оссау Фермье", "Оссау-Ираты",
        "Ощипек", "Оксфорд Блю", "П'Тит Берришон", "Пале де Баблиньи", "панир", "Панела",
        "Pannerone", "трусов Ю. С. Gawn", "Пармезан (Пармиджано)", "Пармиджано Реджано",
        "Па-де-де Вам", "Passendale", "обработанного жидкого обработанного", "Пате-де-Фромаж",
        "Patefine Форт", "Паве д'Affinois", "Паве д'ОЖ", "Паве-де Ширак", "Паве-дю Берри",
        "Пекорино", "пекорино в листьях грецкого ореха", "Пекорино Романо", "пирамида Пикскилла",
        "Pelardon Севенны", "Pelardon де-Корбьер", "Penamellera", "Пенбрин",
        "Pencarreg", "Perail де Brebis", "Пти-Морен", "маленький он", "Пти-Сюисс",
        "Picodon-де-Шевре", "Пикос-де-Эуропа", "Пиора", "Pithtviers АУ Фуэн",
        "Плато де Эрв", "Плимутский сыр", "Подхалански", "Пуавр д'Ане", "Полколбин",
        "Pont l'Eveque", "Port Nicholson", "Port-Salut", "Postel", "Pouligny-Saint-Pierre",
        "Pourly", "Prastost", "Pressato", "Prince-Jean", "Processed Cheddar", "Provolone",
        "Проволоне (Австралиец)", "Пиенгана Чеддер", "Пирамида", "Кварк",
        "Quark (Australian)", "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit",
        "Кесо Бланко", "кесо Бланко кон Фрутас --Пины г манго", "кесо де Мурсия",
        "Кесо дель Монсек", "кесо дель Тьетар", "кесо Фреско", "кесо Фреско (Adobera)",
        "Кесо Иберико", "Сыра И Халапеньо", "Кесо Majorero", "Кесо Медиа Луна",
        "Кесо Пункт Фриер", "Кесадилья Сыра", "Рабакал", "Раклет", "Ragusano", "Raschera",
        "Реблошон", "Ред Лестер", "Регал де ла Домб", "Реджанито", "Ремедоу",
        "Рекесон", "Ришелье", "Рикотта", "Рикотта (Австралийская)", "Рикотта Салата", "Риддер",
        "Rigotte", "Рокамадур", "Rollot", "Романо", "Римляне Пар Дье", "Ронкаль", "Рокфор",
        "Roule", "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu", "Saaland Pfarr",
        "Saanenkaese", "Сага", "Мудрец Дерби", "Сент-Мор", "Сен-Марселен",
        "Сен-Нектер", "Сен-Полен", "Салер", "Самсо", "Сан-Симон", "Сансер",
        "САП Саго", "Сардиния", "Сардо Египта", "Сбринц", "Скаморца", "Schabzieger", "Замок",
        "Сель-сюр-Шер", "Сельва", "Serat", "серьезно острый Чеддер", "Серра-да-Эштрела",

        "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
        "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
        "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
        "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
        "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
        "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
        "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
        "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
        "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
        "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
        "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
        "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
        "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)",
        "Boeren Leidenkaas", "Bonchester", "Bosworth", "Bougon", "Boule Du Roves",
        "Boulette d'Avesnes", "Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
        "Breakfast Cheese", "Brebis du Lavort", "Brebis du Lochois", "Brebis du Puyfaucon",
        "Bresse Bleu", "Brick", "Brie", "Brie de Meaux", "Brie de Melun", "Brillat-Savarin",
        "Brin", "Brin d' Amour", "Brin d'Amour", "Brinza (Burduf Brinza)",
        "Briquette de Brebis", "Briquette du Forez", "Broccio", "Broccio Demi-Affine",
        "Brousse du Rove", "Bruder Basil", "Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
        "Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase", "Button (Innes)",
        "Buxton Blue", "Cabecou", "Caboc", "Cabrales", "Cachaille", "Caciocavallo", "Caciotta",
        "Caerphilly", "Cairnsmore", "Calenzana", "Cambazola", "Camembert de Normandie",
        "Canadian Cheddar", "Canestrato", "Cantal", "Caprice des Dieux", "Capricorn Goat",
        "Capriole Banon", "Carre de l'Est", "Casciotta di Urbino", "Cashel Blue", "Castellano",
        "Castelleno", "Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
        "Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou", "Chabichou du Poitou",
        "Chabis de Gatine", "Chaource", "Charolais", "Chaumes", "Cheddar",
        "Cheddar Clothbound", "Cheshire", "Chevres", "Chevrotin des Aravis", "Chontaleno",
        "Civray", "Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby", "Cold Pack",
        "Comte", "Coolea", "Cooleney", "Coquetdale", "Corleggy", "Cornish Pepper",
        "Cotherstone", "Cotija", "Cottage Cheese", "Cottage Cheese (Australian)",
        "Cougar Gold", "Coulommiers", "Coverdale", "Crayeux de Roncq", "Cream Cheese",
        "Cream Havarti", "Crema Agria", "Crema Mexicana", "Creme Fraiche", "Crescenza",
        "Croghan", "Crottin de Chavignol", "Crottin du Chavignol", "Crowdie", "Crowley",
        "Cuajada", "Curd", "Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
        "Cypress Grove Chevre", "Danablu (Danish Blue)", "Danbo", "Danish Fontina",
        "Daralagjazsky", "Dauphin", "Delice des Fiouves", "Denhany Dorset Drum", "Derby",
        "Dessertnyj Belyj", "Devon Blue", "Devon Garland", "Dolcelatte", "Doolin",
        "Doppelrhamstufel", "Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
        "Dreux a la Feuille", "Dry Jack", "Duddleswell", "Dunbarra", "Dunlop", "Dunsyre Blue",
        "Duroblando", "Durrus", "Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz",
        "Emental Grand Cru", "Emlett", "Emmental", "Epoisses de Bourgogne", "Esbareich",
        "Esrom", "Etorki", "Evansdale Farmhouse Brie", "Evora De L'Alentejo", "Exmoor Blue",
        "Explorateur", "Feta", "Feta (Australian)", "Figue", "Filetta", "Fin-de-Siecle",
        "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis", "Flor de Guia",
        "Flower Marie", "Folded", "Folded cheese with mint", "Fondant de Brebis",
        "Fontainebleau", "Fontal", "Fontina Val d'Aosta", "Formaggio di capra", "Fougerus",
        "Four Herb Gouda", "Fourme d' Ambert", "Fourme de Haute Loire", "Fourme de Montbrison",
        "Fresh Jack", "Fresh Mozzarella", "Fresh Ricotta", "Fresh Truffles", "Fribourgeois",
        "Friesekaas", "Friesian", "Friesla", "Frinault", "Fromage a Raclette", "Fromage Corse",
        "Fromage de Montagne de Savoie", "Fromage Frais", "Fruit Cream Cheese",
        "Frying Cheese", "Fynbo", "Gabriel", "Galette du Paludier", "Galette Lyonnaise",
        "Galloway Goat's Milk Gems", "Gammelost", "Gaperon a l'Ail", "Garrotxa", "Gastanberra",
        "Geitost", "Gippsland Blue", "Gjetost", "Gloucester", "Golden Cross", "Gorgonzola",
        "Gornyaltajski", "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
        "Grafton Village Cheddar", "Grana", "Grana Padano", "Grand Vatel",
        "Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh", "Greve",
        "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny", "Halloumi",
        "Halloumy (Australian)", "Haloumi-Style Cheese", "Harbourne Blue", "Havarti",
        "Heidi Gruyere", "Hereford Hop", "Herrgardsost", "Herriot Farmhouse", "Herve",
        "Hipi Iti", "Hubbardston Blue Cow", "Hushallsost", "Iberico", "Idaho Goatster",
        "Idiazabal", "Il Boschetto al Tartufo", "Ile d'Yeu", "Isle of Mull", "Jarlsberg",
        "Jermi Tortes", "Jibneh Arabieh", "Jindi Brie", "Jubilee Blue", "Juustoleipa",
        "Kadchgall", "Kaseri", "Kashta", "Kefalotyri", "Kenafa", "Kernhem", "Kervella Affine",
        "Kikorangi", "King Island Cape Wickham Brie", "King River Gold", "Klosterkaese",
        "Knockalara", "Kugelkase", "L'Aveyronnais", "L'Ecir de l'Aubrac", "La Taupiniere",
        "La Vache Qui Rit", "Laguiole", "Lairobell", "Lajta", "Lanark Blue", "Lancashire",
        "Langres", "Lappi", "Laruns", "Lavistown", "Le Brin", "Le Fium Orbo", "Le Lacandou",
        "Le Roule", "Leafield", "Lebbene", "Leerdammer", "Leicester", "Leyden", "Limburger",
        "Lincolnshire Poacher", "Lingot Saint Bousquet d'Orb", "Liptauer", "Little Rydings",
        "Livarot", "Llanboidy", "Llanglofan Farmhouse", "Loch Arthur Farmhouse",
        "Loddiswell Avondale", "Longhorn", "Lou Palou", "Lou Pevre", "Lyonnais", "Maasdam",
        "Macconais", "Mahoe Aged Gouda", "Mahon", "Malvern", "Mamirolle", "Manchego",
        "Manouri", "Manur", "Marble Cheddar", "Marbled Cheeses", "Maredsous", "Margotin",
        "Maribo", "Maroilles", "Mascares", "Mascarpone", "Mascarpone (Australian)",
        "Mascarpone Torta", "Matocq", "Maytag Blue", "Meira", "Menallack Farmhouse",
        "Menonita", "Meredith Blue", "Mesost", "Metton (Cancoillotte)", "Meyer Vintage Gouda",
        "Mihalic Peynir", "Milleens", "Mimolette", "Mine-Gabhar", "Mini Baby Bells", "Mixte",
        "Molbo", "Monastery Cheeses", "Mondseer", "Mont D'or Lyonnais", "Montasio",
        "Monterey Jack", "Monterey Jack Dry", "Morbier", "Morbier Cru de Montagne",
        "Mothais a la Feuille", "Mozzarella", "Mozzarella (Australian)",
        "Mozzarella di Bufala", "Mozzarella Fresh, in water", "Mozzarella Rolls", "Munster",
        "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais", "Neufchatel",
        "Neufchatel (Australian)", "Niolo", "Nokkelost", "Northumberland", "Oaxaca",
        "Olde York", "Olivet au Foin", "Olivet Bleu", "Olivet Cendre",
        "Orkney Extra Mature Cheddar", "Orla", "Oschtjepka", "Ossau Fermier", "Ossau-Iraty",
        "Oszczypek", "Oxford Blue", "P'tit Berrichon", "Palet de Babligny", "Paneer", "Panela",
        "Pannerone", "Pant ys Gawn", "Parmesan (Parmigiano)", "Parmigiano Reggiano",
        "Pas de l'Escalette", "Passendale", "Pasteurized Processed", "Pate de Fromage",
        "Patefine Fort", "Pave d'Affinois", "Pave d'Auge", "Pave de Chirac", "Pave du Berry",
        "Pecorino", "Pecorino in Walnut Leaves", "Pecorino Romano", "Peekskill Pyramid",
        "Pelardon des Cevennes", "Pelardon des Corbieres", "Penamellera", "Penbryn",
        "Pencarreg", "Perail de Brebis", "Petit Morin", "Petit Pardou", "Petit-Suisse",
        "Picodon de Chevre", "Picos de Europa", "Piora", "Pithtviers au Foin",
        "Plateau de Herve", "Plymouth Cheese", "Podhalanski", "Poivre d'Ane", "Polkolbin",
        "Pont l'Eveque", "Port Nicholson", "Port-Salut", "Postel", "Pouligny-Saint-Pierre",
        "Pourly", "Prastost", "Pressato", "Prince-Jean", "Processed Cheddar", "Provolone",
        "Provolone (Australian)", "Pyengana Cheddar", "Pyramide", "Quark",
        "Quark (Australian)", "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit",
        "Queso Blanco", "Queso Blanco con Frutas --Pina y Mango", "Queso de Murcia",
        "Queso del Montsec", "Queso del Tietar", "Queso Fresco", "Queso Fresco (Adobera)",
        "Queso Iberico", "Queso Jalapeno", "Queso Majorero", "Queso Media Luna",
        "Queso Para Frier", "Queso Quesadilla", "Rabacal", "Raclette", "Ragusano", "Raschera",
        "Reblochon", "Red Leicester", "Regal de la Dombes", "Reggianito", "Remedou",
        "Requeson", "Richelieu", "Ricotta", "Ricotta (Australian)", "Ricotta Salata", "Ridder",
        "Rigotte", "Rocamadour", "Rollot", "Romano", "Romans Part Dieu", "Roncal", "Roquefort",
        "Roule", "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu", "Saaland Pfarr",
        "Saanenkaese", "Saga", "Sage Derby", "Sainte Maure", "Saint-Marcellin",
        "Saint-Nectaire", "Saint-Paulin", "Salers", "Samso", "San Simon", "Sancerre",
        "Sap Sago", "Sardo", "Sardo Egyptian", "Sbrinz", "Scamorza", "Schabzieger", "Schloss",
        "Selles sur Cher", "Selva", "Serat", "Seriously Strong Cheddar", "Serra da Estrela",
        "Sharpam", "Shelburne Cheddar", "Shropshire Blue", "Siraz", "Sirene", "Smoked Gouda",
        "Somerset Brie", "Sonoma Jack", "Sottocenare al Tartufo", "Soumaintrain",
        "Sourire Lozerien", "Spenwood", "Sraffordshire Organic", "St. Agur Blue Cheese",
        "Stilton", "Stinking Bishop", "String", "Sussex Slipcote", "Sveciaost", "Swaledale",
        "Sweet Style Swiss", "Swiss", "Syrian (Armenian String)", "Tala", "Taleggio", "Tamie",
        "Tasmania Highland Chevre Log", "Taupiniere", "Teifi", "Telemea", "Testouri",
        "Tete de Moine", "Tetilla", "Texas Goat Cheese", "Tibet", "Tillamook Cheddar",
        "Tilsit", "Timboon Brie", "Toma", "Tomme Brulee", "Tomme d'Abondance",
        "Tomme de Chevre", "Tomme de Romans", "Tomme de Savoie", "Tomme des Chouans", "Tommes",
        "Torta del Casar", "Toscanello", "Touree de L'Aubier", "Tourmalet",
        "Trappe (Veritable)", "Trois Cornes De Vendee", "Tronchon", "Trou du Cru", "Truffe",
        "Tupi", "Turunmaa", "Tymsboro", "Tyn Grug", "Tyning", "Ubriaco", "Ulloa",
        "Vacherin-Fribourgeois", "Valencay", "Vasterbottenost", "Venaco", "Vendomois",
        "Vieux Corse", "Vignotte", "Vulscombe", "Waimata Farmhouse Blue",
        "Washed Rind Cheese (Australian)", "Waterloo", "Weichkaese", "Wellington",
        "Wensleydale", "White Stilton", "Whitestone Farmhouse", "Wigmore", "Woodside Cabecou",
        "Xanadu", "Xynotyro", "Yarg Cornish", "Yarra Valley Pyramid", "Yorkshire Blue",
        "Zamorano", "Zanetti Grana Padano", "Zanetti Parmigiano Reggiano"
)
