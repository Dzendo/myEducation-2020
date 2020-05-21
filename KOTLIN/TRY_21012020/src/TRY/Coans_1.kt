package TRY
import kotlin.random.Random as KRandom
import java.util.Random as JRandom
import java.util.Calendar
//import TimeInterval.*

fun main() {
   println(useFoo())
    println(tripleQuotedString)
    val mon = 7
    println(month.substring((mon-1)*4+1,mon*4))
    println(getPattern())
    //println("13022019".getPattern())
}

fun foo(name: String, number: Int=42, toUpperCase: Boolean=false) =
    (if (toUpperCase) name.toUpperCase() else name) + number

fun useFoo() = listOf(
    foo("a"),
    foo("b", number = 1),
    foo("c", toUpperCase = true),
    foo(name = "d", number = 2, toUpperCase = true)
)

const val question = "life, the universe, and everything"
const val answer = 42

val tripleQuotedString = """
    #question = "$question"
    #answer = $answer""".trimIndent().trimMargin("#")

val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

fun getPattern(): String = """\d{2}\.\d{2}\.\d{4}"""

// надо превратить в 13 JUL 1992 String Templates
//Строки в тройных кавычках полезны не только для многострочных строк, но и для создания шаблонов регулярных выражений,
// поскольку вам не нужно экранировать обратную косую черту с помощью обратной косой черты.
//Следующий шаблон соответствует дате в формате 13.06.1992 (две цифры, точка, две цифры, точка, четыре цифры):
//fun getPattern () = "" " \d{2}\.\d{2}\.\d{4}"""
//Используя переменную month, перепишите этот шаблон таким образом,
// чтобы он соответствовал дате в формате 13 июня 1992 года (две цифры, пробел, аббревиатура месяца, пробел, четыре цифры).

//Задание в котлинланге: Используя переменную месяца перепишите этот шаблон таким образом, чтобы он соответствовал дате в формате 13 июня 1992 года (две цифры, пробел, аббревиатура месяца, пробел, четыре цифры).

val month0= "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"
fun getPattern0(): String = """\d{2} ${month0} \d{4}""" //- Я ничего не понимаю ${month}. Как это работает?

/* ${month} равняется: (JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)
Таким образом, строка """\d{2} ${month} \d{4}"""фактически расширяется до
"""\d{2} (JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC) \d{4}"""
Это регулярное выражение, которое захватывает пару чисел, за которыми следует пробел,
 затем одно из значенийJAN, FEB... DEC, за которым следует другой пробел и еще четыре цифры.
 Так что строки вроде 04 APR 1234соответствуют регулярному выражению.
 От ДО КРУТО - ответ из СтекОверфлоу
 это как проверить содержит вал месяц строка февраля, например? - Юрий Попов 6 марта 17: 53
но почему вал месяц в таком формате "(JAN / FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV / DEC)? - Юрий Попов 6 марта 17: 55
это тоже может быть (январь, февраль, март)? - Юрий Попов 6 марта 17: 56
Чтобы соответствовать JAN или FEB или MARшаблон должен быть (JAN|FEB|MAR). Возможно, вы захотите попробовать прочитать
 о or (|)регулярных выражениях: regular-expressions.info/alternation.html
  - Солод 6 '17 марта в 18: 00
Шаблон a(b|c)dсоответствует abdИ.acd Это не соответствует строке abcd - Солод 6 '17 марта в 18: 02



 */

/*
Nullable types

Learn about null safety and safe calls in Kotlin and rewrite the following Java code so that it only has one if expression:
Узнайте о безопасности null и безопасных вызовах в Kotlin и перепишите следующий код Java так, чтобы он имел только одно выражение if:

public void sendMessageToClient(
@Nullable Client client,
@Nullable String message,
@NotNull Mailer mailer
) {
    if (client == null || message == null) return;

    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;

    String email = personalInfo.getEmail();
    if (email == null) return;

    mailer.sendMessage(email, message);
}
*/

fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer) {

   // if (client == null || message == null) return
   // val personalInfo = client.personalInfo ?: return
    //val email = personalInfo.email ?: return
    //val personalInfo = client.personalInfo ?: return
   // val email = (client.personalInfo ?: return).email ?: return

   // mailer.sendMessage(email, message)
    mailer.sendMessage(((client ?:return).personalInfo ?: return).email ?: return, message ?:return)

}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}

/* Лямбда

Kotlin поддерживает функциональное программирование. Узнайте о лямбдах в Котлине.

Передайте лямбду в функцию any, чтобы проверить, содержит ли коллекция четное число.
Функция any получает предикат в качестве аргумента и возвращает true,
 если существует хотя бы один элемент, удовлетворяющий предикату.

 */
fun containsEven(collection: Collection<Int>): Boolean =
    collection.any { it % 2 == 0 }


// Coans Part 2
/* Data classes

Learn about classes, properties and data classes and then rewrite the following Java code to Kotlin:
Узнайте о классах, свойствах и классах данных, а затем перепишите следующий код Java в Kotlin:
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
Afterwards add the data modifier to the resulting class.
This will make the compiler generate a few useful methods for this class:
equals/hashCode, toString and some others.
Затем добавьте модификатор данных в результирующий класс.
Это заставит компилятор сгенерировать несколько полезных методов для этого класса:
equals/hashCode, toString и некоторые другие.
 */
