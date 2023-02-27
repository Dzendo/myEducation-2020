https://hyperskill.org/tracks/18
https://hyperskill.org/knowledge-map


fun main() {
    println("Use the val keyword when the value doesn't change. ")
     println("Use the var keyword when the value can change.")
      println("When you define a function, you define the parameters that can be passed to it.")
       println("When you call a function, you pass arguments for the parameters.")
}

Use the val keyword when the value doesn't change. 
Use the var keyword when the value can change.
When you define a function, you define the parameters that can be passed to it.
When you call a function, you pass arguments for the parameters.

fun main() { 
    println("New chat message from a friend")
}


fun main() {
    var discountPercentage: Int = 0
    var offer: String = ""
    val item = "Google Chromecast"
    discountPercentage = 20
    offer = "Sale - Up to $discountPercentage% discount on $item! Hurry up!"
    
    println(offer)
}

Sale - Up to 20% discount on Google Chromecast! Hurry up!

fun main() {
    val numberOfAdults = "20"
    val numberOfKids = "30"
    val total = numberOfAdults + numberOfKids
    println("The total party size is: $total")
}
The total party size is: 2030

fun main() {
    val numberOfAdults = 20
    val numberOfKids = 30
    val total = numberOfAdults + numberOfKids
    println("The total party size is: $total")
}
The total party size is: 50

fun main() {
    val baseSalary = 5000
    val bonusAmount = 1000
    val totalSalary = "$baseSalary + $bonusAmount"
    println("Congratulations for your bonus! You will receive a total of $totalSalary (additional bonus).")
}
Congratulations for your bonus! You will receive a total of 5000 + 1000 (additional bonus).

fun main() {
    val baseSalary = 5000
    val bonusAmount = 1000
    val totalSalary = baseSalary + bonusAmount
    println("Congratulations for your bonus! You will receive a total of $totalSalary (additional bonus).")
}
Congratulations for your bonus! You will receive a total of 6000 (additional bonus).

fun main() {
    val firstNumber = 10
    val secondNumber = 5
     val result = firstNumber + secondNumber
    println("$firstNumber + $secondNumber = $result")
}
10 + 5 = 15

fun main() {
    val firstNumber = 10
    val secondNumber = 5
    val thirdNumber = 8
    
    val result = add(firstNumber, secondNumber)
    val anotherResult = add(firstNumber, thirdNumber)

    println("$firstNumber + $secondNumber = $result")
    println("$firstNumber + $thirdNumber = $anotherResult")
}

// Define add() function below this line
fun add(firstNumber: Int, secondNumber: Int) : Int {
    return firstNumber + secondNumber
}

10 + 5 = 15
10 + 8 = 18

fun main() {
    val operatingSystem = "Chrome OS"
    val emailId = "sample@gmail.com"

    println(displayAlertMessage(operatingSystem, emailId))
}

// Define your displayAlertMessage() below this line.
fun displayAlertMessage(operatingSystem: String, emailId:String): String {
    println(operatingSystem)
    println(emailId)
   val rezult =  "There's a new sign-in request on $operatingSystem for your Google Account $emailId"
return rezult    
}
Chrome OS
sample@gmail.com
There's a new sign-in request on Chrome OS for your Google Account sample@gmail.com

fun main() {
    val operatingSystem = "Chrome OS"
    val emailId = "sample@gmail.com"

    println(displayAlertMessage(operatingSystem, emailId))
    
     // The following line of code assumes that you named your parameter as emailId. 
    // If you named it differently, feel free to update the name.
    val firstUserEmailId = "user_one@gmail.com"
    println(displayAlertMessage(emailId = firstUserEmailId))
    println()
    
    val secondUserOperatingSystem = "Windows"
    val secondUserEmailId = "user_two@gmail.com"

    println(displayAlertMessage(secondUserOperatingSystem, secondUserEmailId))
    println()

    val thirdUserOperatingSystem = "Mac OS"
    val thirdUserEmailId = "user_three@gmail.com"
    
}

// Define your displayAlertMessage() below this line.
fun displayAlertMessage(operatingSystem: String = "Unknown OS", emailId:String): String {
    println(operatingSystem)
    println(emailId)
   val rezult =  "There's a new sign-in request on $operatingSystem for your Google Account $emailId"
return rezult    
}

