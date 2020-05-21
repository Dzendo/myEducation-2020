
fun main(args:Array<String>){
    println("Hello ${args[0]}")

    val isUnit = println("Выражение")
    println(isUnit)

    val temperature = 10
    val isHot = if (temperature > 50) true else false
    println(isHot)

    val message = "You are ${ if (temperature >50 ) "fired" else "safe" } fish"
    println (message)
}