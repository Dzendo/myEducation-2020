package Inline
// котлин обеспечивает очень мощную конструкцию, когда
// работает с функциями высшего порядка Inline
data class Fish (var name: String )

fun main(){

    val fish_end_end = Fish ("окончательный пример")
    println(fish_end_end.name.capitalize())


    fishExamples()
    fishExamples_end()
}

fun fishExamples_end() {
    val fish_end = Fish ("мой пример")
    println(fish_end.name.capitalize())
}

fun fishExamples() {
    val fish = Fish("splashy")  // грязный
    // with inLine no object is created, and Lambda body is inLined here:
    println(fish.name.capitalize())
}

inline fun  myWith(name: String, block: String.() -> Unit) { name.block() }

// // здесь мы говорим, call myWith на fish.name
//    // и мы разбираем его как лямбду, которую назовем заглавной буквой имени
//    myWith(fish.name) {
//        println(capitalize())                    // превратить в верхний регистр - возвращает копию Splashy
//        // Под капотом функция высшенр порядка
//        // with inLine no object is created, and Lambda body is inLined here:
//        fish.name.capitalize()
//    }
//
 // есть одна проблема:
// каждый раз когда мы вызываем myWith,
// Котлин сделает новый объект лямбда
// ДА, Лямбда - это объекты
// Лямбда-выражение является экземпляром интерфейса функции,
// который сам является подтипом объекта
// Чтобы понять это, можем переписать функцию в полную запись:
// without inLine an object is created every call to myWith:
//    myWith(fish.name, object: Function1<String,Unit) {
//      override fun invoke(name:String) {
//          name.capitalize()
//      }
//    })
        // with inLine no object is created, and Lambda body is inLined here:

//}

// Создание экземпляра функции каждый раз, когда вызывается myWith
// заниемает процессорное время и память
// Это проблема в большинстве случаев
// но если мы определили что-то вроде myWith, что мы будем использовать везде
// или лямбда сложнее
// накладнве расходы, т.е. необходимые ресурсы, могут складываться
// Чтобы исправить эту проблему Котлин давайте определим
// myWith как InLine
// Это обещание, каждый раз, когда вызывается myWith?
// фактически он преобразует исходный код в функцию inLine:
// inline fun  myWith(name: String, block: String.() -> Unit) {
// То есть компилятор изменит код на
// замените лямбду инструкциями внутри лямбды
// Это означает отсутствие накладных расходов
// давайте посмотрим как.
// Когда применяется преобразование inLine
// вызов лямбды заменяется содержимым тела функции лямбды.
//   fish.name.capitalize()
// В нашем примере myWith, когда мы применяем преобразование
// заглавнаяч буква вызывается непосредственно на fish.name
// Это действительно важно:
// Котлин давайте определим API на основе Lambda с нулевыми издержками
// Он даже не будет оплачивать стоимость вызова функции myWith, поскольку она встроена
// Стоит отметить, что включение больших функций увеличивает ваш размер кода,
// поэтому его лучше использовать для простых функций, таких как meWith
// Как пользователь этих API, нам не нужно беспокоиться о создании дополнительных объектов
// об этом позаботится компилятор

// проще говоря все что нужно:
//data class Fish (var name: String )
//fun main(){
//    fishExamples_end()
//}

//fun fishExamples_end() {
//    val fish_end = Fish ("мой пример")
//    println(fish_end.name.capitalize())
//}