fun main(){
// Еще о лябдах - резюие
 // лямбда функция без имени:
    { println("hello Fish")}
    // можем лямбду присоить переменной с аргументом
val waterFilter = {dirty: Int -> dirty / 2  }
    println("main")
    waterFilter(30)    // mainres0: kotlin.Int = 15
    // теперь дата класс рыба, кот имеет одно свойство - его имя
    data class  Fish (val name:String)
    // теперь можно создать переменную - список из классов трех рыб:
    val myFish = listOf(Fish("Flipper"), Fish("Moby Dick"), Fish("Dory"))
    // Напечатать имена всех кто содержит букву I : добавляем фильтр
    // Внутри фильтра мы используем it для ссылки на текущий элемент списка
    // получаем его имя и проверяем, содержит ли оно букву i
    myFish.filter { it.name.contains('i') }.joinToString(" ") { it.name }
    // joinToString(separator:" ") { it.name } - создает строку из всех имен элементов списка с исп разделителя
    // joinToString - еще одна из тех удобных функций стандартной библиотеки см документацию
    // joinToString https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/join-to-string.html Есть RUN
     // https://kotlinlang.org/api/latest/jvm/stdlib/index.html - 150 Пакетов ( 50 основных) по >100 функций каждый



}