Chrome OS
sample@gmail.com
There's a new sign-in request on Chrome OS for your Google Account sample@gmail.com
Unknown OS
user_one@gmail.com
There's a new sign-in request on Unknown OS for your Google Account user_one@gmail.com

9. Шагомер

fun main() {
    val steps = 4000
    val caloriesBurned = pedoMeterStepsToCalories(steps);
    println("Walking $steps steps burns $caloriesBurned calories") 
}

fun pedoMeterStepsToCalories(numberOfstepS: Int): Double {
    val caloriesBurnedforEachStep = 0.04
    val totalCaloriesBurned = numberOfstepS * caloriesBurnedforEachStep
    return totalCaloriesBurned
}
Walking 4000 steps burns 160.0 calories


fun main() {
    println(compare(300,250))
    println(compare(300,300))
    println(compare(200,220))
   
}

fun compare (timeSpentToday: Int, timeSpentYesterday:Int): Boolean =
 		timeSpentToday > timeSpentYesterday
true
false
false

fun main() {
    println("City: Ankara")
    println("Low temperature: 27, High temperature: 31")
    println("Chance of rain: 82%")
    println()

    println("City: Tokyo")
    println("Low temperature: 32, High temperature: 36")
    println("Chance of rain: 10%")
    println()
    
    println("City: Cape Town")
    println("Low temperature: 59, High temperature: 64")
    println("Chance of rain: 2%")
    println()
    
    println("City: Guatemala City")
    println("Low temperature: 50, High temperature: 55")
    println("Chance of rain: 7%")
    println()
}

fun main() {
    weather("Ankara", 27, 31, 82)
    weather("Tokyo", 32, 36, 10)
    weather("Cape Town", 59, 64, 2)
    weather("Guatemala City", 50, 22, 7)
}
  fun  weather(city: String, low: Int, high: Int, rain: Int){
    println("City: $city")
    println("Low temperature: $low, High temperature: $high")
    println("Chance of rain: $rain%")
    println()
  }


12. Код решения
Печать сообщений
Решение использует println()функцию для печати сообщений в каждой строке.


fun main() {
    println("Use the val keyword when the value doesn't change.")
    println("Use the var keyword when the value can change.")
    println("When you define a function, you define the parameters that can be passed to it.")
    println("When you call a function, you pass arguments for the parameters.")
}
Исправлена ошибка компиляции
Код содержал две ошибки компиляции:

Строка должна заканчиваться двойными кавычками, а не одинарными кавычками.
Аргумент функции должен заканчиваться круглой скобкой, а не фигурной скобкой.

fun main() { 
    println("New chat message from a friend")
}
Шаблоны строк
Ошибки компиляции являются результатом присвоения переменных discountPercentageи, offerдоступных только для чтения, новым значениям; это присвоение не разрешено.


fun main() {
    val discountPercentage = 20
    val item = "Google Chromecast"
    val offer = "Sale  - Up to $discountPercentage% discount off $item! Hurry Up!"

    println(offer)
}
В качестве альтернативного решения вы можете объявить discountPercentageцелое число и offerстроку с varключевым словом. Однако их значения неизменяемы в контексте программы, поэтому вы можете придерживаться valключевого слова.

Конкатенация строк
Шаг 1
Программа выводит этот вывод:


The total party size is: 2030 
Это был вопрос с подвохом. Когда +оператор используется для Stringзначений, он создает объединенную строку. Целые числа заключены в двойные кавычки, поэтому они рассматриваются как строки, а не целые числа, отсюда вывод 2030.

Шаг 2
Вы можете убрать двойные кавычки вокруг переменных numberOfAdultsиnumberOfKids, чтобы преобразовать их в Intпеременные.


fun main() {
    val numberOfAdults = 20
    val numberOfKids = 30
    val total = numberOfAdults + numberOfKids
    println("The total party size is: $total")
}
Если вы помните, компилятор Kotlin может определять тип переменных на основе присвоенных им значений. В этом случае компилятор делает вывод, что numberOfAdultsnumberOfKidsпеременные и являются Intтипами.