data class Person_coan(val name: String, val age: Int)
fun getPeople(): List<Person_coan> =
    listOf(Person_coan("Alice", 29), Person_coan("Bob", 31))
fun comparePeople(): Boolean =
    Person_coan("Alice", 29) == Person_coan("Alice", 29)

data class Persone(val name: String, val age: Int)
fun getPeoplee() = listOf(Persone("Alice", 29), Persone("Bob", 31))
fun comparePeoplee() = Persone("Alice", 29) == Persone("Alice", 29)

/* Smart casts
* Умные слепки
Rewrite the following Java code using smart casts and the when expression:
Перепишите следующий код Java, используя интеллектуальные приведения и выражение when:

public int eval(Expr expr) {
    if (expr instanceof Num) {
        return ((Num) expr).getValue();
    }
    if (expr instanceof Sum) {
        Sum sum = (Sum) expr;
        return eval(sum.getLeft()) + eval(sum.getRight());
    }
    throw new IllegalArgumentException("Unknown expression");
}

 */


// Sealed classes - ничего не понял переписал по аналогии разбирать
fun eval(expr: Expr): Int =
    when (expr) {
        is Num -> expr.value
        is Sum -> eval(expr.left) + eval(expr.right)
       else -> throw IllegalArgumentException("Unknown expression")
    }
//interface Expr
sealed class Expr
class Num(val value: Int) : Expr()
class Sum(val left: Expr, val right: Expr) : Expr();


/* Запечатанный класс
Повторно используйте решение из предыдущей задачи, но замените интерфейс запечатанным классом.
Тогда вам больше не нужен другой ветке, когда.
 */
/*
При импорте класса или функции можно указать другое имя,
 которое будет использоваться для нее, добавив новое имя после директивы import.
 Это может быть полезно, если вы хотите использовать два класса или функции с одинаковыми именами из разных библиотек.
Раскомментируйте код и сделайте его компиляцией.
 Переименовать случайным образом из Котлин пакет для случайных и случайно из пакета java в J случайным.
*/
// должно стоять наверху - здесь копия
//import kotlin.random.Random as KRandom
//import java.util.Random as JRandom

fun useDifferentRandomClasses(): String {
    return "Kotlin random: " +
            KRandom.nextInt(2) +
            " Java random:" +
            JRandom().nextInt(2) +
            "."
}

/*
Функция расширения
Узнайте о функциях расширения.
Затем реализуйте функции расширения в.r () и пара.р() и заставить их превратить в пару к рациональным числом.
Pair-это класс, определенный в стандартной библиотеке:

Extension functions
Learn about extension functions.
Then implement the extension functions Int.r() and Pair.r() and make them convert Int and Pair to a RationalNumber.
Pair is a class defined in the standard library:

data class Pair(
    val first: A,
    val second: B
)

 */
fun Int.r(): RationalNumber = RationalNumber(this,1)

fun Pair<Int, Int>.r(): RationalNumber = RationalNumber(this.first,this.second)

data class RationalNumber(val numerator: Int, val denominator: Int)

// 3 Conventions

/*  Comparison
Learn about operator overloading and how the different conventions for operations like ==, &lt;, + work in Kotlin.
Add the function compareTo to the class MyDate to make it comparable.
 After this the code below date1 &lt; date2 should start to compile.
Note that when you override a member in Kotlin, the override modifier is mandatory.

Сравнение
Узнайте о перегрузке операторов и о том, как в Kotlin работают различные соглашения для таких операций, как==, &lt;,+.
Добавьте функцию compareTo в класс MyDate, чтобы сделать его сопоставимым.
После этого код под date1 &lt; date2 должен начать компилироваться.
братите внимание, что при переопределении элемента в Kotlin модификатор переопределения является обязательным.
 */
