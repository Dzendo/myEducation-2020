import java.lang.Math.random
import java.util.*


fun main(args:Array<String>) {
    println("Hello ${args[0]}")
    val time_arg: Int = args[0].toInt()
    println("  ${if (args[0].toInt() < 12) "Доброе утро" else "Спокойной ночи"}  Котлин")
    println("  ${when (args[0].toInt()){
        in 0..11  -> "Доброе утро"
        in 12..23 -> "Спокойной ночи"
        else  -> "Это не время"}}  Котлин")

    val random1 = random()
    val random2 = {random()}
    println(random1)
    println(random2)
    println(random2())

    // Создайте лямбду и назначьте ее rollDice, которая возвращает бросок костей (число от 1 до 12).
    val rollDice1 = { Random().nextInt(12) + 1}
    println(rollDice1())
    //Расширьте лямбду, чтобы получить аргумент, указывающий количество сторон кости, использованной для броска.
    val rollDice = { sides: Int ->
        Random().nextInt(sides) + 1
    }
    println(rollDice(6))
    // Если вы еще этого не сделали, исправьте лямбду, чтобы она возвращала 0, если число переданных сторон равно 0.
    val rollDice0 = { sides: Int ->
        if (sides == 0) 0
        else Random().nextInt(sides) + 1
    }
    println(rollDice0(0))
    //Создайте новую переменную rollDice2для той же лямбды, используя обозначение типа функции.
    val rollDice2: (Int) -> Int = { sides ->
        if (sides == 0) 0
        else Random().nextInt(sides) + 1
    }
    println(rollDice2(0))
    println(rollDice2(12))

    // Обозначение типа функции более читабельно, что уменьшает количество ошибок,
    // четко показывая, какой тип передается и какой тип возвращается.
    fun gamePlay(diceRoll: Int){
        // do something with the dice roll
        println(diceRoll)
    }
    gamePlay(rollDice2(4))
}