Форматирование сообщений
Программа выводит этот вывод:


Congratulations for your bonus! You will receive a total of 5000 + 1000 (additional bonus).
"$baseSalary + $bonusAmount" использует синтаксис шаблонных выражений. В выражениях шаблонов сначала вычисляется код, а затем результат объединяется в строку.

В вопросе $baseSalaryпеременная оценивается 5000как значение, а $bonusAmountпеременная оценивается 1000как значение. Затем результат объединяется "5000 + 1000"в product и присваивается resultпеременной.

Реализация основных математических операций
Шаг 1
Определите неизменяемую resultпеременную с valключевым словом, а затем присвоите ей результат операции сложения:


fun main() {
    val firstNumber = 10
    val secondNumber = 5
    
    val result = firstNumber + secondNumber
    println("$firstNumber + $secondNumber = $result")
}
Шаг 2
Создайте add()функцию, которая принимает firstNumberпараметр и secondNumberпараметр, оба Intтипа, и возвращает Intзначение.
Введите код для операции сложения внутри add()тела функции, а затем используйте returnключевое слово, чтобы вернуть результат операции.

fun add(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber + secondNumber
}
Шаг 3
Определите subtract()функцию, которая принимает firstNumberпараметр и secondNumberпараметр, оба Intтипа, и возвращает Intзначение.
Введите код для операции вычитания внутри subtract()тела функции, а затем используйте returnключевое слово, чтобы вернуть результат операции.

fun subtract(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber - secondNumber
}
Измените main()функцию, чтобы использовать новую subtract()функцию. Пример решения может выглядеть так:

fun main() {
    val firstNumber = 10
    val secondNumber = 5
    val thirdNumber = 8
    
    val result = add(firstNumber, secondNumber)
    val anotherResult = subtract(firstNumber, thirdNumber)

    println("$firstNumber + $secondNumber = $result")
    println("$firstNumber - $thirdNumber = $anotherResult")
}

fun add(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber + secondNumber
}

fun subtract(firstNumber: Int, secondNumber: Int): Int {
    return firstNumber - secondNumber
}
Параметры по умолчанию
Шаг 1
Создайте displayAlertMessage()функцию, которая принимает operatingSystemпараметр и emailIdпараметр, оба Stringтипа, и возвращает Stringзначение.
В теле функции используйте шаблонное выражение, чтобы обновить сообщение и вернуть его.

fun displayAlertMessage(operatingSystem: String, emailId: String): String {
    return "There is a new sign-in request on $operatingSystem for your Google Account $emailId."
}
Шаг 2
Присвойте "Unknown OS"operatingSystemпараметру значение.

fun displayAlertMessage(
    operatingSystem: String = "Unknown OS",
    emailId: String
): String {
    return "There is a new sign-in request on $operatingSystem for your Google Account $emailId."
}
Шагомер
Имена функций и имена переменных должны соответствовать соглашению camel case.

Если имена содержат несколько слов, введите в нижний регистр первую букву первого слова, заглавную первую букву последующих слов и удалите все пробелы между словами.

Примеры имен функций включают:

calculateTip
displayMessage
takePhoto
Примеры имен переменных включают:

numberOfEmails
cityName
bookPublicationDate
Чтобы узнать больше об именах, см. раздел Правила именования.

Избегайте использования ключевого слова Kotlin в качестве имени функции, поскольку этим словам уже присвоены определенные значения в языке Kotlin.

Ваш код решения должен выглядеть примерно так: этот фрагмент кода:


fun main() {
    val steps = 4000
    val caloriesBurned = pedometerStepsToCalories(steps);
    println("Walking $steps steps burns $caloriesBurned calories") 
}

fun pedometerStepsToCalories(numberOfSteps: Int): Double {
    val caloriesBurnedForEachStep = 0.04
    val totalCaloriesBurned = numberOfSteps * caloriesBurnedForEachStep
    return totalCaloriesBurned
}
Сравните два числа
Создайте compareTime()функцию, которая принимает timeSpentTodayпараметр и timeSpentYesterdayпараметр, оба Intтипа, и возвращает Booleanзначение.
Решение основано на операторе >сравнения. Оператор вычисляет Booleanзначение, поэтому compareTime()функция просто возвращает результат timeSpentToday > timeSpentYesterday.