data class MyDate0(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate0> {
    override fun compareTo(other: MyDate0) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}
fun test(date1: MyDate0, date2: MyDate0) {
    // this code should compile:
    println(date1 < date2)
}

//abstract operator fun compareTo(other: T): Int
// Сравнивает этот объект с указанным объектом для заказа.
// Возвращает ноль, если этот объект равен к указанному другому объекту,
// отрицательное число, если оно меньше другого ,
// или положительное число если это больше, чем другие .

/*Ranges

Using ranges implement a function
that checks whether the date is in the range between the first date and the last date (inclusive).
You can build a range of any comparable elements.
In Kotlin in checks are translated to the corresponding contains calls and .. to rangeTo calls:

Использование диапазонов реализация функции
это проверяет, находится ли дата в диапазоне между первой датой и последней датой (включительно).
Вы можете построить диапазон из любых сопоставимых элементов.
В Котлине в чеках переводятся на соответствующие содержит вызовы И.. диапазон до звонков:

val list = listOf("a", "b")
"a" in list  // list.contains("a")
"a" !in list // !list.contains("a")

date1..date2 // date1.rangeTo(date2)

 */

fun checkInRange(date: MyDate00, first: MyDate00, last: MyDate00): Boolean = date in first.. last

data class MyDate00(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate00> {
    override fun compareTo(other: MyDate00): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}

/*For loop
A Kotlin for loop can iterate through any object
if the corresponding iterator member or extension function is available.
Make the class DateRange implement Iterable, so that it can be iterated over.
Use the function MyDate.followingDate() defined in DateUtil.kt;
you don't have to implement the logic for finding the following date on your own.
Use an object expression which plays the same role in Kotlin as an anonymous class in Java.

Цикл for
Цикл Kotlin for может выполнять итерацию через любой объект,
если соответствующий элемент итератора или функция расширения доступны.
Сделайте реализацию класса DateRange итеративной, чтобы его можно было перебирать.
Используйте функцию MyDate.следующие данные() определены в DateUtil.kt;
вам не нужно реализовывать логику поиска следующей даты самостоятельно.
Используйте выражение объекта, которое играет ту же роль в Kotlin, что и анонимный класс в Java.
 */
// не понимаю:
/*class DateRange(val start: MyDate, val end: MyDate)

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

private operator fun <T : Comparable<T>> ClosedRange<T>.iterator(): Iterator<MyDate> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}*/
// https://www.i-programmer.info/programming/other-languages/11505.html
// след попытка :
class DateRange(val start: MyDate, val end: MyDate) {
    operator fun iterator(): Iterator<MyDate> {
    return iterator()     // start.followingDate()
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}

// import java.util.Calendar

/*
 * Returns the following date after the given one.
 * For example, for Dec 31, 2019 the date Jan 1, 2020 is returned.
 */
fun MyDate.followingDate(): MyDate {
    val c = Calendar.getInstance()
    c.set(year, month, dayOfMonth)
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    val timeInMillis = c.timeInMillis + millisecondsInADay
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)


/* Operators overloading

Implement date arithmetic. Support adding years, weeks, and days to a date. You could write the code like this: date + YEAR * 2 + WEEK * 3 + DAY * 15.

First, add the extension function plus() to MyDate, taking the TimeInterval as an argument. Use the utility function MyDate.addTimeIntervals() declared in DateUtil.kt

Then, try to support adding several time intervals to a date. You may need an extra class.
Перегрузка операторов

Реализовать арифметику дат. Поддержка добавления лет, недель и дней к дате. Вы можете написать код следующим образом: дата + год * 2 + неделя * 3 + ДЕНЬ * 15.

Во-первых, добавьте функцию расширения plus() к моей дате, взяв временной интервал в качестве аргумента. Используйте служебную функцию MyDate.добавьте временные интервалы (), объявленные в DateUtil.уз

Затем попробуйте поддержать добавление нескольких временных интервалов к дате. Возможно, Вам понадобится дополнительный класс.

 */

// import TimeInterval.*

data class MyDate01(val year: Int, val month: Int, val dayOfMonth: Int)

// Supported intervals that might be added to dates:
enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate01.plus(timeInterval: TimeInterval): MyDate01 = TODO()

fun task1(today: MyDate01): MyDate01 {
    return today + TimeInterval.YEAR + TimeInterval.WEEK
}

fun task2(today: MyDate01): MyDate01 {
    TODO("Uncomment") //return today + YEAR * 2 + WEEK * 3 + DAY * 5
}

//import java.util.Calendar

/*
 * Returns the date after the given time interval.
 * The interval is specified as the given amount of days, weeks of years.
 * Usages:
 * 'date.addTimeIntervals(TimeInterval.DAY, 4)'
 * 'date.addTimeIntervals(TimeInterval.WEEK, 3)'
 */
fun MyDate.addTimeIntervals(timeInterval: TimeInterval, amount: Int): MyDate {
    val c = Calendar.getInstance()
    c.set(year + if (timeInterval == TimeInterval.YEAR) amount else 0, month, dayOfMonth)
    var timeInMillis = c.timeInMillis
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    timeInMillis += amount * when (timeInterval) {
        TimeInterval.DAY -> millisecondsInADay
        TimeInterval.WEEK -> 7 * millisecondsInADay
        TimeInterval.YEAR -> 0L
    }
    val result = Calendar.getInstance()
    result.timeInMillis = timeInMillis
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}

/*Invoke
Objects with an invoke() method can be invoked as a function.
You can add an invoke extension for any class, but it's better not to overuse it:
fun Int.invoke() { println(this) }
1() //huh?..
Implement the function Invokable.invoke() so it can count the number of times it is invoked.

Взывать
Объекты с помощью метода invoke () можно вызывать как функцию.
Вы можете добавить расширение invoke для любого класса, но лучше не злоупотреблять им:
забавный Инт.invoke () { println (this) }
1 () / / а?..
Реализовать функцию вызываться.invoke (), так что он может считать количество раз, когда он вызывается.
https://kotlinlang.org/docs/reference/operator-overloading.html#invoke
 */
// Оператор вызова
class Invokable {
    var numberOfInvocations: Int = 0
        private set

    operator fun invoke(): Invokable {
        TODO()
    }
}

fun invokeTwice(invokable: Invokable) = invokable()()