Например, если вы передаете 300аргумент timeSpentTodayпараметру и 250аргумент timeSpentYesterdayпараметру, тело функции вычисляется 300 > 250как , которое возвращает trueзначение, потому что 300 больше 250.


fun main() {
    println("Have I spent more time using my phone today: ${compareTime(300, 250)}")
    println("Have I spent more time using my phone today: ${compareTime(300, 300)}")
    println("Have I spent more time using my phone today: ${compareTime(200, 220)}")
}

fun compareTime(timeSpentToday: Int, timeSpentYesterday: Int): Boolean {
    return timeSpentToday > timeSpentYesterday
}

Have I spent more time using my phone today: true
Have I spent more time using my phone today: false
Have I spent more time using my phone today: false
Переместить дублирующийся код в функцию
Создайте функцию, которая распечатывает данные о погоде для города Анкара после main()функции.
Для имени функции вы можете использовать printWeatherForCity()или что-то подобное.

Вызовите функцию из main()функции.
Программа должна распечатать данные о погоде для Анкары.


fun main() {
    printWeatherForCity()
}

fun printWeatherForCity() {
    println("City: Ankara")
    println("Low temperature: 27, High temperature: 31")
    println("Chance of rain: 82%")
    println()
}
Теперь вы можете создать другую функцию, более гибкую, чтобы она могла печатать информацию о погоде для других городов.

Замените части println()инструкций, относящиеся к Анкаре, переменными.
Не забудьте использовать соглашение о регистре верблюда для имен переменных и $символа перед переменной, чтобы вместо имени переменной использовалось значение переменной. Это строковые шаблоны, о которых вы узнали из более ранней codelab.


fun printWeatherForCity() {
    println("City: $cityName")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $chanceOfRain%")
    println()
}
Измените определение функции так, чтобы эти переменные были параметрами, которые должны передаваться в функцию при ее вызове, и укажите тип данных для каждого параметра.
cityNameПараметр имеет Stringтип, в то время lowTempкак параметры , highTemp, и chanceOfRainимеют Intтип.

В returnопределении функции не требуется никакого значения, потому что сообщения печатаются на выходе.


fun printWeatherForCity(cityName: String, lowTemp: Int, highTemp: Int, chanceOfRain: Int) {
    println("City: $cityName")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $chanceOfRain%")
    println()
}
Обновите main()функцию, чтобы вызвать printWeatherForCity()функцию и передать сведения о погоде для Анкары.
Название города"Ankara", низкая температура27, высокая температура 31и вероятность дождя 82.


fun main() {
    printWeatherForCity("Ankara", 27, 31, 82)
}

fun printWeatherForCity(cityName: String, lowTemp: Int, highTemp: Int, chanceOfRain: Int) {
    println("City: $cityName")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $chanceOfRain%")
    println()
}
Запустите программу, чтобы убедиться, что на выходе отображаются данные о погоде в Анкаре.
Вызовите printWeatherForCity()функцию с информацией о погоде для других городов.

fun main() {
    printWeatherForCity("Ankara", 27, 31, 82)
    printWeatherForCity("Tokyo", 32, 36, 10)
    printWeatherForCity("Cape Town", 59, 64, 2)
    printWeatherForCity("Guatemala City", 50, 55, 7)
}

fun printWeatherForCity(cityName: String, lowTemp: Int, highTemp: Int, chanceOfRain: Int) {
    println("City: $cityName")
    println("Low temperature: $lowTemp, High temperature: $highTemp")
    println("Chance of rain: $chanceOfRain%")
    println()
}
Запустите программу.
Он должен выводить тот же результат, что и исходная программа, но теперь ваш код более лаконичен и не содержит ненужных повторений! Весь код для печати сведений о погоде в городе централизован в одном месте: printWeatherForCity()функция. Если вы когда-нибудь захотите изменить способ отображения сведений о погоде, вы можете изменить их в одном месте, которое применимо ко всем городам.










